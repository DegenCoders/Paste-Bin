package com.degenCoders.pastebin.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.degenCoders.pastebin.models.NoteEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteRepository extends MongoRepository<NoteEntity, String>  {
    void deleteByUserIdIsNullAndCreationDateBefore(LocalDateTime date);
    void deleteByUserIdIsNotNullAndCreationDateBefore(LocalDateTime date);
    List<NoteEntity> findByUserId(String userId);
}
