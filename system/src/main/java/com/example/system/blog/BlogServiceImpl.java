package com.example.system.blog;

import com.example.system.attachment.Attachment;
import com.example.system.attachment.AttachmentResponse;
import com.example.system.attachment.AttachmentService;
import com.example.system.demand.DemandEntryDto;
import com.example.system.email.EmailService;
import com.example.system.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.webjars.NotFoundException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BlogServiceImpl implements BlogService {
    private BlogRespository blogRespository;
    private AttachmentService attachmentService;
    private UserService userService;

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
                    attachments,
                    item.getIsPublic()
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

        if (blogEntryDto.getIsPublic()) {
            userService.sendToAll("new-blog-article", "Guitar Hearts Project: Neuer Blogartikel");
        }

        return blogRespository.save(new Blog(
                blogEntryDto.getBlogDate(),
                blogEntryDto.getBlogTitle(),
                blogEntryDto.getBlogText(),
                blogEntryDto.getBlogAuthor(),
                attachments,
                blogEntryDto.getIsPublic()
        ));
    }

    @Override
    public void deleteBlogEntry(Long id) {
        var blogEntry = blogRespository.findById(id)
                .orElseThrow(() -> new NotFoundException("Löschen fehlgeschlagen - Eintrag mit der ID " + id + " nicht gefunden"));

        blogRespository.delete(blogEntry);
    }

    @Override
    public Blog updateBlogEntry(BlogEntryDto blogNewDto, Long blogId, Optional<List<MultipartFile>> files) {
        var blogPreviousEntry = blogRespository.findById(blogId)
                .orElseThrow(() -> new NotFoundException("Kein Eintrag mit der Id " + blogId + " gefunden"));
        var newImageIds = new ArrayList<>();
        var toDeleteImages = new ArrayList<Attachment>();
        var existingAttachments = new ArrayList<Attachment>();

        if (
                blogNewDto.getBlogTitle() == null ||
                blogNewDto.getBlogText() == null
        ) {
            throw new NotFoundException("Speichern fehlgeschlagen - Eintrag nicht vollständig");
        }

        blogNewDto.getBlogImages().forEach(newImage -> {
            newImageIds.add(newImage.getId());
        });

        blogPreviousEntry.getBlogImages().forEach(item -> {
            if (!newImageIds.contains(item.getId())) {
                toDeleteImages.add(item);
            } else {
                existingAttachments.add(item);
            }
        });

        toDeleteImages.forEach(deleteImage -> {
            blogPreviousEntry.getBlogImages().remove(deleteImage);
            blogRespository.save(blogPreviousEntry);

            try {
                attachmentService.deleteImage(deleteImage.getId());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        // handle new images
        List<MultipartFile> fileList = files.orElse(Collections.emptyList());
        List<Attachment> newAttachments = attachmentService.handleAttachmentUploadList(fileList);

        // merge both attachment arrays (new and old)
        existingAttachments.addAll(newAttachments);

        blogPreviousEntry.setBlogDate(blogNewDto.getBlogDate());
        blogPreviousEntry.setBlogTitle(blogNewDto.getBlogTitle());
        blogPreviousEntry.setBlogAuthor(blogNewDto.getBlogAuthor());
        blogPreviousEntry.setBlogText(blogNewDto.getBlogText());
        blogPreviousEntry.setBlogImages(existingAttachments);
        blogPreviousEntry.setIsPublic(blogNewDto.getIsPublic());

        if (blogPreviousEntry.getIsPublic()) {
            userService.sendToAll("new-blog-article", "Guitar Hearts Project: Neuer Blogartikel");
        }

        return blogRespository.save(blogPreviousEntry);

    }
}
