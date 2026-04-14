package com.neuroval.notepadapi.model;

import lombok.Data;

import java.util.Date;

@Data
public class NoteDTO {
    public Integer id;
    public String title;
    public String content;
    public String author;
    public Date creationDate;
    public Date modificationDate;
}
