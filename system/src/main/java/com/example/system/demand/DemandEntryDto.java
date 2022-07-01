package com.example.system.demand;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
public class DemandEntryDto {
    private LocalDateTime demandDate;
    private String demandTitle;
    private String demandText;
    private String demandName;
}
