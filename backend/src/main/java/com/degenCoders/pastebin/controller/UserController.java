package com.degenCoders.pastebin.controller;

import com.degenCoders.pastebin.models.DashEntity;
import com.degenCoders.pastebin.models.NoteEntity;
import com.degenCoders.pastebin.models.UserEntity;
import com.degenCoders.pastebin.service.JwtService;
import com.degenCoders.pastebin.service.NoteService;
import com.degenCoders.pastebin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private NoteService noteService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public ResponseEntity<List<UserEntity>> getAllUsers() {
        List<UserEntity> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/dashboard")
    public ResponseEntity<DashEntity> getDashData(@RequestHeader("Authorization") String token) {
        DashEntity dashboard = new DashEntity();
        final String jwt;
        String username = "";
        String email = "";
        if(!token.isEmpty()){
            jwt = token.substring(7);
            try{
                username = jwtService.extractData(jwt);
                email = jwtService.extractEmail(jwt);
                System.out.println(email);
                System.out.println(username);
            }
            catch(Error e){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        }
        List<NoteEntity> notes = noteService.getUserNotes(username);
        dashboard.setNotes(notes);
        dashboard.setUsername(username);
        dashboard.setEmail(email);
        return ResponseEntity.ok(dashboard);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserEntity> getUserById(@PathVariable String userId) {
        UserEntity user = userService.getUserById(userId);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/")
    public ResponseEntity<UserEntity> createUser(@RequestBody UserEntity user) {
        UserEntity createdUser = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserEntity> updateUser(@PathVariable String userId, @RequestBody UserEntity user) {
        UserEntity updatedUser = userService.updateUser(userId, user);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUserById(@PathVariable String userId) {
        userService.deleteUserById(userId);
        return ResponseEntity.noContent().build();
    }
}
