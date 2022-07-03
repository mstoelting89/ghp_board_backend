package com.example.system.news;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {

    List<News> findAllByOrderByNewsDateDesc();

    @Transactional
    @Modifying
    @Query("UPDATE News n SET n.newsImage = null WHERE n.id = ?1")
    void deleteImage(Long id);
}
