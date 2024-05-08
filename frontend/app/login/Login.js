"use client"
import React, { useEffect, useState } from 'react'
import axios from 'axios'
import {toast} from "react-hot-toast"
import { useRouter } from 'next/navigation'
import Link from 'next/link'

const Login = () => {
    const router = useRouter()
    const [user, setUser] = useState({
        email: "",
        password: "", 
    })

    const [buttonDisabled, setButtonDisabled] = useState(false)

    const [loading, setLoading] = useState(false)

    const onSignin = async () => {
        try {
            setLoading(true)
            const response = await axios.post("http://localhost:8080/api/auth/signin", user);
            const token = response.data.token;

            localStorage.setItem('token', token);
            router.push('/');
        } catch (error) {
            console.log("Signin failed")
            toast.error(error.message)
        } finally {
            setLoading(false);
        }
    }

    useEffect(() => {
        if (user.email.length > 0 && user.password.length > 0) {
            setButtonDisabled(false)
        } else {
            setButtonDisabled(true)
        }
        
    }, [user])

    return (
        <div className='flex flex-col items-center justify-center min-h-screen py-2 bg-black text-white'>
            <label htmlFor = "email">Email</label>
            <input 
                className='text-black p-2 border border-gray-300 rounded-lg mb-4 focus:outline-none focus:border-gray-600'
                id='email'
                value={user.email}
                onChange={(e) => setUser({...user, email: e.target.value })}
                placeholder='Email'
                type="email"
            />
            
            <label htmlFor = "password">Password</label>
            <input 
                className='text-black p-2 border border-gray-300 rounded-lg mb-4 focus:outline-none focus:border-gray-600'
                id='password'
                value={user.password}
                onChange={(e) => setUser({...user, password: e.target.value })}
                placeholder='Password'
                type="password"
            />     

            <button
                onClick={onSignin}
                disabled={buttonDisabled}
                className='p-2 border border-gray-300 rounded-lg mb-4 focus:outline-none focus:border-gray-600'
            >
                {loading ? "Signing in..." : "Sign In"}
            </button>
        </div>      
    )
}

export default Login
