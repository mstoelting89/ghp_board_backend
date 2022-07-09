package com.example.system.voting;

import com.example.system.demand.DemandRepository;
import com.example.system.security.jwt.JwtAuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RestController
@AllArgsConstructor
@CrossOrigin
public class VotingController {

    private JwtAuthenticationService jwtAuthenticationService;
    private VotingService votingService;
    private DemandRepository demandRepository;

    @GetMapping(path = "/api/v1/voting/{id}")
    public ResponseEntity<Optional<VotingResponseDto>> getVote(@PathVariable("id") Long demandId) {
        var demand = demandRepository.findById(demandId).orElseThrow(() -> new NotFoundException("Anfrage nicht gefunden"));
        return new ResponseEntity<Optional<VotingResponseDto>>(votingService.getVotingValue(demand), HttpStatus.OK);
    }

    @PostMapping(path = "/api/v1/voting")
    public ResponseEntity<VotingResponseDto> setVote(@RequestBody VotingEntryDto votingEntryDto, HttpServletRequest request) {
        final String token = jwtAuthenticationService.extractTokenFromRequest(request);
        final String email = jwtAuthenticationService.getEmailFromToken(token);

        return new ResponseEntity<VotingResponseDto>(votingService.setVotingValue(votingEntryDto, email), HttpStatus.OK);
    }
}
