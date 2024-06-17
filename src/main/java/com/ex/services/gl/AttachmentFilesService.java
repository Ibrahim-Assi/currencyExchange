package com.ex.services.gl;

import com.ex.exceptions.ServiceResponse;
import com.ex.models.dto.AttachmentFilesDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AttachmentFilesService {

    public ServiceResponse saveAttachment(MultipartFile file);
    public AttachmentFilesDTO getFile(Long id);
    public ServiceResponse deleteAttachment(Long id);
    public List<AttachmentFilesDTO> getFilesList();
}
