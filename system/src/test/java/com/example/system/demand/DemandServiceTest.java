package com.example.system.demand;

import com.example.system.attachment.AttachmentResponse;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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
public class DemandServiceTest {

    private static final String DEMAND_NAME = "Michael";
    private static final LocalDateTime DEMAND_DATE = LocalDateTime.parse("2022-06-01T21:16:20");
    private static final String DEMAND_TEXT = "Dies ist der Beispieltext";
    private static final String DEMAND_TITEL = "Testtitel";
    private static final String DEMAND_NAME_2 = "Peter";
    private static final LocalDateTime DEMAND_DATE_2 = LocalDateTime.parse("2022-06-12T21:16:20");
    private static final String DEMAND_TEXT_2 = "Dies ist der Beispieltext 2";
    private static final String DEMAND_TITEL_2 = "Testtitel 2";

    @Autowired
    private DemandService demandService;

    @AfterEach
    public void clearTestDatabaseFromEntries() {
        var result = demandService.getAllDemandEntries(null);
        result.forEach(entry -> {
            try {
                demandService.deleteDemandEntry(entry.getId());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Test
    public void shouldGetOneSpecificDemandEntry() throws IOException {
        // arrange
        DemandEntryDto demandEntryDto = new DemandEntryDto();
        demandEntryDto.setDemandDate(DEMAND_DATE);
        demandEntryDto.setDemandName(DEMAND_NAME);
        demandEntryDto.setDemandText(DEMAND_TEXT);
        demandEntryDto.setDemandTitle(DEMAND_TITEL);

        File file = new File("src/test/resources/upload/test_1.jpg");
        FileInputStream input = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile("test_1.jpg",
                file.getName(), "image/jpg", IOUtils.toByteArray(input));

        List<MultipartFile> imageList = new ArrayList<>();
        imageList.add(multipartFile);
        Optional<List<MultipartFile>> image = Optional.of(imageList);

        // act
        var result = demandService.insertNewDemandEntry(demandEntryDto, image);
        var getDemandEntry = demandService.getDemandById(result.getId(), null);

        // assert
        assertNotNull(getDemandEntry);
        assertEquals(getDemandEntry.getDemandDate(), DEMAND_DATE);
        assertEquals(getDemandEntry.getDemandText(), DEMAND_TEXT);
        assertEquals(getDemandEntry.getDemandTitle(), DEMAND_TITEL);
        assertEquals(getDemandEntry.getDemandName(), DEMAND_NAME);

    }

    @Test
    public void shouldGetAllDemandEntries() throws IOException {
        // precondition

        // arrange
        DemandEntryDto demandEntryDto = new DemandEntryDto();
        demandEntryDto.setDemandDate(DEMAND_DATE);
        demandEntryDto.setDemandName(DEMAND_NAME);
        demandEntryDto.setDemandText(DEMAND_TEXT);
        demandEntryDto.setDemandTitle(DEMAND_TITEL);

        Optional<List<MultipartFile>> image = Optional.empty();

        DemandEntryDto demandEntryDto2 = new DemandEntryDto();
        demandEntryDto2.setDemandDate(DEMAND_DATE_2);
        demandEntryDto2.setDemandName(DEMAND_NAME_2);
        demandEntryDto2.setDemandText(DEMAND_TEXT_2);
        demandEntryDto2.setDemandTitle(DEMAND_TITEL_2);

        Optional<List<MultipartFile>> image2 = Optional.empty();

        // act
        var result = demandService.insertNewDemandEntry(demandEntryDto, image);
        var result2 = demandService.insertNewDemandEntry(demandEntryDto2, image2);
        var getAllDemandEntries = demandService.getAllDemandEntries(null);

        // assert
        assertNotNull(getAllDemandEntries.get(1));
        assertEquals(getAllDemandEntries.get(1).getDemandDate(), DEMAND_DATE);
        assertEquals(getAllDemandEntries.get(1).getDemandText(), DEMAND_TEXT);
        assertEquals(getAllDemandEntries.get(1).getDemandTitle(), DEMAND_TITEL);
        assertEquals(getAllDemandEntries.get(1).getDemandName(), DEMAND_NAME);

        assertNotNull(getAllDemandEntries.get(0));
        assertEquals(getAllDemandEntries.get(0).getDemandDate(), DEMAND_DATE_2);
        assertEquals(getAllDemandEntries.get(0).getDemandText(), DEMAND_TEXT_2);
        assertEquals(getAllDemandEntries.get(0).getDemandTitle(), DEMAND_TITEL_2);
        assertEquals(getAllDemandEntries.get(0).getDemandName(), DEMAND_NAME_2);

    }

    @Test
    public void shouldInsertNewDemandEntryWithImage() throws IOException {
        // arrange
        DemandEntryDto demandEntryDto = new DemandEntryDto();
        demandEntryDto.setDemandDate(DEMAND_DATE);
        demandEntryDto.setDemandName(DEMAND_NAME);
        demandEntryDto.setDemandText(DEMAND_TEXT);
        demandEntryDto.setDemandTitle(DEMAND_TITEL);

        File file = new File("src/test/resources/upload/test_1.jpg");
        FileInputStream input = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile("test_1.jpg",
                file.getName(), "image/jpg", IOUtils.toByteArray(input));

        List<MultipartFile> imageList = new ArrayList<>();
        imageList.add(multipartFile);
        Optional<List<MultipartFile>> image = Optional.of(imageList);

        // act
        var result = demandService.insertNewDemandEntry(demandEntryDto, image);

        // assert
        assertNotNull(result);
        assertEquals(result.getDemandDate(), DEMAND_DATE);
        assertEquals(result.getDemandText(), DEMAND_TEXT);
        assertEquals(result.getDemandTitle(), DEMAND_TITEL);
        assertEquals(result.getDemandName(), DEMAND_NAME);

    }

    @Test
    public void shouldInsertNewDemandEntryWithSeveralImages() throws IOException {
        // arrange
        DemandEntryDto demandEntryDto = new DemandEntryDto();
        demandEntryDto.setDemandDate(DEMAND_DATE);
        demandEntryDto.setDemandName(DEMAND_NAME);
        demandEntryDto.setDemandText(DEMAND_TEXT);
        demandEntryDto.setDemandTitle(DEMAND_TITEL);

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
        var result = demandService.insertNewDemandEntry(demandEntryDto, image);

        // assert
        assertNotNull(result);
        assertEquals(result.getDemandDate(), DEMAND_DATE);
        assertEquals(result.getDemandText(), DEMAND_TEXT);
        assertEquals(result.getDemandTitle(), DEMAND_TITEL);
        assertEquals(result.getDemandName(), DEMAND_NAME);

    }

    @Test
    public void shouldInsertNewDemandEntryWithoutImage() {
        // arrange
        DemandEntryDto demandEntryDto = new DemandEntryDto();
        demandEntryDto.setDemandDate(DEMAND_DATE);
        demandEntryDto.setDemandName(DEMAND_NAME);
        demandEntryDto.setDemandText(DEMAND_TEXT);
        demandEntryDto.setDemandTitle(DEMAND_TITEL);

        Optional<List<MultipartFile>> image = Optional.empty();

        // act
        var result = demandService.insertNewDemandEntry(demandEntryDto, image);

        // assert
        assertNotNull(result);
        assertEquals(result.getDemandDate(), DEMAND_DATE);
        assertEquals(result.getDemandText(), DEMAND_TEXT);
        assertEquals(result.getDemandTitle(), DEMAND_TITEL);
        assertEquals(result.getDemandName(), DEMAND_NAME);

    }

    @Test
    public void shouldReturnFailureIfDataIsMissingWhenInsertingData() {
        // arrange
        DemandEntryDto demandEntryDto = new DemandEntryDto();
        demandEntryDto.setDemandDate(DEMAND_DATE);
        demandEntryDto.setDemandText(DEMAND_TEXT);
        demandEntryDto.setDemandTitle(DEMAND_TITEL);
        Optional<List<MultipartFile>> image = Optional.empty();

        // act
        Throwable exception = assertThrows(NotFoundException.class, () -> demandService.insertNewDemandEntry(demandEntryDto, image));

        // assert
        assertEquals("Speichern fehlgeschlagen - Eintrag nicht vollständig", exception.getMessage());
    }

    @Test
    public void shouldUpdateExistingDemandEntryWithDifferentData() {
        // arrange
        DemandEntryDto demandEntryDto = new DemandEntryDto();
        demandEntryDto.setDemandDate(DEMAND_DATE);
        demandEntryDto.setDemandName(DEMAND_NAME);
        demandEntryDto.setDemandText(DEMAND_TEXT);
        demandEntryDto.setDemandTitle(DEMAND_TITEL);
        Optional<List<MultipartFile>> image = Optional.empty();

        DemandEntryDto demandUpdateEntryDto = new DemandEntryDto();
        demandUpdateEntryDto.setDemandDate(DEMAND_DATE_2);
        demandUpdateEntryDto.setDemandName(DEMAND_NAME_2);
        demandUpdateEntryDto.setDemandText(DEMAND_TEXT_2);
        demandUpdateEntryDto.setDemandTitle(DEMAND_TITEL_2);

        // act
        var result = demandService.insertNewDemandEntry(demandEntryDto, image);
        var updatedResult = demandService.updateDemandEntry(demandUpdateEntryDto, result.getId(), image);

        // assert
        assertNotNull(result);
        assertEquals(result.getDemandDate(), DEMAND_DATE);
        assertEquals(result.getDemandText(), DEMAND_TEXT);
        assertEquals(result.getDemandTitle(), DEMAND_TITEL);
        assertEquals(result.getDemandName(), DEMAND_NAME);

        assertNotNull(updatedResult);
        assertEquals(updatedResult.getDemandDate(), DEMAND_DATE_2);
        assertEquals(updatedResult.getDemandText(), DEMAND_TEXT_2);
        assertEquals(updatedResult.getDemandTitle(), DEMAND_TITEL_2);
        assertEquals(updatedResult.getDemandName(), DEMAND_NAME_2);
    }

    @Test
    public void shouldUpdateExistingDemandEntryWithNewImage() throws IOException {
        // arrange
        DemandEntryDto demandEntryDto = new DemandEntryDto();
        demandEntryDto.setDemandDate(DEMAND_DATE);
        demandEntryDto.setDemandName(DEMAND_NAME);
        demandEntryDto.setDemandText(DEMAND_TEXT);
        demandEntryDto.setDemandTitle(DEMAND_TITEL);
        Optional<List<MultipartFile>> image = Optional.empty();

        DemandEntryDto demandUpdateEntryDto = new DemandEntryDto();
        demandUpdateEntryDto.setDemandDate(DEMAND_DATE_2);
        demandUpdateEntryDto.setDemandName(DEMAND_NAME_2);
        demandUpdateEntryDto.setDemandText(DEMAND_TEXT_2);
        demandUpdateEntryDto.setDemandTitle(DEMAND_TITEL_2);

        File file = new File("src/test/resources/upload/test_1.jpg");
        FileInputStream input = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile("test_1.jpg",
                file.getName(), "image/jpg", IOUtils.toByteArray(input));

        List<MultipartFile> imageList = new ArrayList<>();
        imageList.add(multipartFile);
        Optional<List<MultipartFile>> updatedImage = Optional.of(imageList);

        // act
        var result = demandService.insertNewDemandEntry(demandEntryDto, image);
        var updatedResult = demandService.updateDemandEntry(demandUpdateEntryDto, result.getId(), updatedImage);

        // assert
        assertNotNull(result);
        assertEquals(result.getDemandDate(), DEMAND_DATE);
        assertEquals(result.getDemandText(), DEMAND_TEXT);
        assertEquals(result.getDemandTitle(), DEMAND_TITEL);
        assertEquals(result.getDemandName(), DEMAND_NAME);
        assertTrue(result.getDemandImages().isEmpty());

        assertNotNull(updatedResult);
        assertEquals(updatedResult.getDemandDate(), DEMAND_DATE_2);
        assertEquals(updatedResult.getDemandText(), DEMAND_TEXT_2);
        assertEquals(updatedResult.getDemandTitle(), DEMAND_TITEL_2);
        assertEquals(updatedResult.getDemandName(), DEMAND_NAME_2);
        assertFalse(updatedResult.getDemandImages().isEmpty());

    }

    @Test
    public void shouldUpdateExistingDemandEntryWithNewImageWhileHavingExistingImages() throws IOException {
        // arrange
        DemandEntryDto demandEntryDto = new DemandEntryDto();
        demandEntryDto.setDemandDate(DEMAND_DATE);
        demandEntryDto.setDemandName(DEMAND_NAME);
        demandEntryDto.setDemandText(DEMAND_TEXT);
        demandEntryDto.setDemandTitle(DEMAND_TITEL);

        File file = new File("src/test/resources/upload/test_1.jpg");
        FileInputStream input = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile("test_1.jpg",
                file.getName(), "image/jpg", IOUtils.toByteArray(input));

        List<MultipartFile> imageList = new ArrayList<>();
        imageList.add(multipartFile);
        Optional<List<MultipartFile>> image = Optional.of(imageList);

        DemandEntryDto demandUpdateEntryDto = new DemandEntryDto();
        demandUpdateEntryDto.setDemandDate(DEMAND_DATE_2);
        demandUpdateEntryDto.setDemandName(DEMAND_NAME_2);
        demandUpdateEntryDto.setDemandText(DEMAND_TEXT_2);
        demandUpdateEntryDto.setDemandTitle(DEMAND_TITEL_2);

        File file2 = new File("src/test/resources/upload/test_2.png");
        FileInputStream input2 = new FileInputStream(file2);
        MultipartFile multipartFile2 = new MockMultipartFile("test_2.png",
                file.getName(), "image/jpg", IOUtils.toByteArray(input2));

        List<MultipartFile> imageList2 = new ArrayList<>();
        imageList2.add(multipartFile2);
        Optional<List<MultipartFile>> updatedImage = Optional.of(imageList2);

        // act
        var result = demandService.insertNewDemandEntry(demandEntryDto, image);

        List<AttachmentResponse> existingImages = new ArrayList<>();
        result.getDemandImages().forEach(existingImage -> existingImages.add(new AttachmentResponse(existingImage.getId(), "")));
        demandUpdateEntryDto.setDemandImages(existingImages);
        var updatedResult = demandService.updateDemandEntry(demandUpdateEntryDto, result.getId(), updatedImage);

        // assert
        assertNotNull(result);
        assertEquals(result.getDemandDate(), DEMAND_DATE);
        assertEquals(result.getDemandText(), DEMAND_TEXT);
        assertEquals(result.getDemandTitle(), DEMAND_TITEL);
        assertEquals(result.getDemandName(), DEMAND_NAME);

        assertEquals(1, result.getDemandImages().size());

        assertNotNull(updatedResult);
        assertEquals(updatedResult.getDemandDate(), DEMAND_DATE_2);
        assertEquals(updatedResult.getDemandText(), DEMAND_TEXT_2);
        assertEquals(updatedResult.getDemandTitle(), DEMAND_TITEL_2);
        assertEquals(updatedResult.getDemandName(), DEMAND_NAME_2);
        assertFalse(updatedResult.getDemandImages().isEmpty());
        assertEquals(2, updatedResult.getDemandImages().size());

    }

    @Test
    public void shouldRemoveImageFromExistingDemandEntry() throws IOException {
        // arrange
        DemandEntryDto demandEntryDto = new DemandEntryDto();
        demandEntryDto.setDemandDate(DEMAND_DATE);
        demandEntryDto.setDemandName(DEMAND_NAME);
        demandEntryDto.setDemandText(DEMAND_TEXT);
        demandEntryDto.setDemandTitle(DEMAND_TITEL);

        File file = new File("src/test/resources/upload/test_1.jpg");
        FileInputStream input = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile("test_1.jpg",
                file.getName(), "image/jpg", IOUtils.toByteArray(input));

        List<MultipartFile> imageList = new ArrayList<>();
        imageList.add(multipartFile);
        Optional<List<MultipartFile>> image = Optional.of(imageList);

        Optional<List<MultipartFile>> image2 = Optional.empty();

        // act
        var result = demandService.insertNewDemandEntry(demandEntryDto, image);
        var updatedResult = demandService.updateDemandEntry(demandEntryDto, result.getId(), image2);

        // assert
        assertNotNull(result);
        assertEquals(result.getDemandDate(), DEMAND_DATE);
        assertEquals(result.getDemandText(), DEMAND_TEXT);
        assertEquals(result.getDemandTitle(), DEMAND_TITEL);
        assertEquals(result.getDemandName(), DEMAND_NAME);

        assertEquals(1, result.getDemandImages().size());

        assertNotNull(updatedResult);
        assertEquals(updatedResult.getDemandDate(), DEMAND_DATE);
        assertEquals(updatedResult.getDemandText(), DEMAND_TEXT);
        assertEquals(updatedResult.getDemandTitle(), DEMAND_TITEL);
        assertEquals(updatedResult.getDemandName(), DEMAND_NAME);

        assertEquals(0, updatedResult.getDemandImages().size());

    }

    @Test
    public void shouldRemoveImageFromExistingDemandEntryWhileHavingSeveralExistingImages() throws IOException {
        // arrange
        DemandEntryDto demandEntryDto = new DemandEntryDto();
        demandEntryDto.setDemandDate(DEMAND_DATE);
        demandEntryDto.setDemandName(DEMAND_NAME);
        demandEntryDto.setDemandText(DEMAND_TEXT);
        demandEntryDto.setDemandTitle(DEMAND_TITEL);

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
        var result = demandService.insertNewDemandEntry(demandEntryDto, image);

        List<AttachmentResponse> existingImages = new ArrayList<>();
        existingImages.add(new AttachmentResponse(result.getDemandImages().get(0).getId(), ""));
        existingImages.add(new AttachmentResponse(result.getDemandImages().get(2).getId(), ""));
        demandEntryDto.setDemandImages(existingImages);
        var updatedResult = demandService.updateDemandEntry(demandEntryDto, result.getId(), image2);

        // assert
        assertNotNull(result);
        assertEquals(result.getDemandDate(), DEMAND_DATE);
        assertEquals(result.getDemandText(), DEMAND_TEXT);
        assertEquals(result.getDemandTitle(), DEMAND_TITEL);
        assertEquals(result.getDemandName(), DEMAND_NAME);

        assertEquals(3, result.getDemandImages().size());

        assertNotNull(updatedResult);
        assertEquals(updatedResult.getDemandDate(), DEMAND_DATE);
        assertEquals(updatedResult.getDemandText(), DEMAND_TEXT);
        assertEquals(updatedResult.getDemandTitle(), DEMAND_TITEL);
        assertEquals(updatedResult.getDemandName(), DEMAND_NAME);

        assertEquals(2, updatedResult.getDemandImages().size());

    }

    @Test
    public void shouldDeleteDemandEntryWithoutImage() throws IOException {
        // arrange
        DemandEntryDto demandEntryDto = new DemandEntryDto();
        demandEntryDto.setDemandDate(DEMAND_DATE);
        demandEntryDto.setDemandName(DEMAND_NAME);
        demandEntryDto.setDemandText(DEMAND_TEXT);
        demandEntryDto.setDemandTitle(DEMAND_TITEL);
        Optional<List<MultipartFile>> image = Optional.empty();


        // act
        var result = demandService.insertNewDemandEntry(demandEntryDto, image);
        demandService.deleteDemandEntry(result.getId());
        Throwable exception = assertThrows(NotFoundException.class, () -> demandService.getDemandById(result.getId(), null));

        // assert
        assertEquals("Keine Anfrage mit der id " + result.getId() + " gefunden", exception.getMessage());
        assertNotNull(result);
        assertEquals(result.getDemandDate(), DEMAND_DATE);
        assertEquals(result.getDemandText(), DEMAND_TEXT);
        assertEquals(result.getDemandTitle(), DEMAND_TITEL);
        assertEquals(result.getDemandName(), DEMAND_NAME);

    }

    @Test
    public void shouldDeleteDemandEntryWithImage() throws IOException {
        // arrange
        DemandEntryDto demandEntryDto = new DemandEntryDto();
        demandEntryDto.setDemandDate(DEMAND_DATE);
        demandEntryDto.setDemandName(DEMAND_NAME);
        demandEntryDto.setDemandText(DEMAND_TEXT);
        demandEntryDto.setDemandTitle(DEMAND_TITEL);

        File file = new File("src/test/resources/upload/test_1.jpg");
        FileInputStream input = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile("test_1.jpg",
                file.getName(), "image/jpg", IOUtils.toByteArray(input));

        List<MultipartFile> imageList = new ArrayList<>();
        imageList.add(multipartFile);
        Optional<List<MultipartFile>> image = Optional.of(imageList);

        // act
        var result = demandService.insertNewDemandEntry(demandEntryDto, image);
        demandService.deleteDemandEntry(result.getId());
        Throwable exception = assertThrows(NotFoundException.class, () -> demandService.getDemandById(result.getId(), null));

        // assert
        assertEquals("Keine Anfrage mit der id " + result.getId() + " gefunden", exception.getMessage());
        assertNotNull(result);
        assertEquals(result.getDemandDate(), DEMAND_DATE);
        assertEquals(result.getDemandText(), DEMAND_TEXT);
        assertEquals(result.getDemandTitle(), DEMAND_TITEL);
        assertEquals(result.getDemandName(), DEMAND_NAME);

        result.getDemandImages().forEach(existingImage -> {
            Path filePath = Paths.get(existingImage.getLocation());
            assertFalse(Files.exists(filePath));
        });
    }

    @Test
    public void shouldDeleteDemandEntryWithSeveralImages() throws IOException {
        // arrange
        DemandEntryDto demandEntryDto = new DemandEntryDto();
        demandEntryDto.setDemandDate(DEMAND_DATE);
        demandEntryDto.setDemandName(DEMAND_NAME);
        demandEntryDto.setDemandText(DEMAND_TEXT);
        demandEntryDto.setDemandTitle(DEMAND_TITEL);

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
        var result = demandService.insertNewDemandEntry(demandEntryDto, image);
        demandService.deleteDemandEntry(result.getId());
        Throwable exception = assertThrows(NotFoundException.class, () -> demandService.getDemandById(result.getId(), null));

        // assert
        assertEquals("Keine Anfrage mit der id " + result.getId() + " gefunden", exception.getMessage());
        assertNotNull(result);
        assertEquals(result.getDemandDate(), DEMAND_DATE);
        assertEquals(result.getDemandText(), DEMAND_TEXT);
        assertEquals(result.getDemandTitle(), DEMAND_TITEL);
        assertEquals(result.getDemandName(), DEMAND_NAME);

        result.getDemandImages().forEach(existingImage -> {
            Path filePath = Paths.get(existingImage.getLocation());
            assertFalse(Files.exists(filePath));
        });
    }
}
