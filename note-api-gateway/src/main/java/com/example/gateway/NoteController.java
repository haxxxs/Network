package com.example.gateway;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.Map;


@Controller
public class NoteController {

	@Value("${target.service.url}")
	private String targetServiceUrl;

	private final RestTemplate restTemplate;

	public NoteController(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	@GetMapping("/notes")
	public ResponseEntity<String> getNotes() {
		String response = restTemplate.getForObject(targetServiceUrl, String.class);
		return ResponseEntity.ok(response);
	}

	@PostMapping("/notes")
	public ResponseEntity<String> createNote(@RequestBody String note) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<>(note, headers);

		String response = restTemplate.postForObject(targetServiceUrl, request, String.class);
		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/notes/{id}")
	public ResponseEntity<Void> deleteNote(@PathVariable Long id) {
		restTemplate.delete(targetServiceUrl + "/" + id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/notes/{id}")
	public ResponseEntity<String> getNoteById(@PathVariable Long id) {
		String response = restTemplate.getForObject(targetServiceUrl + "/" + id, String.class);
		return ResponseEntity.ok(response);
	}

	@PutMapping("/notes/{id}")
	public ResponseEntity<String> updateNote(@PathVariable Long id, @RequestBody Map<String, String> note) {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<Map<String, String>> request = new HttpEntity<>(note, headers);

			restTemplate.put(targetServiceUrl + "/" + id, request);
			return ResponseEntity.ok("Note updated successfully.");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(500).body("Error updating note: " + e.getMessage());
		}
	}
}
