package com.ex.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Audited
@Table(name = "attachment_files")
public class AttachmentFiles  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_id", length = 10)
    private Long id;
    @Column(name = "file_desc", length = 1000)
    private String fileDesc;
    @Column(name = "file_name", length = 200)
    private String fileName;
    @Column(name = "file_content_type", length = 50)
    private String fileContentType;
    @Column(name = "file_path", length = 100)
    private String filePath;
    @Column(name = "file_size", length = 5)
    private Long fileSize;
    @Column(name = "attachment_url", length = 500)
    private String attachmentUrl;


}
