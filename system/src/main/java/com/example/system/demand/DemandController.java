package com.example.system.demand;

import com.example.system.security.jwt.JwtAuthenticationService;
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

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@CrossOrigin
public class DemandController {

    private DemandService demandService;
    private JwtAuthenticationService jwtAuthenticationService;

    @GetMapping(path = "/api/v1/demand", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllDemands(HttpServletRequest request) {
        final String token = jwtAuthenticationService.extractTokenFromRequest(request);
        final String email = jwtAuthenticationService.getEmailFromToken(token);
        return new ResponseEntity<>(demandService.getAllDemandEntries(email), HttpStatus.OK);
    }

    @GetMapping(path = "/api/v1/demand/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getDemandById(@PathVariable("id") Long id, HttpServletRequest request) {
        final String token = jwtAuthenticationService.extractTokenFromRequest(request);
        final String email = jwtAuthenticationService.getEmailFromToken(token);
        return new ResponseEntity<>(demandService.getDemandById(id, email), HttpStatus.OK);
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
    public ResponseEntity<?> deleteDemand(@PathVariable("id") Long demandDeleteId) throws IOException {
        demandService.deleteDemandEntry(demandDeleteId);
        return new ResponseEntity<>("", HttpStatus.OK);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handlerNotFoundException(NotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
}
