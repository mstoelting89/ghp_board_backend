package com.example.system.news;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
public class NewsEntryDto {
    private Long id;
    private String newsTitle;
    private String newsText;
    private LocalDateTime newsDate;
    private String newsAuthor;
    private String newsImage;
}
