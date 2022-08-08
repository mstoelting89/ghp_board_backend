package com.example.system.instrument;

import com.example.system.attachment.AttachmentService;
import com.example.system.user.UserService;
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
    private UserService userService;

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
    public Instrument insertNewInstrumentEntry(Instrument instrumentDto, Optional<MultipartFile> file) throws IOException {

        userService.sendToAll("new-instrument", "Guitar Hearts Project: Neues Instrument vorhanden");

        if (file.isPresent()) {
            var attachment = attachmentService.handelAttachmentUpload(
                    file.orElseThrow(() -> new IllegalStateException("Beim Hochladen ist ein Fehler aufgetreten")),
                    "/upload/images/"
            );

            return instrumentRepository.save(new Instrument(
                    instrumentDto.getInstrumentDate(),
                    instrumentDto.getInstrumentTitle(),
                    attachment
            ));
        } else {
            return instrumentRepository.save(new Instrument(
                    instrumentDto.getInstrumentDate(),
                    instrumentDto.getInstrumentTitle(),
                    null
            ));
        }

    }

    @Override
    public void deleteInstrumentEntry(Long instrumentDeleteId) {
        var instrumentEntry = instrumentRepository.findById(instrumentDeleteId)
                .orElseThrow(() -> new NotFoundException("Löschen fehlgeschlagen - Eintrag mit der ID " + instrumentDeleteId + " nicht gefunden"));

        instrumentRepository.delete(instrumentEntry);
    }

    @Override
    public Instrument updateInstrumentEntry(Long instrumentUpdateId, Instrument instrumentDto, Optional<MultipartFile> file) throws IOException {
        var instrumentEntry = instrumentRepository.findById(instrumentUpdateId)
                .orElseThrow(() -> new NotFoundException("Kein Eintrag mit der Id " + instrumentUpdateId + " gefunden"));

        if (instrumentDto.getInstrumentImage() != null) {
            attachmentService.deleteImage(instrumentEntry.getInstrumentImage().getId());
        }

        instrumentEntry.setInstrumentTitle(instrumentDto.getInstrumentTitle());
        instrumentEntry.setInstrumentDate(instrumentDto.getInstrumentDate());

        if (file.isPresent()) {
            var attachment = attachmentService.handelAttachmentUpload(
                    file.orElseThrow(() -> new IllegalStateException("Beim Hochladen ist ein Fehler aufgetreten")),
                    "/upload/images/"
            );
            instrumentEntry.setInstrumentImage(attachment);
        } else {
            instrumentEntry.setInstrumentImage(null);
        }

        return instrumentRepository.save(instrumentEntry);
    }
}
