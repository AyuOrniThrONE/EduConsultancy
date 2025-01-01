package com.pol.feedback_service.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FeedBackResponseDTO {
    private UUID id;
    private String email;
    private String name;
    private String feedback;
}
