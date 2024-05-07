package com.degenCoders.pastebin.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Set;
import java.time.Instant;

@Document(collection = "Notes")
public class NoteEntity {
    
    @Id
    private String id;
    private String userId;
    private String title;
    private String content;
    private Set<String> tags;
    private String category;
    private Instant createdAt;
    private Instant modifiedAt;
    
    // Constructors, getters, and setters
    
    public NoteEntity() {}

    public NoteEntity(String userId, String title, String content, Set<String> tags, String category, Instant createdAt, Instant modifiedAt) {
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.tags = tags;
        this.category = category;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    // Getters and Setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(Instant modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    @Override
    public String toString() {
        return "Note{" +
                "id='" + id + '\'' +
                ", userId=" + userId +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", tags=" + tags +
                ", category='" + category + '\'' +
                ", createdAt=" + createdAt +
                ", modifiedAt=" + modifiedAt +
                '}';
    }
}
