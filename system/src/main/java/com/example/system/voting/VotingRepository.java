package com.example.system.voting;

import com.example.system.demand.Demand;
import com.example.system.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VotingRepository extends JpaRepository<Voting, Long> {

    Optional<Voting> findByDemandAndUser(Demand demand, User user);

    Long countVotingByDemandAndVoteEquals(Demand demand, Boolean vote);
}
