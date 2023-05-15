from fastapi import FastAPI, Request, Form, Cookie, HTTPException, status
from fastapi.responses import HTMLResponse, RedirectResponse, JSONResponse
from fastapi.requests import Request
from fastapi.templating import Jinja2Templates
from pydantic import BaseModel
from passlib.context import CryptContext
from datetime import datetime, timedelta
import jwt
from jwt import PyJWTError
from cassandra.cluster import Cluster 
import uuid
import re
import os
import dotenv
from fastapi.middleware.cors import CORSMiddleware
dotenv.load_dotenv()
app = FastAPI()
PAGE_SIZE=5
origins = [
    "http://localhost",
    "http://localhost:3000",
    "http://localhost:8000",
]

app.add_middleware(
    CORSMiddleware,
    allow_origins=origins,
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)
# Cassandra configuration
cluster = Cluster([os.getenv("IP_ADDR")])
session = cluster.connect('my_keyspace')
rows = session.execute('SELECT * from users;')

# FastAPI configuration
templates = Jinja2Templates(directory="templates")
pwd_context = CryptContext(schemes=["bcrypt"], deprecated="auto")
SECRET_KEY = "secret_key"
ALGORITHM = "HS256"
ACCESS_TOKEN_EXPIRE_MINUTES = 30


# Hash password
def get_password_hash(password):
    return pwd_context.hash(password)

# Verify password
def verify_password(plain_password, hashed_password):
    return pwd_context.verify(plain_password, hashed_password)

# Create access token
def create_access_token(data: dict, expires_delta: timedelta = None):
    to_encode = data.copy()
    if expires_delta:
        expire = datetime.utcnow() + expires_delta
    else:
        expire = datetime.utcnow() + timedelta(minutes=15)
    to_encode.update({"exp": expire})
    encoded_jwt = jwt.encode(to_encode, SECRET_KEY, algorithm=ALGORITHM)
    return encoded_jwt

# Routes
@app.get("/", response_class=HTMLResponse)
async def index(request: Request):
    # user = await get_current_user(request.cookies.get("token"))
    return templates.TemplateResponse("index.html", {"request": request})

email_regex = r'^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$'

def is_valid_email(email):
    return re.match(email_regex, email) is not None

def user_exists(username):
    query= session.execute("SELECT username FROM users WHERE username = '{}' ALLOW FILTERING".format(username))
    if query:
        return True
    else: 
        return False

def get_user(username, password):
    pass



@app.post("/signup")
async def signup(username: str = Form(...), password: str = Form(...), email: str = Form(...)):
    if not is_valid_email(email):
        return {"Error" : "Enter a valid email"}
           
    if user_exists(username, email):
        return {"Error" : "User already exists"}

    hashed_password = get_password_hash(password)
    id = uuid.uuid4()

    addPrep = session.prepare("INSERT INTO users (user_id, username, password, email, created_at) VALUES (?, ?, ?, ?, toTimestamp(now()))")
    session.execute(addPrep, (id, username, hashed_password, email))
    
    return {"message": "User created successfully"}

@app.post('/signin')
async def signin(username: str = Form(...), password: str = Form(...)):
    if not user_exists(username):
        return {"Error" : "User does not exist"}

    try:
        usernameQuery = session.execute("SELECT username, password FROM users WHERE username = '{}' ALLOW FILTERING".format(username))
    except ValueError as err:
        return {"Error" : err}
    
    for row in usernameQuery:
        username = row.username
        hashed_password = row.password

    if not verify_password(password, hashed_password):
        return {"Error" : "Invalid Password"}
    
    return {"Message" : "Sign In Sucessful!"}


@app.post("/save")
async def save(request: Request, title: str= Form(...), value: str= Form(...), tags: str= Form(...)):
    id = uuid.uuid4()
    query = 'SELECT max(indext) FROM notes;'
    lastindex = session.execute(query).current_rows[0].system_max_indext
    if lastindex is None:
        lastindex = 0
    else:
        lastindex+=1
    tags=tags.split()
    strtags = ", ".join("'{}'".format(tag) for tag in tags)
    query = "INSERT INTO notes(indext, note_id, content, created_at, category, modified_at, tags, title, user_id) VALUES ({}, {}, '{}', toTimestamp(now()), 'luridarcy', toTimestamp(now()), {{{}}}, '{}', 550e8400-e29b-41d4-a716-446655440000);".format(lastindex, id, value, strtags,title)
    session.execute(query)
    return rows.current_rows


@app.post('/getnotes')
async def getposts(page: int): 
    global PAGE_SIZE
    pagerange=page*PAGE_SIZE
    query = f"SELECT * FROM notes WHERE user_id = 550e8400-e29b-41d4-a716-446655440000 AND category = 'luridarcy' AND indext >= {pagerange} ORDER BY indext ASC LIMIT {PAGE_SIZE} ALLOW FILTERING;"
    posts = session.execute(query).current_rows
    return posts



if __name__ == "__main__":
    import uvicorn

    uvicorn.run(app, host="localhost", port=8800)
