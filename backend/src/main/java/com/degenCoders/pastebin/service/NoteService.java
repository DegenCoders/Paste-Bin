package com.degenCoders.pastebin.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.degenCoders.pastebin.models.NoteEntity;
import com.degenCoders.pastebin.repository.NoteRepository;

@Service
public class NoteService {
    @Autowired
    private NoteRepository noteRepository;

    public NoteEntity getNoteById(String id) {
        return noteRepository.findById(id).orElse(null);
    }

    public List<NoteEntity> getAllNotes() {
        return noteRepository.findAll();
    }

    public List<NoteEntity> getUserNotes(String userId) {
        return noteRepository.findByUserId(userId);
    }

    public NoteEntity createOrUpdateNote(NoteEntity note) {
        return noteRepository.save(note);
    }

    public void deleteNoteById(String id) {
        noteRepository.deleteById(id);
    }

    
}
