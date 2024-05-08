import Link from 'next/link'
import React from 'react'
import { useRouter } from 'next/router'

const Navbar = () => {
  const router = useRouter();
  const token = localStorage.getItem('token');

  const handleSignOut = () => {
    localStorage.removeItem('token');
    router.push('/');
  };

  return (
    <>
      <nav className='w-full py-5 px-6 bg-black text-white flex items-center justify-between'>
        <Link href="/">
          <h1 className='text-2xl font-bold'>Paste<span className='text-blue-700'>Bin</span></h1>
        </Link>

        <div>
          {token ? (
            <>
              <button
                onClick={handleSignOut}
                className='px-4 py-3 text-sm rounded-full font-medium text-blue-700 border border-blue-700 hover:bg-blue-700 hover:text-white mx-2'
              >
                {email}
              </button>
            </>
          ) : (
            <>
              <Link
                href='/login'
                className='px-4 py-3 text-sm rounded-full font-medium text-blue-700 border border-blue-700 hover:bg-blue-700 hover:text-white mx-2'
              >
                Login
              </Link>

              <Link
                href='/signup'
                className='px-4 py-3 bg-blue-700 text-white text-sm rounded-full font-medium hover:bg-blue-600 tracking-light'
              >
                Signup
              </Link>
            </>
          )}
        </div>
      </nav>
    </>
  )
}

export default Navbar
