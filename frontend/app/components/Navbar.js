import Link from 'next/link'
import React from 'react'

const Navbar = () => {
  return (
    <>
        <nav className='w-full py-5 px-6 bg-black text-white flex items-center justify-between'>
            <Link
              href="/"
            >
                <h1 className='text-2xl font-bold'>Paste<span className='text-blue-700'>Bin</span></h1>
            </Link>

            <div>
                <Link
                href='/signup'
                className='px-4 py-3 bg-blue-700 text-white text-sm rounded-full font-medium hover:bg-blue-600  tracking-light'>
                    Login
                </Link>
            </div>
        </nav>
    </>
  )
}

export default Navbar
