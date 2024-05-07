package com.degenCoders.pastebin.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.degenCoders.pastebin.models.NoteEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteRepository extends MongoRepository<NoteEntity, String>  {
    
}