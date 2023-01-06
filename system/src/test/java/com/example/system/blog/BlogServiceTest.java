package com.example.system.blog;

import org.junit.jupiter.api.AfterEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.web.WebAppConfiguration;

@ActiveProfiles("test")
@WebAppConfiguration
@SpringBootTest
public class BlogServiceTest {
    @AfterEach
    public void clearTestDatabaseFromEntries() {
        
    }
    
    //TODO: shouldGetOneSpecificBlogEntry
    //TODO: shouldGetAllBlogEntries
    //TODO: shouldInsertNewBlogEntryWithImage
    //TODO: shouldInsertNewBlogEntryWithSeveralImages
    //TODO: shouldInsertNewBlogEntryWithoutImage
    //TODO: shouldReturnFailureIfDataIsMissingWhenInsertingData
    //TODO: shouldUpdateExistingBlogEntryWithDifferentData
    //TODO: shouldUpdateExistingBlogEntryWithNewImage
    //TODO: shouldUpdateExistingBlogEntryWithNewImageWhileHavingExistingImages
    //TODO: shouldRemoveImageFromExistingBlogEntry
    //TODO: shouldRemoveImageFromExistingBlogEntryWhileHavingSeveralExistingImages
    //TODO: shouldDeleteBlogEntryWithoutImage
    //TODO: shouldDeleteBlogEntryWithImage
    //TODO: shouldDeleteBlogEntryWithSeveralImages
}
