package com.example.system.instrument;

import com.example.system.attachment.AttachmentService;
import com.example.system.config.GhpProperties;
import com.example.system.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.webjars.NotFoundException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@EnableConfigurationProperties(GhpProperties.class)
public class InstrumentServiceImpl implements InstrumentService {

    private InstrumentRepository instrumentRepository;
    private AttachmentService attachmentService;
    private UserService userService;
    private final GhpProperties ghpProperties;

    @Override
    public InstrumentResponseDto getSpecificInstrument(Long instrumentId) {
        var instrument = instrumentRepository.findById(instrumentId)
                .orElseThrow(() -> new NotFoundException("Kein Eintrag mit der Id " + instrumentId + " gefunden"));

        String attachment = null;
        if (instrument.getInstrumentImage() != null) {
            attachment = attachmentService.getAttachmentAsBase64(instrument.getInstrumentImage().getId());
        }

        return new InstrumentResponseDto(
                instrument.getId(),
                instrument.getInstrumentDate(),
                instrument.getInstrumentTitle(),
                attachment,
                instrument.getDonator(),
                instrument.isTaken()
        );
    }

    @Override
    public List<InstrumentResponseDto> getAllInstruments() {
        List<InstrumentResponseDto> instruments = new ArrayList<>();
        instrumentRepository.findAllByOrderByInstrumentDateDesc().forEach(instrument -> {
            String attachment = null;
            if (instrument.getInstrumentImage() != null) {
                attachment = attachmentService.getAttachmentAsBase64(instrument.getInstrumentImage().getId());
            }

            instruments.add(new InstrumentResponseDto(
                    instrument.getId(),
                    instrument.getInstrumentDate(),
                    instrument.getInstrumentTitle(),
                    attachment,
                    instrument.getDonator(),
                    instrument.isTaken()
            ));
        });

        return instruments;
    }

    @Override
    public Instrument insertNewInstrumentEntry(Instrument instrumentDto, Optional<MultipartFile> file) throws IOException {

        //userService.sendToAll("new-instrument", "Guitar Hearts Project: Neues Instrument vorhanden");

        if (file.isPresent()) {
            var attachment = attachmentService.handelAttachmentUpload(
                    file.orElseThrow(() -> new IllegalStateException("Beim Hochladen ist ein Fehler aufgetreten")),
                    ghpProperties.uploadDir
            );

            return instrumentRepository.save(new Instrument(
                            instrumentDto.getInstrumentDate(),
                            instrumentDto.getInstrumentTitle(),
                            attachment,
                            instrumentDto.getDonator(),
                            instrumentDto.isTaken()
                    ));
        } else {
            return instrumentRepository.save(new Instrument(
                    instrumentDto.getInstrumentDate(),
                    instrumentDto.getInstrumentTitle(),
                    null,
                    instrumentDto.getDonator(),
                    instrumentDto.isTaken()
            ));
        }

    }

    @Override
    public void deleteInstrumentEntry(Long instrumentDeleteId) throws IOException {
        var instrumentEntry = instrumentRepository.findById(instrumentDeleteId)
                .orElseThrow(() -> new NotFoundException("Löschen fehlgeschlagen - Eintrag mit der ID " + instrumentDeleteId + " nicht gefunden"));

        if (instrumentEntry.getInstrumentImage() != null) {
            instrumentRepository.deleteImage(instrumentDeleteId);
            attachmentService.deleteImage(instrumentEntry.getInstrumentImage().getId());
        }

        instrumentRepository.delete(instrumentEntry);
    }

    @Override
    public Instrument updateInstrumentEntry(Long instrumentUpdateId, Instrument instrumentDto, Optional<MultipartFile> file, boolean instrumentImageDelete) throws IOException {
        var instrumentEntry = instrumentRepository.findById(instrumentUpdateId)
                .orElseThrow(() -> new NotFoundException("Kein Eintrag mit der Id " + instrumentUpdateId + " gefunden"));

        if (instrumentDto.getInstrumentImage() != null && instrumentImageDelete) {
            attachmentService.deleteImage(instrumentEntry.getInstrumentImage().getId());
        }

        instrumentEntry.setInstrumentTitle(instrumentDto.getInstrumentTitle());
        instrumentEntry.setInstrumentDate(instrumentDto.getInstrumentDate());
        instrumentEntry.setDonator(instrumentDto.getDonator());
        instrumentEntry.setTaken(instrumentDto.isTaken());

        if (file.isPresent()) {
            var attachment = attachmentService.handelAttachmentUpload(
                    file.orElseThrow(() -> new IllegalStateException("Beim Hochladen ist ein Fehler aufgetreten")),
                    ghpProperties.uploadDir
            );
            instrumentEntry.setInstrumentImage(attachment);
        } else if (instrumentImageDelete) {
            instrumentEntry.setInstrumentImage(null);
        }

        return instrumentRepository.save(instrumentEntry);
    }
}
