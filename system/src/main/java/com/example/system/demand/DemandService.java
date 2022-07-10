package com.example.system.demand;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface DemandService {

    List<DemandResponseDto> getAllDemandEntries(String email);

    DemandResponseDto getDemandById(Long id, String email);

    Demand insertNewDemandEntry(DemandEntryDto demandEntryDto, Optional<List<MultipartFile>> file);

    Demand updateDemandEntry(DemandEntryDto demandEntryDto, Long demandId, Optional<List<MultipartFile>> file);

    void deleteDemandEntry(Long newsDeleteId);
}
