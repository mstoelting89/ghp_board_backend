package com.example.system.instrument;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface InstrumentRepository extends JpaRepository<Instrument, Long> {
    @Transactional
    @Modifying
    @Query("UPDATE Instrument i SET i.instrumentImage = null WHERE i.id = ?1")
    void deleteImage(Long id);
}
