package com.example.system.instrument;

import com.example.system.news.NewsEntryDto;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@WebAppConfiguration
@SpringBootTest
public class InstrumentServiceTest {

    private static final LocalDateTime INSTRUMENT_DATE = LocalDateTime.parse("2022-06-01T21:16:20");
    private static final String INSTRUMENT_TITLE = "Testinstrument";
    private static final String INSTRUMENT_DONATOR = "Testspender";
    private static final Boolean INSTRUMENT_TAKEN = false;
    private static final LocalDateTime INSTRUMENT_DATE_2 = LocalDateTime.parse("2022-05-01T21:16:20");
    private static final String INSTRUMENT_TITLE_2 = "Testinstrument 2";
    private static final String INSTRUMENT_DONATOR_2 = "Testspender 2";
    private static final Boolean INSTRUMENT_TAKEN_2 = true;

    @Autowired
    private InstrumentService instrumentService;

    @AfterEach
    public void clearTestDatabaseFromEntries() {
        var result = instrumentService.getAllInstruments();
        result.forEach(entry -> {
            try {
                instrumentService.deleteInstrumentEntry(entry.getId());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Test
    public void shouldGetOneSpecificInstrumentEntryWithoutImage() throws IOException {
        // arrange
        Instrument instrument = new Instrument();
        instrument.setInstrumentDate(INSTRUMENT_DATE);
        instrument.setInstrumentTitle(INSTRUMENT_TITLE);
        instrument.setDonator(INSTRUMENT_DONATOR);
        instrument.setTaken(INSTRUMENT_TAKEN);
        Optional<MultipartFile> image = Optional.empty();

        // act
        var result = instrumentService.insertNewInstrumentEntry(instrument, image);
        var instrumentEntry = instrumentService.getSpecificInstrument(result.getId());

        // assert
        assertNotNull(instrumentEntry);
        assertEquals(instrumentEntry.getInstrumentDate(), INSTRUMENT_DATE);
        assertEquals(instrumentEntry.getInstrumentTitle(), INSTRUMENT_TITLE);
        assertEquals(instrumentEntry.getDonator(), INSTRUMENT_DONATOR);
        assertEquals(instrumentEntry.getTaken(), INSTRUMENT_TAKEN);
        assertNull(instrumentEntry.getInstrumentImage());
    }

    @Test
    public void shouldGetOneSpecificInstrumentEntryWithImage() throws IOException {
        // arrange
        Instrument instrument = new Instrument();
        instrument.setInstrumentDate(INSTRUMENT_DATE);
        instrument.setInstrumentTitle(INSTRUMENT_TITLE);
        instrument.setDonator(INSTRUMENT_DONATOR);
        instrument.setTaken(INSTRUMENT_TAKEN);

        File file = new File("src/test/resources/upload/test_1.jpg");
        FileInputStream input = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile("test_1.jpg",
                file.getName(), "image/jpg", IOUtils.toByteArray(input));

        Optional<MultipartFile> image = Optional.of(multipartFile);

        // act
        var result = instrumentService.insertNewInstrumentEntry(instrument, image);
        var instrumentEntry = instrumentService.getSpecificInstrument(result.getId());

        // assert
        assertNotNull(instrumentEntry);
        assertEquals(instrumentEntry.getInstrumentDate(), INSTRUMENT_DATE);
        assertEquals(instrumentEntry.getInstrumentTitle(), INSTRUMENT_TITLE);
        assertEquals(instrumentEntry.getDonator(), INSTRUMENT_DONATOR);
        assertEquals(instrumentEntry.getTaken(), INSTRUMENT_TAKEN);
        assertNotNull(instrumentEntry.getInstrumentImage());
    }

    @Test
    public void shouldGetAllInstrumentEntries() throws IOException {
        // arrange
        Instrument instrument = new Instrument();
        instrument.setInstrumentDate(INSTRUMENT_DATE);
        instrument.setInstrumentTitle(INSTRUMENT_TITLE);
        instrument.setDonator(INSTRUMENT_DONATOR);
        instrument.setTaken(INSTRUMENT_TAKEN);
        Optional<MultipartFile> image = Optional.empty();

        Instrument instrument2 = new Instrument();
        instrument2.setInstrumentDate(INSTRUMENT_DATE_2);
        instrument2.setInstrumentTitle(INSTRUMENT_TITLE_2);
        instrument2.setDonator(INSTRUMENT_DONATOR_2);
        instrument2.setTaken(INSTRUMENT_TAKEN_2);
        Optional<MultipartFile> image2 = Optional.empty();

        // act
        var result = instrumentService.insertNewInstrumentEntry(instrument, image);
        var result2 = instrumentService.insertNewInstrumentEntry(instrument2, image2);
        var entries = instrumentService.getAllInstruments();

        assertNotNull(entries.get(0));
        assertEquals(entries.get(0).getInstrumentDate(), INSTRUMENT_DATE);
        assertEquals(entries.get(0).getInstrumentTitle(), INSTRUMENT_TITLE);
        assertEquals(entries.get(0).getDonator(), INSTRUMENT_DONATOR);
        assertEquals(entries.get(0).getTaken(), INSTRUMENT_TAKEN);
        assertNull(entries.get(0).getInstrumentImage());
        assertNotNull(entries.get(1));
        assertEquals(entries.get(1).getInstrumentDate(), INSTRUMENT_DATE_2);
        assertEquals(entries.get(1).getInstrumentTitle(), INSTRUMENT_TITLE_2);
        assertEquals(entries.get(1).getDonator(), INSTRUMENT_DONATOR_2);
        assertEquals(entries.get(1).getTaken(), INSTRUMENT_TAKEN_2);
        assertNull(entries.get(1).getInstrumentImage());
    }

    @Test
    public void shouldInsertNewInstrumentEntryWithImage() throws IOException {
        // arrange
        Instrument instrument = new Instrument();
        instrument.setInstrumentDate(INSTRUMENT_DATE);
        instrument.setInstrumentTitle(INSTRUMENT_TITLE);
        instrument.setDonator(INSTRUMENT_DONATOR);
        instrument.setTaken(INSTRUMENT_TAKEN);

        File file = new File("src/test/resources/upload/test_1.jpg");
        FileInputStream input = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile("test_1.jpg",
                file.getName(), "image/jpg", IOUtils.toByteArray(input));

        Optional<MultipartFile> image = Optional.of(multipartFile);

        // act
        var result = instrumentService.insertNewInstrumentEntry(instrument, image);

        // assert
        assertNotNull(result);
        assertEquals(result.getInstrumentDate(), INSTRUMENT_DATE);
        assertEquals(result.getInstrumentTitle(), INSTRUMENT_TITLE);
        assertEquals(result.getDonator(), INSTRUMENT_DONATOR);
        assertEquals(result.isTaken(), INSTRUMENT_TAKEN);
        assertNotNull(result.getInstrumentImage());
    }

    @Test
    public void shouldInsertNewInstrumentEntryWithoutImage() throws IOException {
        // arrange
        Instrument instrument = new Instrument();
        instrument.setInstrumentDate(INSTRUMENT_DATE);
        instrument.setInstrumentTitle(INSTRUMENT_TITLE);
        instrument.setDonator(INSTRUMENT_DONATOR);
        instrument.setTaken(INSTRUMENT_TAKEN);

        Optional<MultipartFile> image = Optional.empty();

        // act
        var result = instrumentService.insertNewInstrumentEntry(instrument, image);

        // assert
        assertNotNull(result);
        assertEquals(result.getInstrumentDate(), INSTRUMENT_DATE);
        assertEquals(result.getInstrumentTitle(), INSTRUMENT_TITLE);
        assertEquals(result.getDonator(), INSTRUMENT_DONATOR);
        assertEquals(result.isTaken(), INSTRUMENT_TAKEN);
        assertNull(result.getInstrumentImage());
    }

    @Test
    public void shouldUpdateExistingInstrumentEntryWithDifferentData() throws IOException {
        // arrange
        Instrument instrument = new Instrument();
        instrument.setInstrumentDate(INSTRUMENT_DATE);
        instrument.setInstrumentTitle(INSTRUMENT_TITLE);
        instrument.setDonator(INSTRUMENT_DONATOR);
        instrument.setTaken(INSTRUMENT_TAKEN);

        Optional<MultipartFile> image = Optional.empty();

        Instrument instrument2 = new Instrument();
        instrument2.setInstrumentDate(INSTRUMENT_DATE_2);
        instrument2.setInstrumentTitle(INSTRUMENT_TITLE);
        instrument2.setDonator(INSTRUMENT_DONATOR_2);
        instrument2.setTaken(INSTRUMENT_TAKEN);

        Optional<MultipartFile> image2 = Optional.empty();

        // act
        var result = instrumentService.insertNewInstrumentEntry(instrument, image);
        var updatedResult = instrumentService.updateInstrumentEntry(result.getId(), instrument2, image2, false);

        // assert
        assertNotNull(result);
        assertEquals(result.getInstrumentDate(), INSTRUMENT_DATE);
        assertEquals(result.getInstrumentTitle(), INSTRUMENT_TITLE);
        assertEquals(result.getDonator(), INSTRUMENT_DONATOR);
        assertEquals(result.isTaken(), INSTRUMENT_TAKEN);
        assertNull(result.getInstrumentImage());

        assertNotNull(updatedResult);
        assertEquals(updatedResult.getInstrumentDate(), INSTRUMENT_DATE_2);
        assertEquals(updatedResult.getInstrumentTitle(), INSTRUMENT_TITLE);
        assertEquals(updatedResult.getDonator(), INSTRUMENT_DONATOR_2);
        assertEquals(updatedResult.isTaken(), INSTRUMENT_TAKEN);
        assertNull(updatedResult.getInstrumentImage());
    }

    @Test
    public void shouldUpdateExistingInstrumentEntryWithNewImage() throws IOException {
        // arrange
        Instrument instrument = new Instrument();
        instrument.setInstrumentDate(INSTRUMENT_DATE);
        instrument.setInstrumentTitle(INSTRUMENT_TITLE);
        instrument.setDonator(INSTRUMENT_DONATOR);
        instrument.setTaken(INSTRUMENT_TAKEN);

        Optional<MultipartFile> image = Optional.empty();

        Instrument instrument2 = new Instrument();
        instrument2.setInstrumentDate(INSTRUMENT_DATE_2);
        instrument2.setInstrumentTitle(INSTRUMENT_TITLE);
        instrument2.setDonator(INSTRUMENT_DONATOR_2);
        instrument2.setTaken(INSTRUMENT_TAKEN);

        File file = new File("src/test/resources/upload/test_1.jpg");
        FileInputStream input = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile("test_1.jpg",
                file.getName(), "image/jpg", IOUtils.toByteArray(input));

        Optional<MultipartFile> image2 = Optional.of(multipartFile);

        // act
        var result = instrumentService.insertNewInstrumentEntry(instrument, image);
        var updatedResult = instrumentService.updateInstrumentEntry(result.getId(), instrument2, image2, false);

        // assert
        assertNotNull(result);
        assertEquals(result.getInstrumentDate(), INSTRUMENT_DATE);
        assertEquals(result.getInstrumentTitle(), INSTRUMENT_TITLE);
        assertEquals(result.getDonator(), INSTRUMENT_DONATOR);
        assertEquals(result.isTaken(), INSTRUMENT_TAKEN);
        assertNull(result.getInstrumentImage());

        assertNotNull(updatedResult);
        assertEquals(updatedResult.getInstrumentDate(), INSTRUMENT_DATE_2);
        assertEquals(updatedResult.getInstrumentTitle(), INSTRUMENT_TITLE);
        assertEquals(updatedResult.getDonator(), INSTRUMENT_DONATOR_2);
        assertEquals(updatedResult.isTaken(), INSTRUMENT_TAKEN);
        assertNotNull(updatedResult.getInstrumentImage());
    }

    @Test
    public void shouldRemoveImageFromExistingInstrumentEntry() throws IOException {
        // arrange
        Instrument instrument = new Instrument();
        instrument.setInstrumentDate(INSTRUMENT_DATE);
        instrument.setInstrumentTitle(INSTRUMENT_TITLE);
        instrument.setDonator(INSTRUMENT_DONATOR);
        instrument.setTaken(INSTRUMENT_TAKEN);

        File file = new File("src/test/resources/upload/test_1.jpg");
        FileInputStream input = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile("test_1.jpg",
                file.getName(), "image/jpg", IOUtils.toByteArray(input));

        Optional<MultipartFile> image = Optional.of(multipartFile);

        Instrument instrument2 = new Instrument();
        instrument2.setInstrumentDate(INSTRUMENT_DATE_2);
        instrument2.setInstrumentTitle(INSTRUMENT_TITLE);
        instrument2.setDonator(INSTRUMENT_DONATOR_2);
        instrument2.setTaken(INSTRUMENT_TAKEN);

        Optional<MultipartFile> image2 = Optional.empty();

        // act
        var result = instrumentService.insertNewInstrumentEntry(instrument, image);
        var updatedResult = instrumentService.updateInstrumentEntry(result.getId(), instrument2, image2, true);

        // assert
        assertNotNull(result);
        assertEquals(result.getInstrumentDate(), INSTRUMENT_DATE);
        assertEquals(result.getInstrumentTitle(), INSTRUMENT_TITLE);
        assertEquals(result.getDonator(), INSTRUMENT_DONATOR);
        assertEquals(result.isTaken(), INSTRUMENT_TAKEN);
        assertNotNull(result.getInstrumentImage());

        assertNotNull(updatedResult);
        assertEquals(updatedResult.getInstrumentDate(), INSTRUMENT_DATE_2);
        assertEquals(updatedResult.getInstrumentTitle(), INSTRUMENT_TITLE);
        assertEquals(updatedResult.getDonator(), INSTRUMENT_DONATOR_2);
        assertEquals(updatedResult.isTaken(), INSTRUMENT_TAKEN);
        assertNull(updatedResult.getInstrumentImage());
    }

    @Test
    public void shouldDeleteInstrumentEntryWithImage() throws IOException {
        // arrange
        Instrument instrument = new Instrument();
        instrument.setInstrumentDate(INSTRUMENT_DATE);
        instrument.setInstrumentTitle(INSTRUMENT_TITLE);
        instrument.setDonator(INSTRUMENT_DONATOR);
        instrument.setTaken(INSTRUMENT_TAKEN);

        File file = new File("src/test/resources/upload/test_1.jpg");
        FileInputStream input = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile("test_1.jpg",
                file.getName(), "image/jpg", IOUtils.toByteArray(input));

        Optional<MultipartFile> image = Optional.of(multipartFile);

        // act
        var result = instrumentService.insertNewInstrumentEntry(instrument, image);
        instrumentService.deleteInstrumentEntry(result.getId());

        Throwable exception = assertThrows(NotFoundException.class, () -> instrumentService.getSpecificInstrument(result.getId()));

        // assert
        assertEquals(result.getInstrumentDate(), INSTRUMENT_DATE);
        assertEquals(result.getInstrumentTitle(), INSTRUMENT_TITLE);
        assertEquals(result.getDonator(), INSTRUMENT_DONATOR);
        assertEquals(result.isTaken(), INSTRUMENT_TAKEN);
        assertNotNull(result.getInstrumentImage());

        Path filePath = Paths.get(result.getInstrumentImage().getLocation());
        assertFalse(Files.exists(filePath));

        assertEquals("Kein Eintrag mit der Id " + result.getId() + " gefunden", exception.getMessage());

    }

    @Test
    public void shouldDeleteInstrumentEntryWithoutImage() throws IOException {
        // arrange
        Instrument instrument = new Instrument();
        instrument.setInstrumentDate(INSTRUMENT_DATE);
        instrument.setInstrumentTitle(INSTRUMENT_TITLE);
        instrument.setDonator(INSTRUMENT_DONATOR);
        instrument.setTaken(INSTRUMENT_TAKEN);

        Optional<MultipartFile> image = Optional.empty();

        // act
        var result = instrumentService.insertNewInstrumentEntry(instrument, image);
        instrumentService.deleteInstrumentEntry(result.getId());

        Throwable exception = assertThrows(NotFoundException.class, () -> instrumentService.getSpecificInstrument(result.getId()));

        // assert
        assertEquals(result.getInstrumentDate(), INSTRUMENT_DATE);
        assertEquals(result.getInstrumentTitle(), INSTRUMENT_TITLE);
        assertEquals(result.getDonator(), INSTRUMENT_DONATOR);
        assertEquals(result.isTaken(), INSTRUMENT_TAKEN);
        assertNull(result.getInstrumentImage());

        assertEquals("Kein Eintrag mit der Id " + result.getId() + " gefunden", exception.getMessage());
    }
}
