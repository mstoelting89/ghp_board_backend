package com.example.system.instrument;

import com.example.system.demand.DemandEntryDto;
import com.example.system.news.NewsEntryDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Optional;

@RestController
@AllArgsConstructor
@CrossOrigin
public class InstrumentController {

    private InstrumentService instrumentService;

    @GetMapping(path = "/api/v1/instrument/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllDemands(HttpServletRequest request) {
        return new ResponseEntity<>(instrumentService.getAllInstruments(), HttpStatus.OK);
    }

    @PostMapping(
            path = "/api/v1/instrument",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> insertNewInstrument(
            @RequestParam Optional<MultipartFile> file,
            @RequestParam String instrumentData
    ) throws IOException {

        ObjectMapper mapper = new ObjectMapper()
                .findAndRegisterModules()
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        Instrument instrumentDto = mapper.readValue(instrumentData, Instrument.class);

        return new ResponseEntity<>(instrumentService.insertNewInstrumentEntry(instrumentDto, file), HttpStatus.OK);
    }

    @PutMapping(
            path = "/api/v1/instrument",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> updateInstrument(
            @RequestParam("file") Optional<MultipartFile> file,
            @RequestParam("instrumentUpdateId") Long instrumentUpdateId,
            @RequestParam("instrumentData") String instrumentUpdateString,
            @RequestParam("instrumentImageDelete") boolean instrumentImageDelete,
            HttpServletRequest request) throws IOException
    {
        ObjectMapper mapper = new ObjectMapper()
                .findAndRegisterModules()
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        Instrument instrumentDto = mapper.readValue(instrumentUpdateString, Instrument.class);

        return new ResponseEntity<>(instrumentService.updateInstrumentEntry(instrumentUpdateId, instrumentDto, file, instrumentImageDelete), HttpStatus.OK);
    }

    @DeleteMapping(
            path = "/api/v1/instrument/{id}"
    )
    public ResponseEntity<?> deleteInstrument(@PathVariable("id") Long instrumentDeleteId) throws IOException {
        instrumentService.deleteInstrumentEntry(instrumentDeleteId);
        return new ResponseEntity<>("", HttpStatus.OK);
    }
}
