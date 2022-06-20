package com.example.system.attachment;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

public interface AttachmentService {

    Attachment handelAttachmentUpload(Optional<MultipartFile> file) throws IOException;

    Attachment getAttachment(Long id);

    String getAttachmentAsBase64(Long id);
}
