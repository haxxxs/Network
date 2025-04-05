package com.example.demo;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

@WebMvcTest(NoteController.class)
public class NoteControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NoteService noteService;

    private Note note1;
    private Note note2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        note1 = new Note();
        note1.setId(1L);
        note1.setTitle("Note 1");

        note2 = new Note();
        note2.setId(2L);
        note2.setTitle("Note 2");
    }

    @Test
    void testGetAllNotes() throws Exception {
        when(noteService.getAllNotes()).thenReturn(Arrays.asList(note1, note2));

        mockMvc.perform(get("/notes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Note 1"))
                .andExpect(jsonPath("$[1].title").value("Note 2"));
    }

    @Test
    void testGetNoteById() throws Exception {
        when(noteService.getNoteById(1L)).thenReturn(Optional.of(note1));

        mockMvc.perform(get("/notes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Note 1"));
    }

    @Test
    void testCreateNote() throws Exception {
        when(noteService.createNote(any(Note.class))).thenReturn(note1);

        mockMvc.perform(post("/notes")
                        .contentType("application/json")
                        .content("{\"title\": \"Note 1\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Note 1"));
    }

    @Test
    void testUpdateNote() throws Exception {
        Note updatedNote = new Note();
        updatedNote.setId(1L);
        updatedNote.setTitle("Updated Note 1");

        when(noteService.updateNote(eq(1L), any(Note.class))).thenReturn(updatedNote);

        mockMvc.perform(put("/notes/1")
                        .contentType("application/json")
                        .content("{\"title\": \"Updated Note 1\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Note 1"));
    }

    @Test
    void testUpdateNoteNotFound() throws Exception {
        when(noteService.updateNote(eq(1L), any(Note.class))).thenReturn(null);

        mockMvc.perform(put("/notes/1")
                        .contentType("application/json")
                        .content("{\"title\": \"Non-existing Note\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteNoteById() throws Exception {
        doNothing().when(noteService).deleteNoteById(1L);

        mockMvc.perform(delete("/notes/1"))
                .andExpect(status().isOk());
    }
}
