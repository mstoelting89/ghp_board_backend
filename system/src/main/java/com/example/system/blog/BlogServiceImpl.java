package com.example.system.blog;

import com.example.system.attachment.Attachment;
import com.example.system.attachment.AttachmentResponse;
import com.example.system.attachment.AttachmentService;
import com.example.system.demand.Demand;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.webjars.NotFoundException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BlogServiceImpl implements BlogService {
    private BlogRespository blogRespository;
    private AttachmentService attachmentService;

    @Override
    public List<BlogResponseDto> getAllBlogPosts() {
        List<BlogResponseDto> blogResponseDto = new ArrayList<>();
        blogRespository.findAllByOrderByBlogDateDesc().forEach(item -> {

            List<AttachmentResponse> attachments = attachmentService.getAttachmentListAsBase64(item.getId(), item.getBlogImages());
            blogResponseDto.add(new BlogResponseDto(
                    item.getId(),
                    item.getBlogDate(),
                    item.getBlogTitle(),
                    item.getBlogText(),
                    item.getBlogAuthor(),
                    attachments
            ));
        });

        return blogResponseDto;
    }

    @Override
    public Blog insertNewBlogEntry(BlogEntryDto blogEntryDto, Optional<List<MultipartFile>> file) {
        List<MultipartFile> fileList = file.orElse(Collections.emptyList());
        List<Attachment> attachments = attachmentService.handleAttachmentUploadList(fileList);

        if( blogEntryDto.getBlogAuthor() == null ||
                blogEntryDto.getBlogText() == null ||
                blogEntryDto.getBlogTitle() == null
        ) {
            throw new NotFoundException("Speichern fehlgeschlagen - Eintrag nicht vollständig");
        }
        return blogRespository.save(new Blog(
                blogEntryDto.getBlogDate(),
                blogEntryDto.getBlogTitle(),
                blogEntryDto.getBlogText(),
                blogEntryDto.getBlogAuthor(),
                attachments
        ));
    }
}
