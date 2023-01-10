package com.example.system.instrument;

import com.example.system.attachment.Attachment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Instrument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime instrumentDate;

    @Column(nullable = false)
    private String instrumentTitle;

    private String donator;

    @Column(nullable = false)
    private boolean taken;

    @OneToOne(fetch = FetchType.EAGER, cascade={CascadeType.MERGE,CascadeType.REFRESH})
    private Attachment instrumentImage;

    public Instrument(LocalDateTime instrumentDate, String instrumentTitle, Attachment instrumentImage, String donator, Boolean taken) {
        this.instrumentDate = instrumentDate;
        this.instrumentTitle = instrumentTitle;
        this.instrumentImage = instrumentImage;
        this.donator = donator;
        this.taken = taken;
    }
}
