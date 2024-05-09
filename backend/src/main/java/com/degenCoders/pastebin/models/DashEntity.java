package com.degenCoders.pastebin.models;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DashEntity {
    public List<NoteEntity> notes;
    public String username;
    public String email;
}
