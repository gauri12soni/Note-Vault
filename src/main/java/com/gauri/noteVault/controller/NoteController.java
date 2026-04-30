package com.gauri.noteVault.controller;

import com.gauri.noteVault.dto.NoteRequestDTO;
import com.gauri.noteVault.dto.NoteResponseDTO;
import com.gauri.noteVault.entity.User;
import com.gauri.noteVault.repository.UserRepository;
import com.gauri.noteVault.service.NoteService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/notes")
public class NoteController {

    private static final Logger logger = LoggerFactory.getLogger(NoteController.class);

    private final NoteService noteService;
    private final UserRepository userRepository;

    @Autowired
    public NoteController(NoteService noteService, UserRepository userRepository) {
        this.noteService = noteService;
        this.userRepository = userRepository;
    }

    // Create a new note
    @PostMapping
    public ResponseEntity<NoteResponseDTO> createNote(@Valid @RequestBody NoteRequestDTO dto) {
        UUID userId = getCurrentUserId();
        logger.info("Creating note for user ID: {}", userId);
        NoteResponseDTO created = noteService.createNote(dto, userId);
        logger.info("Note created successfully for user ID: {}", userId);
        return ResponseEntity.ok(created);
    }

    // Get a note by its ID
    @GetMapping("/{id}")
    public ResponseEntity<NoteResponseDTO> getNote(@PathVariable Long id) {
        UUID userId = getCurrentUserId();
        logger.info("Fetching note with ID: {} for user ID: {}", id, userId);
        NoteResponseDTO note = noteService.getById(id, userId);
        return ResponseEntity.ok(note);
    }

    // Update an existing note
    @PutMapping("/{id}")
    public ResponseEntity<NoteResponseDTO> updateNote(@PathVariable Long id, @Valid @RequestBody NoteRequestDTO dto) {
        UUID userId = getCurrentUserId();
        logger.info("Updating note with ID: {} for user ID: {}", id, userId);
        NoteResponseDTO updated = noteService.update(id, dto, userId);
        logger.info("Note updated successfully for user ID: {}", userId);
        return ResponseEntity.ok(updated);
    }

    // Delete a note by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNote(@PathVariable Long id) {
        UUID userId = getCurrentUserId();
        logger.info("Deleting note with ID: {} for user ID: {}", id, userId);
        noteService.delete(id, userId);
        logger.info("Note deleted successfully for user ID: {}", userId);
        return ResponseEntity.noContent().build();
    }

    // List all notes (supports optional search and pagination)
    @GetMapping
    public ResponseEntity<Page<NoteResponseDTO>> listNotes(
            @RequestParam(value = "q", required = false) String query,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        UUID userId = getCurrentUserId();
        Pageable pageable = PageRequest.of(page, size);
        logger.info("Listing notes for user ID: {} (page: {}, size: {}, query: {})", userId, page, size, query);
        Page<NoteResponseDTO> notes = noteService.list(userId, query, pageable);
        return ResponseEntity.ok(notes);
    }

    // Get the current authenticated user's ID from JWT
    private UUID getCurrentUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            logger.error("Access denied - user not authenticated");
            throw new RuntimeException("User not authenticated");
        }
        String username = auth.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return user.getId();
    }
}