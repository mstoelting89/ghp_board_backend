package com.example.system.demand;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;

@Service
@AllArgsConstructor
public class DemandServiceImpl implements DemandService {

    private DemandRepository demandRepository;

    @Override
    public List<Demand> getAllDemandEntries() {
        return demandRepository.findAllByOrderByDemandDateDesc();
    }

    @Override
    public Demand getDemandById(Long id) {
        return demandRepository.findById(id).orElseThrow(() -> new NotFoundException("Keine Anfrage mit der id " + id + " gefunden"));
    }
}
