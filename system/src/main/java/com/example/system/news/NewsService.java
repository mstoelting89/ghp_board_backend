package com.example.system.news;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface NewsService {

    List<News> getAllNewsEntries();

    NewsEntryDto getNewsEntry(Long id);

    News insertNewNewsEntry(NewsEntryDto newsEntryDto, Optional<MultipartFile> file) throws IOException;

    News updateNewsEntry(Long newsId, NewsEntryDto newsUpdateDto, Optional<MultipartFile> file, boolean newsImageDelete) throws IOException;

    void deleteNewsEntry(Long newsId) throws IOException;

}
