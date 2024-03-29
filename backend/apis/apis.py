import os
from cassandra.cluster import Cluster 
import uuid
import re
import datetime
from passlib.context import CryptContext
import jwt
from datetime import datetime, timedelta
import dotenv
from fastapi.responses import JSONResponse
from kafka import KafkaProducer

producer = KafkaProducer(bootstrap_servers=['kafka:9092'])

dotenv.load_dotenv()
pwd_context = CryptContext(schemes=["bcrypt"], deprecated="auto")
SECRET_KEY = "secret_key"
ALGORITHM = "HS256"
ACCESS_TOKEN_EXPIRE_MINUTES = 30
email_regex = r'^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$'
PAGE_SIZE=5

def get_password_hash(password):
    return pwd_context.hash(password)

def create_access_token(data: dict, expires_delta: timedelta = None):
    to_encode = data.copy()
    if expires_delta:
        expire = datetime.utcnow() + expires_delta
    else:
        expire = datetime.utcnow() + timedelta(minutes=15)
    to_encode.update({"exp": expire})
    encoded_jwt = jwt.encode(to_encode, SECRET_KEY, algorithm=ALGORITHM)
    return encoded_jwt

def is_valid_email(email):
    return re.match(email_regex, email) is not None

def user_exists(username):
    query= session.execute("SELECT username FROM users WHERE username = '{}' ALLOW FILTERING".format(username))
    if query:
        return True
    else: 
        return False



def verify_password(plain_password, hashed_password):
    return pwd_context.verify(plain_password, hashed_password)

def connect():    
    global session
    cluster = Cluster([os.getenv("IP_ADDR")])
    session = cluster.connect('my_keyspace')

def signup(username, email, password):
    if not is_valid_email(email):
        return {"Error" : "Enter a valid email"}
    if user_exists(username):
        return {"Error" : "User already exists"}
    hashed_password = get_password_hash(password)
    id = uuid.uuid4()
    addPrep = session.prepare("INSERT INTO users (user_id, username, password, email, created_at) VALUES (?, ?, ?, ?, toTimestamp(now()))")
    session.execute(addPrep, (id, username, hashed_password, email))
    return {"message": "User created successfully"}

def signin(username,password):
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
    
    access_token = create_access_token(data={"sub": username}, expires_delta=timedelta(minutes=ACCESS_TOKEN_EXPIRE_MINUTES))
    response = JSONResponse(content={"message" : "Sign in sucessfull!"})
    response.set_cookie(key="Authorization", value=f"Bearer {access_token}", httponly=True, samesite="None", secure=True)

    return response

def save(title,value,tags):
    producer.send('my-topic', key=b'foo', value=b'bar')
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
    rows = session.execute(query)
    return rows.current_rows

def getposts(page):    
    global PAGE_SIZE
    pagerange=page*PAGE_SIZE
    query = f"SELECT * FROM notes WHERE user_id = 550e8400-e29b-41d4-a716-446655440000 AND category = 'luridarcy' AND indext >= {pagerange} ORDER BY indext ASC LIMIT {PAGE_SIZE} ALLOW FILTERING;"
    posts = session.execute(query).current_rows
    print(posts)
    return posts

def delete_token():
    response = JSONResponse(content={"message": "Token deleted successfully"})
    response.delete_cookie(key="Authorization")
    return response