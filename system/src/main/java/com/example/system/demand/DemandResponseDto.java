package com.example.system.demand;

import com.example.system.attachment.AttachmentResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Getter
@Setter
public class DemandResponseDto {
    private Long id;
    private LocalDateTime demandDate;
    private String demandTitle;
    private String demandText;
    private String demandName;
    private List<AttachmentResponse> demandImages;
    private Long likes;
    private Long dislikes;
    private Optional<Boolean> personalVote;
}
