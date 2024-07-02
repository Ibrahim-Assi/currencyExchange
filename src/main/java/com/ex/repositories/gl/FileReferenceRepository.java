package com.ex.repositories.gl;

import com.ex.models.entities.FileReference;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FileReferenceRepository extends JpaRepository<FileReference, Long> {
    Optional<FileReference> findByObjectName(String objectName);
}
