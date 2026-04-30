package com.gauri.noteVault.service;

import com.gauri.noteVault.dto.NoteRequestDTO;
import com.gauri.noteVault.dto.NoteResponseDTO;
import com.gauri.noteVault.entity.Note;
import com.gauri.noteVault.entity.User;
import com.gauri.noteVault.exception.ResourceNotFoundException;
import com.gauri.noteVault.repository.NoteRepository;
import com.gauri.noteVault.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;

@Service
public class NoteServiceImpl implements NoteService {

    private static final Logger logger = LoggerFactory.getLogger(NoteServiceImpl.class);

    private final NoteRepository noteRepository;
    private final UserRepository userRepository;

    public NoteServiceImpl(NoteRepository noteRepository, UserRepository userRepository) {
        this.noteRepository = noteRepository;
        this.userRepository = userRepository;
    }

    // Create a new note for a given user
    @Override
    public NoteResponseDTO createNote(NoteRequestDTO dto, UUID userId) {
        logger.debug("Creating note for user ID: {}", userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    logger.warn("User ID '{}' not found, cannot create note", userId);
                    return new ResourceNotFoundException("User not found");
                });

        Note note = new Note();
        note.setTitle(dto.getTitle());
        note.setContent(dto.getContent());
        note.setTags(dto.getTags() != null ? dto.getTags() : new ArrayList<>());
        note.setUser(user);

        Note saved = noteRepository.save(note);
        logger.info("Note created successfully for user: {}, Note ID: {}", user.getUsername(), saved.getId());

        return Mapper.toDto(saved);
    }

    // Retrieve a note by ID for a specific user
    @Override
    public NoteResponseDTO getById(Long id, UUID userId) {
        logger.debug("Fetching note ID {} for user ID: {}", id, userId);

        Note note = noteRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> {
                    logger.warn("Note ID {} not found for user ID: {}", id, userId);
                    return new ResourceNotFoundException("Note not found with id " + id);
                });

        logger.info("Note ID {} retrieved successfully", id);
        return Mapper.toDto(note);
    }

    // Update an existing note
    @Override
    public NoteResponseDTO update(Long id, NoteRequestDTO dto, UUID userId) {
        logger.debug("Updating note ID {} for user ID: {}", id, userId);

        Note note = noteRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> {
                    logger.warn("Note ID {} not found for user ID: {}", id, userId);
                    return new ResourceNotFoundException("Note not found with id " + id);
                });

        note.setTitle(dto.getTitle());
        note.setContent(dto.getContent());
        note.setTags(dto.getTags() != null ? dto.getTags() : new ArrayList<>());

        Note updated = noteRepository.save(note);
        logger.info("Note ID {} updated successfully", id);

        return Mapper.toDto(updated);
    }

    // Delete a note by ID for a specific user
    @Override
    public void delete(Long id, UUID userId) {
        logger.debug("Deleting note ID {} for user ID: {}", id, userId);

        Note note = noteRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> {
                    logger.warn("Note ID {} not found for user ID: {}", id, userId);
                    return new ResourceNotFoundException("Note not found with id " + id);
                });

        noteRepository.delete(note);
        logger.info("Note ID {} deleted successfully", id);
    }

    // List notes for a user with optional search and pagination
    @Override
    public Page<NoteResponseDTO> list(UUID userId, String q, Pageable pageable) {
        logger.debug("Listing notes for user ID: {}, search query: '{}'", userId, q);

        Page<Note> page;
        if (q == null || q.isBlank()) {
            page = noteRepository.findByUserId(userId, pageable);
        } else {
            page = noteRepository.searchByUserIdAndQuery(userId, q, pageable);
        }

        logger.info("Notes listed successfully, total notes: {}", page.getTotalElements());
        return page.map(Mapper::toDto);
    }
}