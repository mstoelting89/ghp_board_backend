package com.example.system.demand;

import com.example.system.attachment.AttachmentResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DemandEntryDto {
    private Long id;
    private LocalDateTime demandDate;
    private String demandTitle;
    private String demandText;
    private String demandName;
    private List<AttachmentResponse> demandImages;
}
