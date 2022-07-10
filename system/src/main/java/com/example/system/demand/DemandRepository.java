package com.example.system.demand;

import com.example.system.attachment.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface DemandRepository extends JpaRepository<Demand, Long> {
    List<Demand> findAllByOrderByDemandDateDesc();

    @Transactional
    @Modifying
    @Query("UPDATE Demand SET demandImages = null WHERE id = ?1")
    void deleteDemandImages(Long id);

    @Query("SELECT d.demandImages FROM Demand d WHERE d.id = ?1")
    List<Attachment> getAttachmentById(Long id);
}
