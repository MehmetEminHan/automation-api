package com.neuroval.notepadapi.model;

import lombok.Data;

@Data
public class SaveResponse {
    private String status;
    private String message;
    private NoteDTO data;
}
