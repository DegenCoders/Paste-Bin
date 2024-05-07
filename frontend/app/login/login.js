"use client"
import React, { useEffect, useState } from 'react'
import axios from 'axios'
import {toast} from "react-hot-toast"
import { useRouter } from 'next/navigation'
import Link from 'next/link'

const login = () => {
    const auth = () => {
        const router = useRouter()
        const [user, SetUser] = useState({
            email: "",
            password: "", 
        })
    
        const [buttonDisabled, setButtonDisabled] = useState(false)
    
        const [loading, setLoading] = useState(false)
    
        const onSignup = async () => {
            try {
                setLoading(true)
                axios.post("http://localhost:8080/api/auth/signin", user).then((response)=>{
                    console.log("Signup sucess", response.data);
                })
                .catch(function (error) {
                  console.log(error);
                });
                router.push('/login')
            } catch (error) {
                console.log("Signup failed")
                toast.error(error.message)
            }
        }
        useEffect(() => {
            if (user.email.length > 0 && user.password.length > 0 && user.username.length > 0) {
                setButtonDisabled(false)
            } else {
                setButtonDisabled(true)
            }
            
        }, [user])
    


  return (
 <div className='flex flex-col items-center justify-center min-h-screen py-2 bg-black text-white'>
        <label htmlFor = "email">email</label>
        <input 
        className='text-black p-2 border border-gray-300 rounded-lg mb-4 focus:outline-none focus:border-gray-600'
        id='email'
        value={user.email}
        onChange={(e) => SetUser({...user, email: e.target.value })}
        placeholder='email'
        type="email"/>
        
        <label htmlFor = "username">password</label>
        <input 
        className='text-black p-2 border border-gray-300 rounded-lg mb-4 focus:outline-none focus:border-gray-600'
        id='password'
        value={user.password}
        onChange={(e) => SetUser({...user, password: e.target.value })}
        placeholder='password'
        type="password"/>     
        

        <button
            onClick={onSignup}
            className='p-2 border border-gray-300 rounded-lg mb-4 focus:outline-none focus:border-gray-600'
        >

            {buttonDisabled ? "No signup" : "Signup"}
        </button>
        <Link
        href='/'
        >
        Login page
        </Link>

    </div>      
  )
}
}

export default login
