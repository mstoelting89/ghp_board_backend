package com.example.system.demand;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.webjars.NotFoundException;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@CrossOrigin
public class DemandController {

    private DemandService demandService;

    @GetMapping(path = "/api/v1/demand", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllDemands() {
        return new ResponseEntity<>(demandService.getAllDemandEntries(), HttpStatus.OK);
    }

    @GetMapping(path = "/api/v1/demand/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getDemandById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(demandService.getDemandById(id), HttpStatus.OK);
    }

    @PostMapping(
            path = "/api/v1/demand",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> insertNewDemand(
            @RequestParam Optional<List<MultipartFile>> files,
            @RequestParam String demandData
    ) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper()
                .findAndRegisterModules()
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        DemandEntryDto demandEntryDto = mapper.readValue(demandData, DemandEntryDto.class);

        return new ResponseEntity<>(demandService.insertNewDemandEntry(demandEntryDto, files), HttpStatus.OK);
    }

    @PutMapping(
            path = "/api/v1/demand",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> updateDemandEntry(
            @RequestParam Optional<List<MultipartFile>> files,
            @RequestParam Long demandId,
            @RequestParam String demandData
    ) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper()
                .findAndRegisterModules()
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        DemandEntryDto demandEntryDto = mapper.readValue(demandData, DemandEntryDto.class);

        return new ResponseEntity<>(demandService.updateDemandEntry(demandEntryDto, demandId, files), HttpStatus.OK);
    }

    @DeleteMapping(
            path = "/api/v1/demand/{id}"
    )
    public ResponseEntity<?> deleteDemand(@PathVariable("id") Long newsDeleteId) throws IOException {
        demandService.deleteNewsEntry(newsDeleteId);
        return new ResponseEntity<>("", HttpStatus.OK);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handlerNotFoundException(NotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
}
