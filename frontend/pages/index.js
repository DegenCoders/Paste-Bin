import Head from 'next/head'
import Image from 'next/image'
import Draggable from 'react-draggable'
import { useEffect, useRef, useState } from 'react'

const randomInt = (min, max) => {
  return Math.floor(Math.random() * (max - min + 1) + min)
}
const genEmptyArray = () => {
  let arr = []
  for (let i = 0; i < 7; i++) {
    let row = []
    for (let j = 0; j < 7; j++) {
      row.push();
    }
    arr.push(row)
  }
  return arr;
}
const generateGrid = () => {
  let pos = [];
  let count = 0;
  const numArr = genEmptyArray();
  while (count < 10) {
    let x = randomInt(0, 6);
    let y = randomInt(0, 6)
    let find = pos.find(e => (e.x === x && e.y === y))
    if (!find) {
      pos.push({ x: x, y: y })
      count++;
    }
  }
  for (const i in pos) {
    numArr[pos[i].x][pos[i].y] = parseInt(i)
  }
  return numArr
}
const grid = generateGrid()

export default function Home() {
  const [coords, setCoords] = useState([0, 0])
  const [count, setCount] = useState(0);
  const [w, setw] = useState(0)
  const [visibleGridView, setVisibleGridView] = useState(null)
  const [visibleGrid, setVisibleGrid] = useState(null)
  const defaultKeyState = [{ pos: [1, 4], val: 1 }, { pos: [3, 4], val: 2 }, { pos: [4, 2], val: 7 }]
  const [keys, setKeys] = useState(defaultKeyState)
  const vref = useRef(null)
  const renderVisibleGridView = (arr) => {
    let count = 1;
    let res = [];
    let nm = []
    for (let i = 0; i < 7; i++) {
      let row = []
      for (let j = 0; j < 7; j++) {
        row.push(arr[i][j])
        res.push(<div className='grid-item' key={count++}><h3>{arr[i][j]}</h3></div>)
      }
      nm.push(row)
    }
    setVisibleGridView(res)
    // if(nm[keys[keys.length-1].pos[0]][keys[keys.length-1].pos[1]]==keys[keys.length-1].val){
    //   alert("HERE")
    // }
  }
  const [formState, setFormState] = useState({
    username: '',
    password: ''
  });
  const handleChange = event => {
    const { name, value } = event.target;
    setFormState(prevState => ({ ...prevState, [name]: value }));
  };
  const handleDrag = (e, ui) => {
    setCoords(state => [ui.deltaX + state[0], ui.deltaY + state[1]])
  }
  const handleStop = () => {
    if (visibleGrid[keys[keys.length - 1].pos[0]][keys[keys.length - 1].pos[1]] == keys[keys.length - 1].val) {
      setKeys(state => {
        let temp = state.slice(0, keys.length - 1)
        if (temp.length == 0) {
          var details = JSON.parse(JSON.stringify(formState))
          var formBody = [];
          console.log(details)
          for (var property in details) {
            console.log(property)
            var encodedKey = encodeURIComponent(property);
            var encodedValue = encodeURIComponent(details[property]);
            formBody.push(encodedKey + "=" + encodedValue);
          }
          formBody = formBody.join("&");
          try {
            fetch('http://localhost:8000/signin', {
              method: 'POST',
              headers: {
                'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'
              },
              body: formBody
            }).then(res => {
              return res.json()
            }).then(data => {
              if (data.Message == "Sign In Sucessful!") {
                console.log("correct username and password!")
              }
              else{
                console.log("INCORRECT username OR password!!!!!!")
              }
            })
          }
          catch (err) {
            console.log(err)
          }
          return defaultKeyState;
        }
        else {
          return temp;
        }
      })
      console.log('pass ', keys.length)
    }
    else {
      setKeys(defaultKeyState)
      console.log('reset')
    }
  }
  const generateVisibleGrid = () => {
    const res = genEmptyArray()
    if (coords[0] > 490 || coords[0] < -490 || coords[1] > 490 || coords[1] < -490) {
      setCoords([0, 0])
      return
    }
    let oy = (coords[1] / 70)
    let ox = (coords[0] / 70)
    console.log(ox, oy)
    for (let i = 0; i < 7; i++) {
      for (let j = 0; j < 7; j++) {
        let x = i - ox;
        let y = j - oy;
        if (x >= 7) x = x - 7;
        if (x < 0) x = 7 + x;
        if (y >= 7) y = y - 7;
        if (y < 0) y = 7 + y;
        res[j][i] = grid[y][x]
      }
    }
    setVisibleGrid(res)
    renderVisibleGridView(res)
  }
  useEffect(() => {
    generateVisibleGrid();
  }, [coords])
  return (
    <div>
      <Head>
        <title>GPA</title>
      </Head>

      <main>
        <div>
          <div>
            <label>Username: </label><input type="text" name="username" value={formState.username} onChange={handleChange} ></input>
          </div>
          <div>
            <label>Password: </label><input type="password" name="password" value={formState.password} onChange={handleChange} ></input>
          </div>
          <div className='image-grid'>
            <img src={`https://i.imgur.com/ChmuZXI.png`}></img>
          </div>
          <div className='visible-grid'>
            {visibleGridView}
          </div>
          <Draggable position={{ x: 0, y: 0 }} bounds={{ top: -490, left: -490, right: 490, bottom: 490 }} grid={[70, 70]} onDrag={handleDrag} onStop={handleStop}>
            <div className='draggable-grid' onClick={(e) => { if (e.type == 'contextmenu') alert('hello') }}>
            </div>
          </Draggable>

        </div>
        {/* <video ref={vref}><source src='../test.mp4' type="video/mp4"></source></video> */}
      </main>
    </div>

  )
}


