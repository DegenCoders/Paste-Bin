import Image from "next/image";
import Navbar from "./components/Navbar";
import Pastebin from "./components/Pastebin";


export default function Home() {
  return (
    <div>
        <Navbar/>
        <Pastebin/>
    </div>
  );
}