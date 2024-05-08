package com.degenCoders.pastebin.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
import java.time.Instant;

@Document(collection = "Notes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class NoteEntity {
    
    @Id
    private String id;
    private String userId;
    private String title;
    private String content;
    private Set<String> tags;
    private Instant creationDate;
    private Instant modifiedAt;
    
    @Override
    public String toString() {
        return "Note{" +
                "id='" + id + '\'' +
                ", userId=" + userId +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", tags=" + tags +
                ", creationDate=" + creationDate +
                ", modifiedAt=" + modifiedAt +
                '}';
    }
}
