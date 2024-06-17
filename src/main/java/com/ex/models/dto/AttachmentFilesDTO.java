package com.ex.models.dto;

 import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttachmentFilesDTO {
    private Long id;
    private String fileDesc;
    private String fileName;
    private String fileContentType;
    private String filePath;
    private Long   fileSize;
    private String attachmentUrl;


}
