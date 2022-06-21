package com.example.system.demand;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;

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

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handlerNotFoundException(NotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
}
