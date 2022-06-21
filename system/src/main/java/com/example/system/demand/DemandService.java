package com.example.system.demand;

import java.util.List;

public interface DemandService {

    List<Demand> getAllDemandEntries();

    Demand getDemandById(Long id);
}
