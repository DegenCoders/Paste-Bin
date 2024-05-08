"use client";
import React, { useState, useEffect, useContext } from 'react';
import Prism from 'prismjs';
import 'prismjs/themes/prism.css';
import axios from 'axios';
import { NoteIdProvider} from '../components/Pastebin'; // Import NoteIdProvider and useNoteId from Pastebin

const Success = () => {
  let { noteId } = useContext(NoteIdProvider) // Get noteId using useNoteId hook
  const [noteData, setNoteData] = useState(null);
  const [language, setLanguage] = useState(null);

  useEffect(() => {
    if (noteId) {
      axios.get(`http://localhost:8080/api/notes/${noteId}`)
        .then(response => {
          setNoteData(response.data);
          detectLanguage(response.data.content); // Detect language from note content
        })
        .catch(error => {
          console.error('Error fetching note data:', error);
        });
    }
  }, [noteId]);

  const detectLanguage = (content) => {
    Prism.highlightAll(); 
  };

  return (
    <div className="container mx-auto mt-8">
      {noteData ? (
        <div className="border border-gray-300 rounded p-4">
          <div className="flex justify-between items-center mb-4">
            <h1 className="text-lg font-bold">{noteData.title}</h1>
            <span className="text-gray-500">{language ? `Language: ${language}` : 'Language: unidentified'}</span>
          </div>
          <pre>
            <code className={`language-${language}`}>
              {noteData.content}
            </code>
          </pre>
        </div>
      ) : (
        <p>Loading...</p>
      )}
    </div>
  );
};


const SuccessWithNoteId = () => (
  <NoteIdProvider>
    <Success />
  </NoteIdProvider>
);

export default SuccessWithNoteId;
