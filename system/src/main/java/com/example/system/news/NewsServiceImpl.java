package com.example.system.news;

import com.example.system.attachment.AttachmentService;
import com.example.system.config.GhpProperties;
import com.example.system.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.webjars.NotFoundException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@EnableConfigurationProperties(GhpProperties.class)
public class NewsServiceImpl implements NewsService{

    private NewsRepository newsRepository;
    private AttachmentService attachmentService;
    private UserService userService;
    private final GhpProperties ghpProperties;


    public List<News> getAllNewsEntries() {
        return newsRepository.findAllByOrderByNewsDateDesc();
    }

    public NewsEntryDto getNewsEntry(Long id) {
        var newsEntry = newsRepository.findById(id).orElseThrow(() -> new NotFoundException("Newsentry not found"));
        var attachment = "";
        if (newsEntry.getNewsImage() != null) {
            attachment = attachmentService.getAttachmentAsBase64(newsEntry.getNewsImage().getId());
        }

        return new NewsEntryDto(
                newsEntry.getId(),
                newsEntry.getNewsTitle(),
                newsEntry.getNewsText(),
                newsEntry.getNewsDate(),
                newsEntry.getNewsAuthor(),
                attachment
        );
    }

    @Transactional
    public News insertNewNewsEntry(NewsEntryDto newsEntryDto, Optional<MultipartFile> file) throws IOException {

        if(
                newsEntryDto.getNewsAuthor() == null ||
                newsEntryDto.getNewsTitle() == null ||
                newsEntryDto.getNewsText() == null
        ) {
            throw new NotFoundException("Speichern fehlgeschlagen - Eintrag nicht vollständig");
        }

        userService.sendToAll("new-news-entry", "Guitar Hearts Project: Neuigkeiten auf dem Board");

        if (file.isPresent()) {

            if(!Files.isDirectory(Paths.get(ghpProperties.getUploadDir()))) {
                new File(ghpProperties.getUploadDir()).mkdirs();
            }

            var attachment = attachmentService.handelAttachmentUpload(
                    file.orElseThrow(() -> new IllegalStateException("Beim Hochladen ist ein Fehler aufgetreten")),
                    ghpProperties.getUploadDir()
            );
            return newsRepository.save(new News(
                    newsEntryDto.getNewsDate(),
                    newsEntryDto.getNewsTitle(),
                    newsEntryDto.getNewsText(),
                    newsEntryDto.getNewsAuthor(),
                    attachment
            ));
        } else {
            return newsRepository.save(new News(
                    newsEntryDto.getNewsDate(),
                    newsEntryDto.getNewsTitle(),
                    newsEntryDto.getNewsText(),
                    newsEntryDto.getNewsAuthor(),
                    null
            ));
        }


    }

    @Override
    public News updateNewsEntry(Long newsId, NewsEntryDto newsUpdateDto, Optional<MultipartFile> file, boolean newsImageDelete) throws IOException {
        var newsEntry = newsRepository.findById(newsId)
                .orElseThrow(() -> new NotFoundException("Kein Eintrag mit der Id " + newsId + " gefunden"));

        if (newsEntry.getNewsImage() != null && newsImageDelete) {
            newsRepository.deleteImage(newsId);
            attachmentService.deleteImage(newsEntry.getNewsImage().getId());
        }

        if(
                newsUpdateDto.getNewsAuthor() == null ||
                newsUpdateDto.getNewsTitle() == null ||
                newsUpdateDto.getNewsText() == null
        ) {
            throw new NotFoundException("Speichern fehlgeschlagen - Eintrag nicht vollständig");
        }

        newsEntry.setNewsTitle(newsUpdateDto.getNewsTitle());
        newsEntry.setNewsDate(newsUpdateDto.getNewsDate());
        newsEntry.setNewsAuthor(newsUpdateDto.getNewsAuthor());
        newsEntry.setNewsText(newsUpdateDto.getNewsText());
        if (file.isPresent()) {
            var attachment = attachmentService.handelAttachmentUpload(
                    file.orElseThrow(() -> new IllegalStateException("Beim Hochladen ist ein Fehler aufgetreten")),
                    ghpProperties.getUploadDir()
            );
            newsEntry.setNewsImage(attachment);
        } else if (newsImageDelete) {
            newsEntry.setNewsImage(null);
        }

        return newsRepository.save(newsEntry);
    }

    @Override
    public void deleteNewsEntry(Long newsId) throws IOException {
        var newsEntry = newsRepository.findById(newsId)
                .orElseThrow(() -> new NotFoundException("Löschen fehlgeschlagen - Eintrag mit der ID " + newsId + " nicht gefunden"));
        newsRepository.delete(newsEntry);
    }
}
