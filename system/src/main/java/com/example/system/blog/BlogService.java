package com.example.system.blog;

import com.example.system.demand.Demand;
import com.example.system.demand.DemandEntryDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface BlogService {
    List<BlogResponseDto> getAllBlogPosts();

    Blog insertNewBlogEntry(BlogEntryDto blogEntryDto, Optional<List<MultipartFile>> file);

    void deleteBlogEntry(Long id);
}
