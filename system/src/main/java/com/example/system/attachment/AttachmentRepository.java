package com.example.system.attachment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface AttachmentRepository  extends JpaRepository<Attachment, Long> {

    @Query("SELECT d.demandImages FROM Demand d WHERE d.id = ?1")
    List<Attachment> getAttachmentById(Long id);
}
