package com.example.system.demand;

import com.example.system.attachment.Attachment;
import com.example.system.attachment.AttachmentServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.webjars.NotFoundException;

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
    public Demand getDemandById(Long id) {
        return demandRepository.findById(id).orElseThrow(() -> new NotFoundException("Keine Anfrage mit der id " + id + " gefunden"));
    }

    @Override
    public Demand insertNewDemandEntry(DemandEntryDto demandEntryDto, Optional<List<MultipartFile>> file) {

        List<MultipartFile> fileList = file.orElse(Collections.emptyList());
        List<Attachment> attachments = attachmentServiceImpl.handleAttachmentUploadList(fileList);
        return null;
    }
}
