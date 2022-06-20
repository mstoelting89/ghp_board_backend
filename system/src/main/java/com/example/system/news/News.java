package com.example.system.news;

import com.example.system.attachment.Attachment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime newsDate;

    @Column(nullable = false)
    private String newsTitle;

    @Column(nullable = false)
    private String newsText;

    @Column(nullable = false)
    private String newsAuthor;

    @OneToOne
    private Attachment newsImage;

    public News (LocalDateTime newsDate, String newsTitle, String newsText, String newsAuthor, Attachment newsImage) {
        this.newsDate = newsDate;
        this.newsTitle = newsTitle;
        this.newsText = newsText;
        this.newsAuthor = newsAuthor;
        this.newsImage = newsImage;

    }
}
