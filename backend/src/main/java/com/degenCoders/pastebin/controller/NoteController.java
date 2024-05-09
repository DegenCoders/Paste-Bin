package com.degenCoders.pastebin.controller;

import com.degenCoders.pastebin.models.NoteEntity;
import com.degenCoders.pastebin.service.JwtService;
import com.degenCoders.pastebin.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/api/notes")
public class NoteController {

    @Autowired
    private NoteService noteService;

    @Autowired
    private JwtService jwtService;

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

    @PostMapping("/create")
    public ResponseEntity<NoteEntity> postNote(@RequestBody NoteEntity note, @RequestHeader("Authorization") String token ) {
        note.setCreationDate(Instant.now());
        note.setModifiedAt(Instant.now());
        final String jwt;
        String username;
        if(!token.isEmpty()){
            jwt = token.substring(7);
            try{
                username = jwtService.extractData(jwt);
            }
            catch(Error e){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            note.setUserId(username);
        }
        NoteEntity createdNote = noteService.createOrUpdateNote(note);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdNote);
    }

    @PutMapping("/{noteId}")
    public ResponseEntity<NoteEntity> updateNote(@PathVariable String noteId, @RequestBody NoteEntity note, @RequestHeader("Authorization") String token ) {
        note.setId(noteId);
        note.setModifiedAt(Instant.now());
        final String jwt;
        if(!token.isEmpty()){
            jwt = token.substring(7);
            if(!jwtService.isTokenValid(jwt, note.getUserId())){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        }
        NoteEntity updatedNote = noteService.createOrUpdateNote(note);
        return ResponseEntity.ok(updatedNote);
    }

    @SuppressWarnings("null")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNoteById(@PathVariable String id, @RequestHeader("Authorization") String token ) {
        final String jwt;
        ResponseEntity<NoteEntity> note = getNoteById(id);
        if(!token.isEmpty()){
            jwt = token.substring(7);
            if(!jwtService.isTokenValid(jwt, note.getBody().getUserId())){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        }
        noteService.deleteNoteById(id);
        return ResponseEntity.noContent().build();
    }
}
