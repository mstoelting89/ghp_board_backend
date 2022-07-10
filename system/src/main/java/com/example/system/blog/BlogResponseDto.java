package com.example.system.blog;

import com.example.system.attachment.Attachment;
import com.example.system.attachment.AttachmentResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class BlogResponseDto {
    private Long id;
    private LocalDateTime blogDate;
    private String blogTitle;
    private String blogText;
    private String blogAuthor;
    private List<AttachmentResponse> blogImages;
    private Boolean isPublic;
}

