package com.example.system.blog;

import com.example.system.attachment.Attachment;
import com.example.system.demand.Demand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BlogRespository  extends JpaRepository<Blog, Long> {
    List<Blog> findAllByOrderByBlogDateDesc();

}
