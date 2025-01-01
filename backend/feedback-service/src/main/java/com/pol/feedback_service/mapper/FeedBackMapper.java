package com.pol.feedback_service.mapper;


import com.pol.feedback_service.DTO.FeedBackRequestDTO;
import com.pol.feedback_service.DTO.FeedBackResponseDTO;
import com.pol.feedback_service.entity.Feedback;

public class FeedBackMapper {

//    public static Feedback toEntitu(FeedBackRequestDTO feedBackRequestDTO) {
//        return Feedback.builder()
//                .
//    }

    public static FeedBackResponseDTO toResponse(Feedback feedback) {
        return FeedBackResponseDTO.builder()
                .id(feedback.getId())
                .email(feedback.getEmail())
                .name(feedback.getName())
                .feedback(feedback.getFeedback())
                .build();
    }
}
