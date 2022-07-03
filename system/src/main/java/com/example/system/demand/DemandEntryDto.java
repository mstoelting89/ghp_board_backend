package com.example.system.demand;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class DemandEntryDto {
    private Long id;
    private LocalDateTime demandDate;
    private String demandTitle;
    private String demandText;
    private String demandName;
    private List<String> demandImages;
}
