package com.example.system.instrument;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface InstrumentService {
    List<InstrumentResponseDto> getAllInstruments();

    Instrument insertNewDemandEntry(Instrument instrumentDto, Optional<MultipartFile> file) throws IOException;
}
