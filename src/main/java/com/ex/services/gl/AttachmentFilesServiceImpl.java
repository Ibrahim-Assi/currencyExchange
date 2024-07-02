package com.ex.services.gl;

import com.ex.common.tools.ValidateTool;
import com.ex.exceptions.ServiceException;
import com.ex.exceptions.ServiceResponse;
import com.ex.exceptions.ServiceResult;
import com.ex.models.dto.AttachmentFilesDTO;
import com.ex.models.entities.AttachmentFiles;
import com.ex.models.entities.FileReference;
import com.ex.models.enums.EnBucketStorageName;
import com.ex.repositories.gl.AttachmentFilesRepository;
import com.ex.repositories.gl.FileReferenceRepository;
import com.ex.services.api.ObjectStorageService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AttachmentFilesServiceImpl implements AttachmentFilesService {

    private final AttachmentFilesRepository attachmentFilesRepository;
    private final ObjectStorageService objectStorageService;
    private final FileReferenceRepository fileReferenceRepository;


    @Value("${attachmentFiles.path}")
    private String AttachmentFilesPath;

    //---------------------------------------------------------------------------------------------------
    @Override
    public ServiceResponse saveAttachment(MultipartFile file) {
        try {

            AttachmentFiles attachmentEntity = new AttachmentFiles();
                String fileName = file.getOriginalFilename();
                attachmentEntity.setFileName(fileName);
                attachmentEntity.setFileContentType(file.getContentType());
                attachmentEntity.setFileSize(file.getSize());
                attachmentEntity.setFileRefId(objectStorageService.uploadFile(EnBucketStorageName.ATTACHMENT_PROJECT, file));
                attachmentFilesRepository.save(attachmentEntity);

//                AttachmentFiles savedFileEntity = attachmentFilesRepository.save(attachmentEntity);
//                Path filePath = Paths.get(AttachmentFilesPath, savedFileEntity.getId() + "" + new Random().nextInt(9000) + fileName.substring(fileName.lastIndexOf(".")));
//                Files.copy(file.getInputStream(), filePath);
//                attachmentEntity.setFilePath(filePath.toString());
//                attachmentFilesRepository.save(attachmentEntity);

            return new ServiceResult("The file was saved successfully");
             
        }
        catch (Exception e){
            System.err.println("Error in AttachmentFilesServiceImpl.saveAttachment:"+e);
        }

        return new ServiceException("Failed to save the file successfully");

    }



    //---------------------------------------------------------------------------------------------------
    @Override
    public List<AttachmentFilesDTO> getFilesList() {
        ModelMapper modelMapper = new ModelMapper();
        List<AttachmentFiles> optionalFiles = attachmentFilesRepository.findAll();

        if (ValidateTool.isEmpty(optionalFiles))
            return null; 

        return optionalFiles.stream().map(file ->
                modelMapper.map(file, AttachmentFilesDTO.class)).toList();
    }

    //---------------------------------------------------------------------------------------------------
    @Override
    public AttachmentFilesDTO getFile(Long id) {
        ModelMapper modelMapper = new ModelMapper();
        AttachmentFilesDTO dto = null;
        Optional<AttachmentFiles>  optionalFile  =   attachmentFilesRepository.findById(id);
        if (optionalFile.isPresent()) { 
            dto = modelMapper.map(optionalFile.get(), AttachmentFilesDTO.class);
        }
        return  dto;
    }

    //---------------------------------------------------------------------------------------------------
    @Override
    public ServiceResponse deleteAttachment(Long id) {
        try{
            Optional<AttachmentFiles>  optionalFile  =   attachmentFilesRepository.findById(id);
            if (optionalFile.isPresent()) {
                AttachmentFiles attachmentFile = optionalFile.get();

                Optional<FileReference> fileRef = fileReferenceRepository.findById(attachmentFile.getFileRefId());

                if (fileRef.isPresent()) {
                    FileReference fileReference = fileRef.get();
                    objectStorageService.deleteFile(EnBucketStorageName.ATTACHMENT_PROJECT, fileReference.getObjectName());
                }

//                objectStorageService.deleteFile(EnBucketStorageName.ATTACHMENT_PROJECT, attachmentFile.get());
//                Path filePath = Paths.get(attachmentFile.getFilePath());
//                // Delete the file from the file system
//                Files.deleteIfExists(filePath);
                // Delete the file record from the database
                attachmentFilesRepository.deleteById(id);
            }
            return new ServiceResult("The file has been deleted successfully");
        }
        catch (Exception e){
            System.err.println("AttachmentFilesServiceImpl.deleteAttachment:"+e);
            return new ServiceException("common.message.globalExceptionMsg");
        }
    }
}
