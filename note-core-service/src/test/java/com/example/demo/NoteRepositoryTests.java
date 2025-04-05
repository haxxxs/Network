package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@ActiveProfiles("test")  // Указываем использование профиля test
@AutoConfigureTestDatabase(replace = Replace.ANY)
public class NoteRepositoryTests {

    @Autowired
    private NoteRepository noteRepository;

    @BeforeEach
    public void setUp() {
        noteRepository.deleteAll();
    }

    @Test
    @Rollback(false)
    public void testSaveNote() {
        Note note = new Note();
        note.setTitle("Test Note");
        note.setContent("This is a test note.");
        Note savedNote = noteRepository.save(note);
        assertThat(savedNote).isNotNull();
        assertThat(savedNote.getId()).isGreaterThan(0);
    }

    @Test
    public void testFindNoteById() {
        Note note = new Note();
        note.setTitle("Test Note");
        note.setContent("This is a test note.");
        Note savedNote = noteRepository.save(note);
        Optional<Note> foundNote = noteRepository.findById(savedNote.getId());
        assertThat(foundNote).isPresent();
        assertEquals(savedNote.getTitle(), foundNote.get().getTitle());
    }

    @Test
    public void testFindAllNotes() {
        Note note1 = new Note();
        note1.setTitle("Note 1");
        note1.setContent("Content 1");
        noteRepository.save(note1);

        Note note2 = new Note();
        note2.setTitle("Note 2");
        note2.setContent("Content 2");
        noteRepository.save(note2);

        List<Note> notes = noteRepository.findAll();
        assertThat(notes).isNotNull();
        assertEquals(2, notes.size());
    }

    @Test
    @Transactional
    public void testUpdateNote() {
        Note note = new Note();
        note.setTitle("Old Title");
        note.setContent("Old Content");
        Note savedNote = noteRepository.save(note);

        savedNote.setTitle("New Title");
        savedNote.setContent("New Content");
        Note updatedNote = noteRepository.save(savedNote);

        assertEquals("New Title", updatedNote.getTitle());
        assertEquals("New Content", updatedNote.getContent());
    }

    @Test
    public void testDeleteNote() {
        Note note = new Note();
        note.setTitle("Test Note");
        note.setContent("This is a test note.");
        Note savedNote = noteRepository.save(note);

        noteRepository.delete(savedNote);
        Optional<Note> foundNote = noteRepository.findById(savedNote.getId());
        assertThat(foundNote).isNotPresent();
    }
}
