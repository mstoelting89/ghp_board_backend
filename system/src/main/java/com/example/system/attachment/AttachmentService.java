package com.example.system.attachment;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface AttachmentService {

    Attachment handelAttachmentUpload(Optional<MultipartFile> file) throws IOException;

    List<Attachment> handleAttachmentUploadList(List<MultipartFile> files);

    Attachment getAttachment(Long id);

    String getAttachmentAsBase64(Long id);

    List<AttachmentResponse> getAttachmentListAsBase64(Long id);

    void deleteImage(Long id) throws IOException;
}
