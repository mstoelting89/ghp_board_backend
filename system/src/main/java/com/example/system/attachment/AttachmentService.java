package com.example.system.attachment;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface AttachmentService {

    Attachment handelAttachmentUpload(MultipartFile file, String uploadDir) throws IOException;

    List<Attachment> handleAttachmentUploadList(List<MultipartFile> files, String uploadDir);

    Attachment getAttachment(Long id);

    String getAttachmentAsBase64(Long id);

    List<AttachmentResponse> getAttachmentListAsBase64(Long id, List<Attachment> attachments);

    void deleteImage(Long id) throws IOException;
}
