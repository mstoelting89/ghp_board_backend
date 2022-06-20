package com.example.system.attachment;

import lombok.AllArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.webjars.NotFoundException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Base64;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AttachmentServiceImpl implements AttachmentService{

    private AttachmentRepository attachmentRepository;

    @Override
    public Attachment handelAttachmentUpload(Optional<MultipartFile> file) throws IOException {
        if (file.isPresent()) {
            String ext = file.get().getOriginalFilename().substring(file.get().getOriginalFilename().lastIndexOf(".") + 1);
            String fileName = String.format("%s.%s", RandomStringUtils.randomAlphanumeric(8), ext);

            String uploadDir = "/upload/images/";
            saveFile(uploadDir, fileName, file.get());
            return attachmentRepository.save(new Attachment(fileName));
        } else {
            return null;
        }
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
            image = FileUtils.readFileToByteArray(new File("/upload/images/" + fileName.getLocation()));
            encodedString = Base64.getEncoder().encodeToString(image);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return encodedString;
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
            throw new IOException("Image could not be saved");
        }
    }
}
