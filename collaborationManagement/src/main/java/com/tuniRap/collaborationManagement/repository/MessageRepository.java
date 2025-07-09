package com.tuniRap.collaborationManagement.repository;


import com.tuniRap.collaborationManagement.model.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    Page<Message> findByCollaboration_Id(Long collaborationId, Pageable pageable);

    List<Message> findByCollaboration_IdOrderByTimestampAsc(Long collaborationId);

}
