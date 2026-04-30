package com.gauri.noteVault.service;

import com.gauri.noteVault.dto.NoteRequestDTO;
import com.gauri.noteVault.dto.NoteResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface NoteService {

    NoteResponseDTO createNote(NoteRequestDTO dto, UUID userId);
    NoteResponseDTO getById(Long id, UUID userId);
    NoteResponseDTO update(Long id, NoteRequestDTO dto, UUID userId);
    void delete(Long id, UUID userId);
    Page<NoteResponseDTO> list(UUID userId, String q, Pageable pageable);
}
