"use client";
import React, { useState, useEffect } from 'react';
import Prism from 'prismjs';
import 'prismjs/themes/prism.css';
import 'prismjs/components/prism-javascript';
import 'prismjs/components/prism-python';

const IndexPage = () => {
  const [content, setContent] = useState('');

  useEffect(() => {
    Prism.highlightAll();
  }, [content]);

  const handleInputChange = (event) => {
    setContent(event.target.value);
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
    // Handle submitting content (e.g., send it to a server)
    console.log('Submitted content:', content);
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-gray-100">
      <div className="bg-white p-8 rounded shadow-md w-full md:w-3/4 lg:w-1/2">
        <form onSubmit={handleSubmit}>
          <textarea
            className="w-full h-64 p-4 border rounded-md resize-none focus:outline-none focus:ring focus:border-blue-300"
            placeholder="Enter your code here..."
            value={content}
            onChange={handleInputChange}
            onKeyDown={handleKeyDown}
            spellCheck="false" // Disable spell check
          />
          <div className="mt-4 flex justify-end">
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

export default IndexPage;
