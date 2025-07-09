package com.tuniRap.collaborationManagement.repository;

import com.tuniRap.collaborationManagement.model.Collaboration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface CollaborationRepository extends JpaRepository<Collaboration, Long> {
    Collaboration  getCollaborationById(Long collaborationId);
}
