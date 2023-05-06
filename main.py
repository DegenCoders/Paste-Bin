from fastapi import FastAPI, Request, Form
from fastapi.responses import HTMLResponse
from fastapi.templating import Jinja2Templates
import uvicorn

app = FastAPI()
templates = Jinja2Templates(directory="templates")



@app.get("/", response_class=HTMLResponse)
async def root(request: Request):
    return templates.TemplateResponse("index.html", {"request": request})

@app.post("/save")
async def save(request: Request, value: str= Form(...)):
    return templates.TemplateResponse("saved.html", {"request": request, "value" : value})

if __name__ == '__main__':
    uvicorn.run(app, host='localhost', port=5000)