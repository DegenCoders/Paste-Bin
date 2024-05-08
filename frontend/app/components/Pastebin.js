"use client"
import React, { useState, useEffect, createContext } from 'react';
import Prism from 'prismjs';
import 'prismjs/themes/prism.css';
import 'prismjs/components/prism-javascript';
import 'prismjs/components/prism-python';
import axios from 'axios';
import { useRouter } from 'next/navigation';
import CryptoJS from 'crypto-js'; // Import CryptoJS

const Pastebin = ({ children }) => {
  const router = useRouter();
  const [content, setContent] = useState('');
  const [title, setTitle] = useState('');
  const [tags, setTags] = useState('');
  const [token, setToken] = useState('');
  const [noteId, setNoteId] = useState(null);

  useEffect(() => {
    Prism.highlightAll();
    const storedToken = localStorage.getItem('token');
    setToken(storedToken);
  }, []);

  const storeEncryptedNoteId = (id) => {
    const encryptedNoteId = CryptoJS.AES.encrypt(id.toString(), 'secret').toString();
    localStorage.setItem('encryptedNoteId', encryptedNoteId);
  };

  const handleInputChange = (event) => {
    setContent(event.target.value);
  };

  const handleTitleChange = (event) => {
    setTitle(event.target.value);
  };

  const handleTagsChange = (event) => {
    setTags(event.target.value);
  };

  const handleKeyDown = (event) => {
    if (event.key === 'Tab') {
      event.preventDefault();
      const { selectionStart, selectionEnd } = event.target;
      setContent(
        content.substring(0, selectionStart) +
          '    ' +
          content.substring(selectionEnd)
      );
    }
  };

  const handleSubmit = (event) => {
    event.preventDefault();
  
    const noteData = {
      title: title,
      content: content,
      tags: tags.split(',').map((tag) => tag.trim()),
    };
  
    const headers = {}; // Initialize an empty headers object
  
    // Check if the token is present
    if (token) {
      headers['Authorization'] = `Bearer ${token}`;
    }
  
    axios
      .post('http://localhost:8080/api/notes/create', noteData, {
        headers: headers, // Include headers conditionally
      })
      .then((response) => {
        console.log('Note created successfully:', response.data);
        setNoteId(response.data.id); // Store note ID in state
        setContent('');
        setTitle('');
        setTags('');
        storeEncryptedNoteId(response.data.id); // Store encrypted note ID in local storage
        router.push('/success');
      })
      .catch((error) => {
        console.error('Error creating note:', error);
      });
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-gray-100">
      <div className="bg-white p-8 rounded shadow-md w-full md:w-3/4 lg:w-1/2">
        <form onSubmit={handleSubmit}>
          <input
            className="w-full p-2 mb-4 border rounded-md focus:outline-none focus:ring focus:border-blue-300"
            placeholder="Title"
            value={title}
            onChange={handleTitleChange}
            spellCheck="false"
          />
          <textarea
            className="w-full h-48 p-4 border rounded-md resize-none focus:outline-none focus:ring focus:border-blue-300 mb-4"
            placeholder="Enter your code here..."
            value={content}
            onChange={handleInputChange}
            onKeyDown={handleKeyDown}
            spellCheck="false"
          />
          <input
            className="w-full p-2 mb-4 border rounded-md focus:outline-none focus:ring focus:border-blue-300"
            placeholder="Tags (comma-separated)"
            value={tags}
            onChange={handleTagsChange}
            spellCheck="false"
          />
          <div className="flex justify-end">
            <button
              type="submit"
              className="px-4 py-2 bg-blue-500 text-white rounded hover:bg-blue-600 focus:outline-none focus:bg-blue-600"
            >
              Submit
            </button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default Pastebin;
