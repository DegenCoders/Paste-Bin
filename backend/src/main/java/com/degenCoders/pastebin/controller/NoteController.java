package com.degenCoders.pastebin.controller;

import com.degenCoders.pastebin.models.NoteEntity;
import com.degenCoders.pastebin.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notes")
public class NoteController {

    @Autowired
    private NoteService noteService;

    @GetMapping("/")
    public ResponseEntity<List<NoteEntity>> getAllNotes() {
        List<NoteEntity> notes = noteService.getAllNotes();
        return ResponseEntity.ok(notes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NoteEntity> getNoteById(@PathVariable String id) {
        NoteEntity note = noteService.getNoteById(id);
        return ResponseEntity.ok(note);
    }

    @PostMapping("/")
    public ResponseEntity<NoteEntity> postNote(@RequestBody NoteEntity note) {
        NoteEntity createdNote = noteService.createOrUpdateNote(note);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdNote);
    }

    @PutMapping("/{noteId}")
    public ResponseEntity<NoteEntity> updateNote(@PathVariable String noteId, @RequestBody NoteEntity note) {
        note.setId(noteId);
        NoteEntity updatedNote = noteService.createOrUpdateNote(note);
        return ResponseEntity.ok(updatedNote);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNoteById(@PathVariable String id) {
        noteService.deleteNoteById(id);
        return ResponseEntity.noContent().build();
    }
}
