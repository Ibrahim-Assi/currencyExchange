package com.ex.repositories.gl;

import com.ex.models.entities.AttachmentFiles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttachmentFilesRepository extends JpaRepository<AttachmentFiles,Long> {
}
