package com.example.system.demand;

import com.example.system.attachment.Attachment;
import com.example.system.attachment.AttachmentResponse;
import com.example.system.attachment.AttachmentServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.webjars.NotFoundException;

import java.io.IOException;
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
        List<AttachmentResponse> attachments = new ArrayList<>();

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

    @Override
    public Demand updateDemandEntry(DemandEntryDto demandNewDto, Long demandId, Optional<List<MultipartFile>> file) {
        var demandPreviousEntry = demandRepository.findById(demandId)
                .orElseThrow(() -> new NotFoundException("Kein Eintrag mit der Id " + demandId + " gefunden"));
        var newImageIds = new ArrayList<>();
        var toDeleteImages = new ArrayList<Attachment>();
        var existingAttachments = new ArrayList<Attachment>();

        // Get array with only new image ids
        demandNewDto.getDemandImages().forEach(newImage -> {
            newImageIds.add(newImage.getId());
        });

        // check if the new image id is in the previous image array -> if not put them into the delete array
        demandPreviousEntry.getDemandImages().forEach(item -> {
            if (!newImageIds.contains(item.getId())) {
                toDeleteImages.add(item);
            } else {
                existingAttachments.add(item);
            }
        });
        System.out.println("---- gelöschte Bilder ----");
        toDeleteImages.forEach(item -> {
            System.out.println(item.getId());
            System.out.println(item.getLocation());
        });

        // delete the images in the delete array
        toDeleteImages.forEach(deleteImage -> {
            demandPreviousEntry.getDemandImages().remove(deleteImage);
            demandRepository.save(demandPreviousEntry);

            try {
                attachmentServiceImpl.deleteImage(deleteImage.getId());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });


        // handle new images
        List<MultipartFile> fileList = file.orElse(Collections.emptyList());
        System.out.println("---- FileList ----");
        fileList.forEach(item -> {
            System.out.println(item.getOriginalFilename());
        });
        List<Attachment> newAttachments = attachmentServiceImpl.handleAttachmentUploadList(fileList);
        System.out.println("---- Neue Bilder ----");
        newAttachments.forEach(item -> {
            System.out.println(item.getId());
            System.out.println(item.getLocation());
        });
        // merge both attachment arrays (new and old)
        existingAttachments.addAll(newAttachments);

        // update Entry
        if (
                demandNewDto.getDemandTitle() == null ||
                demandNewDto.getDemandText() == null
        ) {
            throw new NotFoundException("Speichern fehlgeschlagen - Eintrag nicht vollständig");
        }

        demandPreviousEntry.setDemandDate(demandNewDto.getDemandDate());
        demandPreviousEntry.setDemandTitle(demandNewDto.getDemandTitle());
        demandPreviousEntry.setDemandName(demandNewDto.getDemandName());
        demandPreviousEntry.setDemandText(demandNewDto.getDemandText());
        demandPreviousEntry.setDemandImages(existingAttachments);

        return demandRepository.save(demandPreviousEntry);
    }

    @Override
    public void deleteNewsEntry(Long demandId) {
        var demandEntry = demandRepository.findById(demandId)
                .orElseThrow(() -> new NotFoundException("Löschen fehlgeschlagen - Eintrag mit der ID " + demandId + " nicht gefunden"));

        demandRepository.delete(demandEntry);
    }
}
