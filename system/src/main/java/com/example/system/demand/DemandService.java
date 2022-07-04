package com.example.system.demand;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface DemandService {

    List<Demand> getAllDemandEntries();

    DemandEntryDto getDemandById(Long id);

    Demand insertNewDemandEntry(DemandEntryDto demandEntryDto, Optional<List<MultipartFile>> file);

    Demand updateDemandEntry(DemandEntryDto demandEntryDto, Long demandId, Optional<List<MultipartFile>> file);
}
