from fastapi import FastAPI, Request, Form, Cookie, HTTPException, status
from fastapi.responses import HTMLResponse, RedirectResponse, JSONResponse
from fastapi.requests import Request
from fastapi.templating import Jinja2Templates
from fastapi.middleware.cors import CORSMiddleware
from apis import apis

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

apis.connect()

templates = Jinja2Templates(directory="templates")

# Routes
@app.get("/", response_class=HTMLResponse)
async def index(request: Request):
    # user = await get_current_user(request.cookies.get("token"))
    return templates.TemplateResponse("index.html", {"request": request})

@app.post("/signup")
async def signup(username: str = Form(...), password: str = Form(...), email: str = Form(...)):
    return apis.signup(username,email, password)

@app.post('/signin')
async def signin(username: str = Form(...), password: str = Form(...)):
    return apis.signin(username,password)


@app.post("/save")
async def save(title: str= Form(...), value: str= Form(...), tags: str= Form(...)):
    return apis.save(title,value,tags)

@app.post('/getnotes')
async def getposts(page: int): 
    return apis.getposts(page)
