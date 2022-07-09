package com.example.system.voting;

import com.example.system.demand.Demand;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class VotingEntryDto {
    private Long demandId;
    private Long voteValue;
}
