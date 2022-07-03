package com.example.system.demand;

import com.example.system.attachment.Attachment;
import com.example.system.attachment.AttachmentServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.webjars.NotFoundException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DemandServiceImpl implements DemandService {

    private DemandRepository demandRepository;
    private AttachmentServiceImpl attachmentServiceImpl;

    @Override
    public List<Demand> getAllDemandEntries() {
        return demandRepository.findAllByOrderByDemandDateDesc();
    }

    @Override
    public DemandEntryDto getDemandById(Long id) {
        var demand = demandRepository.findById(id).orElseThrow(() -> new NotFoundException("Keine Anfrage mit der id " + id + " gefunden"));
        List<String> attachments = new ArrayList<>();

        if(demand.getDemandImages() != null) {
            attachments = attachmentServiceImpl.getAttachmentListAsBase64(id);
        }

        return new DemandEntryDto(
                demand.getId(),
                demand.getDemandDate(),
                demand.getDemandTitle(),
                demand.getDemandText(),
                demand.getDemandName(),
                attachments
        );
    }

    @Override
    public Demand insertNewDemandEntry(DemandEntryDto demandEntryDto, Optional<List<MultipartFile>> file) {

        List<MultipartFile> fileList = file.orElse(Collections.emptyList());
        List<Attachment> attachments = attachmentServiceImpl.handleAttachmentUploadList(fileList);

        if( demandEntryDto.getDemandName() == null ||
                demandEntryDto.getDemandText() == null ||
                demandEntryDto.getDemandTitle() == null
        ) {
            throw new NotFoundException("Speichern fehlgeschlagen - Eintrag nicht vollständig");
        }
        return demandRepository.save(new Demand(
                demandEntryDto.getDemandDate(),
                demandEntryDto.getDemandTitle(),
                demandEntryDto.getDemandText(),
                demandEntryDto.getDemandName(),
                attachments
        ));
    }
}
