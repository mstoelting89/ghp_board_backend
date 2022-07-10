package com.example.system.instrument;

import com.example.system.attachment.AttachmentService;
import com.example.system.news.News;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.webjars.NotFoundException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class InstrumentServiceImpl implements InstrumentService {

    private InstrumentRepository instrumentRepository;
    private AttachmentService attachmentService;

    @Override
    public List<InstrumentResponseDto> getAllInstruments() {
        List<InstrumentResponseDto> instruments = new ArrayList<>();
        instrumentRepository.findAll().forEach(instrument -> {
            String attachment = null;
            if (instrument.getInstrumentImage() != null) {
                attachment = attachmentService.getAttachmentAsBase64(instrument.getInstrumentImage().getId());
            }

            instruments.add(new InstrumentResponseDto(
                    instrument.getId(),
                    instrument.getInstrumentDate(),
                    instrument.getInstrumentTitle(),
                    attachment
            ));
        });

        return instruments;
    }

    @Override
    public Instrument insertNewDemandEntry(Instrument instrumentDto, Optional<MultipartFile> file) throws IOException {
        var attachment = attachmentService.handelAttachmentUpload(file);

        return instrumentRepository.save(new Instrument(
                instrumentDto.getInstrumentDate(),
                instrumentDto.getInstrumentTitle(),
                attachment
        ));
    }
}
