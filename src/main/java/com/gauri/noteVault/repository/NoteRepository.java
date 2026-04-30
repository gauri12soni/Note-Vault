package com.gauri.noteVault.repository;

import com.gauri.noteVault.entity.Note;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface NoteRepository extends JpaRepository<Note, Long> {


    @Query("SELECT n FROM Note n WHERE n.user.id = :userId")
    Page<Note> findByUserId(@Param("userId") UUID userId, Pageable pageable);


    @Query("""
            SELECT n FROM Note n
            WHERE n.user.id = :userId AND
            (LOWER(n.title) LIKE LOWER(CONCAT('%', :query, '%'))
             OR LOWER(n.content) LIKE LOWER(CONCAT('%', :query, '%')))
            """)
    Page<Note> searchByUserIdAndQuery(@Param("userId") UUID userId,
                                      @Param("query") String query,
                                      Pageable pageable);

    @Query("SELECT n FROM Note n WHERE n.id = :id AND n.user.id = :userId")
    Optional<Note> findByIdAndUserId(@Param("id") Long id,
                                     @Param("userId") UUID userId);
}