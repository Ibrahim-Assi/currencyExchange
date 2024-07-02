package com.ex.controllers.gl;

import com.ex.exceptions.ServiceResponse;
import com.ex.models.dto.AttachmentFilesDTO;
import com.ex.models.entities.FileReference;
import com.ex.models.enums.EnBucketStorageName;
import com.ex.repositories.gl.FileReferenceRepository;
import com.ex.services.api.ObjectStorageService;
import com.ex.services.gl.AttachmentFilesService;
import org.springframework.http.MediaType;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/files/")
@RequiredArgsConstructor
public class AttachmentFilesController { 
    private final AttachmentFilesService attachmentFilesService;
    private final ObjectStorageService objectStorageService;

    private final FileReferenceRepository fileReferenceRepository;


    //-------------------------------------------------------------------
    @GetMapping("/viewFiles")
    public String viewFiles(Model model) {
        List<AttachmentFilesDTO> files = attachmentFilesService.getFilesList();
        model.addAttribute("files", files);
        return "views/files/viewFiles";
    }

    //-------------------------------------------------------------------
    @PostMapping("/addFile")
    public String addFile(@RequestParam("attachmentFile") MultipartFile attachmentFile, RedirectAttributes redirectAttributes) {
            ServiceResponse responseUploadFile = attachmentFilesService.saveAttachment(attachmentFile);
            redirectAttributes.addFlashAttribute(responseUploadFile.getMessageType(),responseUploadFile.getMessage());
        return "redirect:/admin/files/viewFiles";
    }

    //-------------------------------------------------------------------
    @GetMapping("/downloadFile/{id}")
    @ResponseBody
    public ResponseEntity<Resource> downloadFile(@PathVariable Long id) {
        AttachmentFilesDTO fileDto = attachmentFilesService.getFile(id);
//        if (fileDto == null) {
//            throw new IllegalArgumentException("Unauthorized to download this file");
//        }
//        Resource file = new FileSystemResource(fileDto.getFilePath());
//        return ResponseEntity.ok()
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDto.getFileName() + "\"")
//                .body(file);


        Optional<FileReference> fileRef = fileReferenceRepository.findById(fileDto.getFileRefId());

        if (fileRef.isPresent()) {
            FileReference fileReference = fileRef.get();
            InputStream inputStream = objectStorageService.downloadFile(EnBucketStorageName.ATTACHMENT_PROJECT, fileReference.getObjectName());
            InputStreamResource resource = new InputStreamResource(inputStream);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + fileReference.getObjectName())
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource);
        }


        return ResponseEntity.notFound().build();
    }

    //-------------------------------------------------------------------
    @GetMapping("/deleteAttachment/{id}")
    public String deleteAttachment(@PathVariable Long id,  RedirectAttributes redirectAttributes) {
        ServiceResponse response = attachmentFilesService.deleteAttachment(id);
        redirectAttributes.addFlashAttribute(response.getMessageType(), response.getMessage());
        return "redirect:/admin/files/viewFiles";
    }
  

}
