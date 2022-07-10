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

    @OneToOne(cascade = CascadeType.ALL)
    private Attachment instrumentImage;

    public Instrument(LocalDateTime instrumentDate, String instrumentTitle, Attachment instrumentImage) {
        this.instrumentDate = instrumentDate;
        this.instrumentTitle = instrumentTitle;
        this.instrumentImage = instrumentImage;
    }
}
