package com.example.system.blog;

import com.example.system.attachment.Attachment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Blog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime blogDate;

    @Column(nullable = false)
    private String blogTitle;

    @Column(nullable = false)
    private String blogText;

    @Column(nullable = false)
    private String blogAuthor;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Attachment> blogImages;

    private Boolean isPublic;

    public Blog(LocalDateTime blogDate, String blogTitle, String blogText, String blogAuthor, List<Attachment> attachments, Boolean isPublic) {
        this.blogDate = blogDate;
        this.blogTitle = blogTitle;
        this.blogText = blogText;
        this.blogAuthor = blogAuthor;
        this.blogImages = attachments;
        this.isPublic = isPublic;
    }
}
