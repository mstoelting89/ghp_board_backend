package com.example.system.instrument;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface InstrumentService {

    InstrumentResponseDto getSpecificInstrument(Long instrumentId);
    List<InstrumentResponseDto> getAllInstruments();

    Instrument insertNewInstrumentEntry(Instrument instrumentDto, Optional<MultipartFile> file) throws IOException;

    void deleteInstrumentEntry(Long instrumentDeleteId) throws IOException;

    Instrument updateInstrumentEntry(Long instrumentUpdateId, Instrument instrumentDto, Optional<MultipartFile> file, boolean instrumentImageDelete) throws IOException;
}
