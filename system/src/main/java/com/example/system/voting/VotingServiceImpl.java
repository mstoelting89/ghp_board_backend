package com.example.system.voting;

import com.example.system.demand.Demand;
import com.example.system.demand.DemandRepository;
import com.example.system.user.User;
import com.example.system.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.Optional;

@Service
@AllArgsConstructor
public class VotingServiceImpl implements VotingService{

    private final UserService userService;
    private final VotingRepository votingRepository;
    private final DemandRepository demandRepository;

    @Override
    public VotingResponseDto setVotingValue(VotingEntryDto votingEntryDto, String email) {
        Voting votingResponse;
        User user = userService.loadUserByMail(email);
        Demand demand = demandRepository.findById(votingEntryDto.getDemandId())
                .orElseThrow(() -> new NotFoundException("Anfrage nicht gefunden"));

        boolean voting = votingEntryDto.getVoteValue() == 1;
        var previousVoting = votingRepository.findByDemandAndUser(demand, user).orElseGet(() -> votingRepository.save(new Voting(
                user,
                demand,
                voting
        )));

        if (!previousVoting.getVote().equals(voting)) {
            previousVoting.setVote(voting);
            votingResponse = votingRepository.save(previousVoting);
        }


        return new VotingResponseDto(
                demand.getId(),
                votingRepository.countVotingByDemandAndVoteEquals(demand, true),
                votingRepository.countVotingByDemandAndVoteEquals(demand, false)
        );
    }

    @Override
    public Optional<VotingResponseDto> getVotingValue(Demand demand) {


        return Optional.of(new VotingResponseDto(
                demand.getId(),
                votingRepository.countVotingByDemandAndVoteEquals(demand, true),
                votingRepository.countVotingByDemandAndVoteEquals(demand, false))
        );
    }

    @Override
    public Optional<Boolean> getVotingByUser(Demand demand, User user) {
        var voting = votingRepository.findByDemandAndUser(demand, user);

        if (voting.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(voting.get().getVote());
    }
}
