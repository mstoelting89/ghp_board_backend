package com.example.system.demand;


import com.example.system.attachment.Attachment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Demand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime demandDate;

    @Column(nullable = false)
    private String demandTitle;

    @Column(nullable = false)
    private String demandText;

    @Column(nullable = false)
    private String demandName;

    @OneToMany
    private List<Attachment> demandImages;

    public Demand(LocalDateTime demandDate, String demandTitle, String demandText, String demandName, List<Attachment> demandImages) {
        this.demandDate = demandDate;
        this.demandTitle = demandTitle;
        this.demandText = demandText;
        this.demandName = demandName;
        this.demandImages = demandImages;
    }
}
