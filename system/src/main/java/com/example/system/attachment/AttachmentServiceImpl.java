package com.example.system.attachment;

import lombok.AllArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.webjars.NotFoundException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
public class AttachmentServiceImpl implements AttachmentService {

    private AttachmentRepository attachmentRepository;

    @Override
    public Attachment handelAttachmentUpload(MultipartFile file, String uploadDir) throws IOException {

        String ext = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
        String fileName = String.format("%s.%s", RandomStringUtils.randomAlphanumeric(8), ext);

        saveFile(uploadDir, fileName, file);
        return attachmentRepository.save(new Attachment(uploadDir + fileName));

    }

    @Override
    public List<Attachment> handleAttachmentUploadList(List<MultipartFile> files, String uploadDir) {
        List<Attachment> fileList = new ArrayList<>();

        files.forEach(file -> {
            String ext = Objects.requireNonNull(file.getOriginalFilename()).substring(file.getOriginalFilename().lastIndexOf(".") + 1);
            String fileName = String.format("%s.%s", RandomStringUtils.randomAlphanumeric(8), ext);
            try {
                saveFile(uploadDir, fileName, file);
            } catch (IOException e) {
                e.printStackTrace();
            }

            fileList.add(attachmentRepository.save(new Attachment(uploadDir + fileName)));
        });

        return fileList;
    }

    @Override
    public Attachment getAttachment(Long id) {
        return attachmentRepository.findById(id).orElseThrow(() -> new NotFoundException("Bild nicht vorhanden"));
    }

    @Override
    public String getAttachmentAsBase64(Long id) {
        String encodedString = "";
        try {
            var fileName = attachmentRepository.findById(id).orElseThrow(() -> new NotFoundException("Bild nicht vorhanden"));
            byte[] image = new byte[0];
            image = FileUtils.readFileToByteArray(new File(fileName.getLocation()));
            encodedString = Base64.getEncoder().encodeToString(image);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return encodedString;
    }

    @Override
    public List<AttachmentResponse> getAttachmentListAsBase64(Long id, List<Attachment> attachments) {
        var encodedImages = new ArrayList<AttachmentResponse>();

        attachments.forEach(attachment -> {
            var attachmentResponse = new AttachmentResponse();
            attachmentResponse.setId(attachment.getId());
            byte[] image = new byte[0];
            try {
                image = FileUtils.readFileToByteArray(new File(attachment.getLocation()));
                attachmentResponse.setBase64(Base64.getEncoder().encodeToString(image));
                encodedImages.add(attachmentResponse);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        return encodedImages;
    }

    @Transactional
    public void deleteImage(Long id) throws IOException {
        var attachment = attachmentRepository.findById(id).orElseThrow(() -> new NotFoundException("Bild nicht vorhanden"));
        Path filePath = Paths.get(attachment.getLocation());

        if (!Files.exists(filePath)) {
            throw new NotFoundException("Datei nicht vorhanden");
        } else {
            try {
                Files.delete(filePath);
                attachmentRepository.delete(attachment);
            } catch (IOException e) {
                throw new IOException("Datei konnte nicht gelöscht werden");
            }
        }
    }

    private void saveFile(String uploadDir, String fileName, MultipartFile multipartFile) throws IOException {
        Path uploadPath = Paths.get(uploadDir);
        if(!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {
            throw new IOException("Image could not be saved: " + ioe.getMessage());
        }
    }
}
