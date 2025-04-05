package com.example.gateway;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class NoteControllerTests {

	@Mock
	private RestTemplate restTemplate;

	@InjectMocks
	private NoteController noteController;

	@Value("${target.service.url}")
	private String targetServiceUrl;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testGetNotes() {
		String expectedResponse = "Sample Notes";
		when(restTemplate.getForObject(targetServiceUrl, String.class)).thenReturn(expectedResponse);

		ResponseEntity<String> response = noteController.getNotes();
		assertEquals(expectedResponse, response.getBody());
	}

	@Test
	public void testCreateNote() {
		String note = "Sample Note";
		String expectedResponse = "Note Created";
		when(restTemplate.postForObject(eq(targetServiceUrl), any(HttpEntity.class), eq(String.class))).thenReturn(expectedResponse);

		ResponseEntity<String> response = noteController.createNote(note);
		assertEquals(expectedResponse, response.getBody());
	}

	@Test
	public void testDeleteNote() {
		doNothing().when(restTemplate).delete(targetServiceUrl + "/1");

		ResponseEntity<Void> response = noteController.deleteNote(1L);
		assertEquals(204, response.getStatusCodeValue());
	}

	@Test
	public void testGetNoteById() {
		String expectedResponse = "Sample Note";
		when(restTemplate.getForObject(targetServiceUrl + "/1", String.class)).thenReturn(expectedResponse);

		ResponseEntity<String> response = noteController.getNoteById(1L);
		assertEquals(expectedResponse, response.getBody());
	}

	@Test
	public void testUpdateNote() {
		Map<String, String> note = new HashMap<>();
		note.put("title", "Updated Title");
		note.put("content", "Updated Content");

		doNothing().when(restTemplate).put(eq(targetServiceUrl + "/1"), any(HttpEntity.class));

		ResponseEntity<String> response = noteController.updateNote(1L, note);

		assertEquals("Note updated successfully.", response.getBody());
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
}
