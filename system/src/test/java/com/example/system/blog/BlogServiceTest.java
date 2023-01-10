package com.example.system.blog;

import com.example.system.attachment.AttachmentResponse;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.multipart.MultipartFile;
import org.webjars.NotFoundException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@WebAppConfiguration
@SpringBootTest
public class BlogServiceTest {

    private static final String BLOG_AUTHOR = "Michael";
    private static final LocalDateTime BLOG_DATE = LocalDateTime.parse("2022-06-01T21:16:20");
    private static final String BLOG_TEXT = "Dies ist der Beispieltext";
    private static final String BLOG_TITEL = "Testtitel";
    private static final Boolean BLOG_IS_PUBLIC = true;

    private static final String BLOG_AUTHOR_2 = "Peter";
    private static final LocalDateTime BLOG_DATE_2 = LocalDateTime.parse("2022-06-12T21:16:20");
    private static final String BLOG_TEXT_2 = "Dies ist der Beispieltext 2";
    private static final String BLOG_TITEL_2 = "Testtitel 2";
    private static final Boolean BLOG_IS_PUBLIC_2 = false;

    @Autowired
    private BlogService blogService;

    @AfterEach
    public void clearTestDatabaseFromEntries() {
        var blogEntries = blogService.getAllBlogPosts();
        blogEntries.forEach(entry -> {
            blogService.deleteBlogEntry(entry.getId());
        });
    }

    @Test
    public void shouldGetAllBlogEntries() {
        // arrange
        var blogEntry = new BlogEntryDto();
        blogEntry.setBlogAuthor(BLOG_AUTHOR);
        blogEntry.setBlogDate(BLOG_DATE);
        blogEntry.setBlogText(BLOG_TEXT);
        blogEntry.setBlogTitle(BLOG_TITEL);
        blogEntry.setIsPublic(BLOG_IS_PUBLIC);
        Optional<List<MultipartFile>> image = Optional.empty();

        var blogEntry2 = new BlogEntryDto();
        blogEntry2.setBlogAuthor(BLOG_AUTHOR_2);
        blogEntry2.setBlogDate(BLOG_DATE_2);
        blogEntry2.setBlogText(BLOG_TEXT_2);
        blogEntry2.setBlogTitle(BLOG_TITEL_2);
        blogEntry2.setIsPublic(BLOG_IS_PUBLIC_2);
        Optional<List<MultipartFile>> image2 = Optional.empty();

        // act
        var insertFirstArticle = blogService.insertNewBlogEntry(blogEntry, image);
        var insertSecondArticle = blogService.insertNewBlogEntry(blogEntry2, image2);
        var result = blogService.getAllBlogPosts();

        // assert
        assertNotNull(result.get(1));
        assertEquals(result.get(1).getBlogAuthor(), BLOG_AUTHOR);
        assertEquals(result.get(1).getBlogDate(), BLOG_DATE);
        assertEquals(result.get(1).getBlogText(), BLOG_TEXT);
        assertEquals(result.get(1).getBlogTitle(), BLOG_TITEL);
        assertEquals(result.get(1).getIsPublic(), BLOG_IS_PUBLIC);

        assertNotNull(result.get(0));
        assertEquals(result.get(0).getBlogAuthor(), BLOG_AUTHOR_2);
        assertEquals(result.get(0).getBlogDate(), BLOG_DATE_2);
        assertEquals(result.get(0).getBlogText(), BLOG_TEXT_2);
        assertEquals(result.get(0).getIsPublic(), BLOG_IS_PUBLIC_2);
    }

    @Test
    public void shouldInsertNewBlogEntryWithImage() throws IOException {
        // arrange
        var blogEntry = new BlogEntryDto();
        blogEntry.setBlogAuthor(BLOG_AUTHOR);
        blogEntry.setBlogDate(BLOG_DATE);
        blogEntry.setBlogText(BLOG_TEXT);
        blogEntry.setBlogTitle(BLOG_TITEL);
        blogEntry.setIsPublic(BLOG_IS_PUBLIC);

        File file = new File("src/test/resources/upload/test_1.jpg");
        FileInputStream input = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile("test_1.jpg",
                file.getName(), "image/jpg", IOUtils.toByteArray(input));

        List<MultipartFile> imageList = new ArrayList<>();
        imageList.add(multipartFile);
        Optional<List<MultipartFile>> image = Optional.of(imageList);

        // act
        var result = blogService.insertNewBlogEntry(blogEntry, image);

        // assert
        assertNotNull(result);
        assertEquals(result.getBlogAuthor(), BLOG_AUTHOR);
        assertEquals(result.getBlogDate(), BLOG_DATE);
        assertEquals(result.getBlogText(), BLOG_TEXT);
        assertEquals(result.getBlogTitle(), BLOG_TITEL);
        assertEquals(result.getIsPublic(), BLOG_IS_PUBLIC);
        assertFalse(result.getBlogImages().isEmpty());

        result.getBlogImages().forEach(existingImage -> {
            Path filePath = Paths.get(existingImage.getLocation());
            assertTrue(Files.exists(filePath));
        });

    }

    @Test
    public void shouldInsertNewBlogEntryWithSeveralImages() throws IOException {
        // arrange
        var blogEntry = new BlogEntryDto();
        blogEntry.setBlogAuthor(BLOG_AUTHOR);
        blogEntry.setBlogDate(BLOG_DATE);
        blogEntry.setBlogText(BLOG_TEXT);
        blogEntry.setBlogTitle(BLOG_TITEL);
        blogEntry.setIsPublic(BLOG_IS_PUBLIC);

        File file = new File("src/test/resources/upload/test_1.jpg");
        FileInputStream input = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile("test_1.jpg",
                file.getName(), "image/jpg", IOUtils.toByteArray(input));

        File file2 = new File("src/test/resources/upload/test_2.png");
        FileInputStream input2 = new FileInputStream(file2);
        MultipartFile multipartFile2 = new MockMultipartFile("test_2.png",
                file.getName(), "image/jpg", IOUtils.toByteArray(input2));

        File file3 = new File("src/test/resources/upload/test_3.jpg");
        FileInputStream input3 = new FileInputStream(file3);
        MultipartFile multipartFile3 = new MockMultipartFile("test_3.jpg",
                file.getName(), "image/jpg", IOUtils.toByteArray(input3));

        List<MultipartFile> imageList = new ArrayList<>();
        imageList.add(multipartFile);
        imageList.add(multipartFile2);
        imageList.add(multipartFile3);
        Optional<List<MultipartFile>> image = Optional.of(imageList);

        // act
        var result = blogService.insertNewBlogEntry(blogEntry, image);

        // assert
        assertNotNull(result);
        assertEquals(result.getBlogAuthor(), BLOG_AUTHOR);
        assertEquals(result.getBlogDate(), BLOG_DATE);
        assertEquals(result.getBlogText(), BLOG_TEXT);
        assertEquals(result.getBlogTitle(), BLOG_TITEL);
        assertEquals(result.getIsPublic(), BLOG_IS_PUBLIC);
        assertFalse(result.getBlogImages().isEmpty());

        result.getBlogImages().forEach(existingImage -> {
            Path filePath = Paths.get(existingImage.getLocation());
            assertTrue(Files.exists(filePath));
        });
    }

    @Test
    public void shouldReturnFailureIfDataIsMissingWhenInsertingData() {
        // arrange
        var blogEntry = new BlogEntryDto();
        blogEntry.setBlogAuthor(BLOG_AUTHOR);
        blogEntry.setBlogDate(BLOG_DATE);
        blogEntry.setBlogTitle(BLOG_TITEL);
        blogEntry.setIsPublic(BLOG_IS_PUBLIC);
        Optional<List<MultipartFile>> image = Optional.empty();

        // act
        Throwable exception = assertThrows(NotFoundException.class, () -> blogService.insertNewBlogEntry(blogEntry, image));

        // assert
        assertEquals("Speichern fehlgeschlagen - Eintrag nicht vollständig", exception.getMessage());
    }

    @Test
    public void shouldUpdateExistingBlogEntryWithDifferentData() {
        // arrange
        var blogEntry = new BlogEntryDto();
        blogEntry.setBlogAuthor(BLOG_AUTHOR);
        blogEntry.setBlogDate(BLOG_DATE);
        blogEntry.setBlogText(BLOG_TEXT);
        blogEntry.setBlogTitle(BLOG_TITEL);
        blogEntry.setIsPublic(BLOG_IS_PUBLIC);
        Optional<List<MultipartFile>> image = Optional.empty();

        var blogEntry2 = new BlogEntryDto();
        blogEntry2.setBlogAuthor(BLOG_AUTHOR_2);
        blogEntry2.setBlogDate(BLOG_DATE_2);
        blogEntry2.setBlogText(BLOG_TEXT_2);
        blogEntry2.setBlogTitle(BLOG_TITEL_2);
        blogEntry2.setIsPublic(BLOG_IS_PUBLIC_2);

        // act
        var result = blogService.insertNewBlogEntry(blogEntry, image);
        var updatedResult = blogService.updateBlogEntry(blogEntry2, result.getId(), image);

        // assert
        assertNotNull(result);
        assertEquals(result.getBlogDate(), BLOG_DATE);
        assertEquals(result.getBlogText(), BLOG_TEXT);
        assertEquals(result.getBlogTitle(), BLOG_TITEL);
        assertEquals(result.getBlogAuthor(), BLOG_AUTHOR);

        assertNotNull(updatedResult);
        assertEquals(updatedResult.getBlogDate(), BLOG_DATE_2);
        assertEquals(updatedResult.getBlogText(), BLOG_TEXT_2);
        assertEquals(updatedResult.getBlogTitle(), BLOG_TITEL_2);
        assertEquals(updatedResult.getBlogAuthor(), BLOG_AUTHOR_2);

    }

    @Test
    public void shouldUpdateExistingBlogEntryWithNewImage() throws IOException {
        // arrange
        var blogEntry = new BlogEntryDto();
        blogEntry.setBlogAuthor(BLOG_AUTHOR);
        blogEntry.setBlogDate(BLOG_DATE);
        blogEntry.setBlogText(BLOG_TEXT);
        blogEntry.setBlogTitle(BLOG_TITEL);
        blogEntry.setIsPublic(BLOG_IS_PUBLIC);
        Optional<List<MultipartFile>> image = Optional.empty();

        var blogEntry2 = new BlogEntryDto();
        blogEntry2.setBlogAuthor(BLOG_AUTHOR);
        blogEntry2.setBlogDate(BLOG_DATE);
        blogEntry2.setBlogText(BLOG_TEXT);
        blogEntry2.setBlogTitle(BLOG_TITEL);
        blogEntry2.setIsPublic(BLOG_IS_PUBLIC);

        File file = new File("src/test/resources/upload/test_1.jpg");
        FileInputStream input = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile("test_1.jpg",
                file.getName(), "image/jpg", IOUtils.toByteArray(input));

        List<MultipartFile> imageList = new ArrayList<>();
        imageList.add(multipartFile);
        Optional<List<MultipartFile>> image2 = Optional.of(imageList);

        // act
        var result = blogService.insertNewBlogEntry(blogEntry, image);
        var updatedResult = blogService.updateBlogEntry(blogEntry2, result.getId(), image2);

        // assert
        assertNotNull(result);
        assertEquals(result.getBlogDate(), BLOG_DATE);
        assertEquals(result.getBlogText(), BLOG_TEXT);
        assertEquals(result.getBlogTitle(), BLOG_TITEL);
        assertEquals(result.getBlogAuthor(), BLOG_AUTHOR);
        assertTrue(result.getBlogImages().isEmpty());

        assertNotNull(updatedResult);
        assertEquals(updatedResult.getBlogDate(), BLOG_DATE);
        assertEquals(updatedResult.getBlogText(), BLOG_TEXT);
        assertEquals(updatedResult.getBlogTitle(), BLOG_TITEL);
        assertEquals(updatedResult.getBlogAuthor(), BLOG_AUTHOR);
        assertFalse(updatedResult.getBlogImages().isEmpty());

    }

    @Test
    public void shouldUpdateExistingBlogEntryWithNewImageWhileHavingExistingImages() throws IOException {
        // arrange
        var blogEntry = new BlogEntryDto();
        blogEntry.setBlogAuthor(BLOG_AUTHOR);
        blogEntry.setBlogDate(BLOG_DATE);
        blogEntry.setBlogText(BLOG_TEXT);
        blogEntry.setBlogTitle(BLOG_TITEL);
        blogEntry.setIsPublic(BLOG_IS_PUBLIC);

        File file = new File("src/test/resources/upload/test_1.jpg");
        FileInputStream input = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile("test_1.jpg",
                file.getName(), "image/jpg", IOUtils.toByteArray(input));

        List<MultipartFile> imageList = new ArrayList<>();
        imageList.add(multipartFile);
        Optional<List<MultipartFile>> image = Optional.of(imageList);

        var blogEntry2 = new BlogEntryDto();
        blogEntry2.setBlogAuthor(BLOG_AUTHOR);
        blogEntry2.setBlogDate(BLOG_DATE);
        blogEntry2.setBlogText(BLOG_TEXT);
        blogEntry2.setBlogTitle(BLOG_TITEL);
        blogEntry2.setIsPublic(BLOG_IS_PUBLIC);

        File file2 = new File("src/test/resources/upload/test_2.png");
        FileInputStream input2 = new FileInputStream(file2);
        MultipartFile multipartFile2 = new MockMultipartFile("test_2.png",
                file.getName(), "image/jpg", IOUtils.toByteArray(input2));

        List<MultipartFile> imageList2 = new ArrayList<>();
        imageList2.add(multipartFile2);
        Optional<List<MultipartFile>> image2 = Optional.of(imageList2);

        // act
        var result = blogService.insertNewBlogEntry(blogEntry, image);
        List<AttachmentResponse> existingImages = new ArrayList<>();
        result.getBlogImages().forEach(existingImage -> existingImages.add(new AttachmentResponse(existingImage.getId(), "")));
        blogEntry2.setBlogImages(existingImages);
        var updatedResult = blogService.updateBlogEntry(blogEntry2, result.getId(), image2);

        // assert
        assertNotNull(result);
        assertEquals(result.getBlogDate(), BLOG_DATE);
        assertEquals(result.getBlogText(), BLOG_TEXT);
        assertEquals(result.getBlogTitle(), BLOG_TITEL);
        assertEquals(result.getBlogAuthor(), BLOG_AUTHOR);
        assertFalse(result.getBlogImages().isEmpty());
        assertEquals(1, result.getBlogImages().size());

        assertNotNull(updatedResult);
        assertEquals(updatedResult.getBlogDate(), BLOG_DATE);
        assertEquals(updatedResult.getBlogText(), BLOG_TEXT);
        assertEquals(updatedResult.getBlogTitle(), BLOG_TITEL);
        assertEquals(updatedResult.getBlogAuthor(), BLOG_AUTHOR);
        assertFalse(updatedResult.getBlogImages().isEmpty());
        assertEquals(2, updatedResult.getBlogImages().size());
    }

    @Test
    public void shouldRemoveImageFromExistingBlogEntry() throws IOException {
        // arrange
        var blogEntry = new BlogEntryDto();
        blogEntry.setBlogAuthor(BLOG_AUTHOR);
        blogEntry.setBlogDate(BLOG_DATE);
        blogEntry.setBlogText(BLOG_TEXT);
        blogEntry.setBlogTitle(BLOG_TITEL);
        blogEntry.setIsPublic(BLOG_IS_PUBLIC);

        File file = new File("src/test/resources/upload/test_1.jpg");
        FileInputStream input = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile("test_1.jpg",
                file.getName(), "image/jpg", IOUtils.toByteArray(input));

        List<MultipartFile> imageList = new ArrayList<>();
        imageList.add(multipartFile);
        Optional<List<MultipartFile>> image = Optional.of(imageList);

        Optional<List<MultipartFile>> image2 = Optional.empty();

        // act
        var result = blogService.insertNewBlogEntry(blogEntry, image);
        var updatedResult = blogService.updateBlogEntry(blogEntry, result.getId(), image2);

        // assert
        assertNotNull(result);
        assertEquals(result.getBlogDate(), BLOG_DATE);
        assertEquals(result.getBlogText(), BLOG_TEXT);
        assertEquals(result.getBlogTitle(), BLOG_TITEL);
        assertEquals(result.getBlogAuthor(), BLOG_AUTHOR);
        assertFalse(result.getBlogImages().isEmpty());
        assertEquals(1, result.getBlogImages().size());

        assertNotNull(updatedResult);
        assertEquals(updatedResult.getBlogDate(), BLOG_DATE);
        assertEquals(updatedResult.getBlogText(), BLOG_TEXT);
        assertEquals(updatedResult.getBlogTitle(), BLOG_TITEL);
        assertEquals(updatedResult.getBlogAuthor(), BLOG_AUTHOR);
        assertTrue(updatedResult.getBlogImages().isEmpty());
        assertEquals(0, updatedResult.getBlogImages().size());
    }

    @Test
    public void shouldRemoveImageFromExistingBlogEntryWhileHavingSeveralExistingImages() throws IOException {
        // arrange
        var blogEntry = new BlogEntryDto();
        blogEntry.setBlogAuthor(BLOG_AUTHOR);
        blogEntry.setBlogDate(BLOG_DATE);
        blogEntry.setBlogText(BLOG_TEXT);
        blogEntry.setBlogTitle(BLOG_TITEL);
        blogEntry.setIsPublic(BLOG_IS_PUBLIC);

        File file = new File("src/test/resources/upload/test_1.jpg");
        FileInputStream input = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile("test_1.jpg",
                file.getName(), "image/jpg", IOUtils.toByteArray(input));

        File file2 = new File("src/test/resources/upload/test_2.png");
        FileInputStream input2 = new FileInputStream(file2);
        MultipartFile multipartFile2 = new MockMultipartFile("test_2.png",
                file.getName(), "image/jpg", IOUtils.toByteArray(input2));

        File file3 = new File("src/test/resources/upload/test_3.jpg");
        FileInputStream input3 = new FileInputStream(file3);
        MultipartFile multipartFile3 = new MockMultipartFile("test_3.jpg",
                file.getName(), "image/jpg", IOUtils.toByteArray(input3));

        List<MultipartFile> imageList = new ArrayList<>();
        imageList.add(multipartFile);
        imageList.add(multipartFile2);
        imageList.add(multipartFile3);
        Optional<List<MultipartFile>> image = Optional.of(imageList);

        Optional<List<MultipartFile>> image2 = Optional.empty();

        // act
        var result = blogService.insertNewBlogEntry(blogEntry, image);
        List<AttachmentResponse> existingImages = new ArrayList<>();
        existingImages.add(new AttachmentResponse(result.getBlogImages().get(0).getId(), ""));
        existingImages.add(new AttachmentResponse(result.getBlogImages().get(2).getId(), ""));
        blogEntry.setBlogImages(existingImages);
        var updatedResult = blogService.updateBlogEntry(blogEntry, result.getId(), image2);

        // assert
        assertNotNull(result);
        assertEquals(result.getBlogDate(), BLOG_DATE);
        assertEquals(result.getBlogText(), BLOG_TEXT);
        assertEquals(result.getBlogTitle(), BLOG_TITEL);
        assertEquals(result.getBlogAuthor(), BLOG_AUTHOR);
        assertFalse(result.getBlogImages().isEmpty());
        assertEquals(3, result.getBlogImages().size());

        assertNotNull(updatedResult);
        assertEquals(updatedResult.getBlogDate(), BLOG_DATE);
        assertEquals(updatedResult.getBlogText(), BLOG_TEXT);
        assertEquals(updatedResult.getBlogTitle(), BLOG_TITEL);
        assertEquals(updatedResult.getBlogAuthor(), BLOG_AUTHOR);
        assertFalse(updatedResult.getBlogImages().isEmpty());
        assertEquals(2, updatedResult.getBlogImages().size());

    }

    @Test
    public void shouldDeleteBlogEntryWithoutImage() {
        // arrange
        var blogEntry = new BlogEntryDto();
        blogEntry.setBlogAuthor(BLOG_AUTHOR);
        blogEntry.setBlogDate(BLOG_DATE);
        blogEntry.setBlogText(BLOG_TEXT);
        blogEntry.setBlogTitle(BLOG_TITEL);
        blogEntry.setIsPublic(BLOG_IS_PUBLIC);

        Optional<List<MultipartFile>> image = Optional.empty();

        // act
        var result = blogService.insertNewBlogEntry(blogEntry, image);
        blogService.deleteBlogEntry(result.getId());
        Throwable exception = assertThrows(NotFoundException.class, () -> blogService.getSpecificBlogPost(result.getId()));

        // assert
        assertEquals("Keinen Blogeintrag mit der id " + result.getId() + " gefunden", exception.getMessage());
    }

    @Test
    public void shouldDeleteBlogEntryWithImage() throws IOException {
        // arrange
        var blogEntry = new BlogEntryDto();
        blogEntry.setBlogAuthor(BLOG_AUTHOR);
        blogEntry.setBlogDate(BLOG_DATE);
        blogEntry.setBlogText(BLOG_TEXT);
        blogEntry.setBlogTitle(BLOG_TITEL);
        blogEntry.setIsPublic(BLOG_IS_PUBLIC);

        File file = new File("src/test/resources/upload/test_1.jpg");
        FileInputStream input = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile("test_1.jpg",
                file.getName(), "image/jpg", IOUtils.toByteArray(input));

        List<MultipartFile> imageList = new ArrayList<>();
        imageList.add(multipartFile);
        Optional<List<MultipartFile>> image = Optional.of(imageList);

        // act
        var result = blogService.insertNewBlogEntry(blogEntry, image);
        blogService.deleteBlogEntry(result.getId());
        Throwable exception = assertThrows(NotFoundException.class, () -> blogService.getSpecificBlogPost(result.getId()));

        // assert
        assertEquals("Keinen Blogeintrag mit der id " + result.getId() + " gefunden", exception.getMessage());

        result.getBlogImages().forEach(existingImage -> {
            Path filePath = Paths.get(existingImage.getLocation());
            assertFalse(Files.exists(filePath));
        });
    }

    //TODO: shouldDeleteBlogEntryWithSeveralImages
    @Test
    public void shouldDeleteBlogEntryWithSeveralImages() throws IOException {
        // arrange
        var blogEntry = new BlogEntryDto();
        blogEntry.setBlogAuthor(BLOG_AUTHOR);
        blogEntry.setBlogDate(BLOG_DATE);
        blogEntry.setBlogText(BLOG_TEXT);
        blogEntry.setBlogTitle(BLOG_TITEL);
        blogEntry.setIsPublic(BLOG_IS_PUBLIC);

        File file = new File("src/test/resources/upload/test_1.jpg");
        FileInputStream input = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile("test_1.jpg",
                file.getName(), "image/jpg", IOUtils.toByteArray(input));

        File file2 = new File("src/test/resources/upload/test_2.png");
        FileInputStream input2 = new FileInputStream(file2);
        MultipartFile multipartFile2 = new MockMultipartFile("test_2.png",
                file.getName(), "image/jpg", IOUtils.toByteArray(input2));

        File file3 = new File("src/test/resources/upload/test_3.jpg");
        FileInputStream input3 = new FileInputStream(file3);
        MultipartFile multipartFile3 = new MockMultipartFile("test_3.jpg",
                file.getName(), "image/jpg", IOUtils.toByteArray(input3));

        List<MultipartFile> imageList = new ArrayList<>();
        imageList.add(multipartFile);
        imageList.add(multipartFile2);
        imageList.add(multipartFile3);
        Optional<List<MultipartFile>> image = Optional.of(imageList);

        // act
        var result = blogService.insertNewBlogEntry(blogEntry, image);
        blogService.deleteBlogEntry(result.getId());
        Throwable exception = assertThrows(NotFoundException.class, () -> blogService.getSpecificBlogPost(result.getId()));

        // assert
        assertEquals("Keinen Blogeintrag mit der id " + result.getId() + " gefunden", exception.getMessage());

        result.getBlogImages().forEach(existingImage -> {
            Path filePath = Paths.get(existingImage.getLocation());
            assertFalse(Files.exists(filePath));
        });
    }
}
