package com.degenCoders.pastebin.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.degenCoders.pastebin.models.NoteEntity;
import com.degenCoders.pastebin.service.NoteService;

@RestController
@RequestMapping("/api/notes")
public class NoteController {

    @Autowired
    private NoteService noteService;


    @GetMapping("/")
    public List<NoteEntity> getAllNotes() {
        return noteService.getAllNotes();
    }

    @GetMapping("/{id}")
    public NoteEntity getNoteById(@PathVariable String id) {
        return noteService.getNoteById(id);
    }

    @PostMapping("/")
    public NoteEntity postNote(@RequestBody NoteEntity note) {
        return noteService.createOrUpdateNote(note);
    }

    @PutMapping("/{noteId}")
    public NoteEntity updateNote(@PathVariable String noteId, @RequestBody NoteEntity note) {
        note.setId(noteId);
        return noteService.createOrUpdateNote(note);
    }
    
    @DeleteMapping("/{id}")
    public void deleteNoteById(@PathVariable String id) {
        noteService.deleteNoteById(id);
    }

    // Other controller methods for CRUD operations
}
