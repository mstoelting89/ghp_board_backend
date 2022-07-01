package com.example.system.demand;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface DemandService {

    List<Demand> getAllDemandEntries();

    Demand getDemandById(Long id);

    Demand insertNewDemandEntry(DemandEntryDto demandEntryDto, Optional<List<MultipartFile>> file);
}
