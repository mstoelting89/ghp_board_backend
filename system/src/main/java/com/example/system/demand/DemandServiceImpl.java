package com.example.system.demand;

import com.example.system.attachment.Attachment;
import com.example.system.attachment.AttachmentResponse;
import com.example.system.attachment.AttachmentServiceImpl;
import com.example.system.user.UserService;
import com.example.system.user.UserServiceImpl;
import com.example.system.voting.VotingService;
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
    private VotingService votingService;
    private UserService userService;

    @Override
    public List<DemandResponseDto> getAllDemandEntries(String email) {
        var response = new ArrayList<DemandResponseDto>();
        var demands = demandRepository.findAllByOrderByDemandDateDesc();
        var user = userService.loadUserByMail(email);

        demands.forEach(demand -> {
            var images = new ArrayList<AttachmentResponse>();
            var voting = votingService.getVotingValue(demand);
            var personalVoting = votingService.getVotingByUser(demand, user);
            Long likes;
            Long dislikes;
            Optional<Boolean> personalVote;

            if (voting.isEmpty()) {
                likes = 0L;
                dislikes = 0L;
            } else {
                likes = voting.get().getCountLikes();
                dislikes = voting.get().getCountDislikes();
            }

            demand.getDemandImages().forEach(image -> {
                    images.add(new AttachmentResponse(
                            image.getId(),
                            attachmentServiceImpl.getAttachmentAsBase64(image.getId())
                    ));
            });

            response.add(new DemandResponseDto(
                    demand.getId(),
                    demand.getDemandDate(),
                    demand.getDemandTitle(),
                    demand.getDemandText(),
                    demand.getDemandName(),
                    images,
                    likes,
                    dislikes,
                    personalVoting
            ));
        });

        return response;
    }

    @Override
    public DemandResponseDto getDemandById(Long id, String email) {
        var demand = demandRepository.findById(id).orElseThrow(() -> new NotFoundException("Keine Anfrage mit der id " + id + " gefunden"));
        List<AttachmentResponse> attachments = new ArrayList<>();

        var voting = votingService.getVotingValue(demand);
        var personalVoting = votingService.getVotingByUser(demand, userService.loadUserByMail(email));

        if(demand.getDemandImages() != null) {
            var demandAttachments = demandRepository.getAttachmentById(id);
            attachments = attachmentServiceImpl.getAttachmentListAsBase64(id, demandAttachments);
        }

        return new DemandResponseDto(
                demand.getId(),
                demand.getDemandDate(),
                demand.getDemandTitle(),
                demand.getDemandText(),
                demand.getDemandName(),
                attachments,
                voting.get().getCountLikes(),
                voting.get().getCountDislikes(),
                personalVoting
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

        userService.sendToAll("new-demand-entry", "Guitar Hearts Project: Neue Anfrage");

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
        List<Attachment> newAttachments = attachmentServiceImpl.handleAttachmentUploadList(fileList);

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
    public void deleteDemandEntry(Long demandId) {
        var demandEntry = demandRepository.findById(demandId)
                .orElseThrow(() -> new NotFoundException("Löschen fehlgeschlagen - Eintrag mit der ID " + demandId + " nicht gefunden"));

        votingService.deleteByDemand(demandEntry);
        demandRepository.delete(demandEntry);
    }
}
