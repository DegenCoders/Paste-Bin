from fastapi import FastAPI, Request, Form
from fastapi.responses import HTMLResponse
from fastapi.templating import Jinja2Templates
from cassandra.cluster import Cluster 
import uvicorn
import uuid


app = FastAPI()
templates = Jinja2Templates(directory="templates")

cluster = Cluster()
session = cluster.connect('my_keyspace')

rows = session.execute('SELECT * from users;')
print(rows.current_rows)

@app.get("/", response_class=HTMLResponse)
async def root(request: Request):
    return templates.TemplateResponse("index.html", {"request": request})

@app.post("/save")
async def save(request: Request, value: str= Form(...)):
    id = uuid.uuid4()
    # return templates.TemplateResponse("saved.html", {"request": request, "value" : value})
    query = "INSERT INTO notes(note_id , content , created_at , category , modified_at, tags , title , user_id) VALUES ({}, '{}', toTimestamp(now()), 'luridarcy', toTimestamp(now()), {{'gaming', 'stuff', 'more stuff'}}, 'Title', 550e8400-e29b-41d4-a716-446655440000)".format(id, value)
    session.execute(query)
    print(rows.current_rows)
    return rows.current_rows

if __name__ == '__main__':
    uvicorn.run(app, host='localhost', port=5000)