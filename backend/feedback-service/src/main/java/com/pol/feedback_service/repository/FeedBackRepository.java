package com.pol.feedback_service.repository;

import com.pol.feedback_service.DTO.FeedBackResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.pol.feedback_service.entity.Feedback;

import java.util.UUID;

@Repository
public interface FeedBackRepository extends JpaRepository<Feedback, UUID> {

    @Query(
            "SELECT new com.pol.feedback_service.DTO.FeedBackResponseDTO(f.id, f.email, f.name, f.feedback) FROM Feedback f"
    )
    Page<FeedBackResponseDTO> findAllFeedBacks(Pageable pageable);
}
