package com.example.system.instrument;

import com.example.system.attachment.AttachmentResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class InstrumentResponseDto {
    private Long id;
    private LocalDateTime instrumentDate;
    private String instrumentTitle;
    private String instrumentImage;
    private String donator;
    private Boolean taken;
}
