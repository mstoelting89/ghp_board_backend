package com.example.system.voting;

import com.example.system.demand.Demand;
import com.example.system.user.User;

import java.util.Optional;

public interface VotingService {

    VotingResponseDto setVotingValue(VotingEntryDto votingEntryDto, String email);

    Optional<VotingResponseDto> getVotingValue(Demand demand);

    Optional<Boolean> getVotingByUser(Demand demand, User user);
}
