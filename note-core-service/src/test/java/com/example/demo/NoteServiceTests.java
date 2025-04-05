package com.example.demo;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class NoteServiceTests {

    @Mock
    private NoteRepository noteRepository;

    @InjectMocks
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
    void testGetAllNotes() {
        when(noteRepository.findAll()).thenReturn(Arrays.asList(note1, note2));

        List<Note> notes = noteService.getAllNotes();

        assertEquals(2, notes.size());
        assertEquals("Note 1", notes.get(0).getTitle());
        assertEquals("Note 2", notes.get(1).getTitle());
    }

    @Test
    void testGetNoteById() {
        when(noteRepository.findById(1L)).thenReturn(Optional.of(note1));

        Optional<Note> note = noteService.getNoteById(1L);

        assertTrue(note.isPresent());
        assertEquals("Note 1", note.get().getTitle());
    }

    @Test
    void testCreateNote() {
        when(noteRepository.save(any(Note.class))).thenReturn(note1);

        Note createdNote = noteService.createNote(note1);

        assertNotNull(createdNote);
        assertEquals("Note 1", createdNote.getTitle());
    }

    @Test
    void testUpdateNote() {
        Note updatedNoteDetails = new Note();
        updatedNoteDetails.setTitle("Updated Note 1");
        updatedNoteDetails.setContent("Updated Content");

        when(noteRepository.findById(1L)).thenReturn(Optional.of(note1));
        when(noteRepository.save(any(Note.class))).thenReturn(note1);

        Note updatedNote = noteService.updateNote(1L, updatedNoteDetails);

        assertNotNull(updatedNote);
        assertEquals("Updated Note 1", updatedNote.getTitle());
        assertEquals("Updated Content", updatedNote.getContent());
    }

    @Test
    void testUpdateNoteNotFound() {
        Note updatedNoteDetails = new Note();
        updatedNoteDetails.setTitle("Updated Note 1");
        updatedNoteDetails.setContent("Updated Content");

        when(noteRepository.findById(3L)).thenReturn(Optional.empty());

        Note updatedNote = noteService.updateNote(3L, updatedNoteDetails);

        assertNull(updatedNote);
    }

    @Test
    void testDeleteNoteById() {
        doNothing().when(noteRepository).deleteById(1L);

        noteService.deleteNoteById(1L);

        verify(noteRepository, times(1)).deleteById(1L);
    }
}
