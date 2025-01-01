package com.pol.feedback_service.controller;


import com.pol.feedback_service.DTO.FeedBackPageResponseDTO;
import com.pol.feedback_service.DTO.FeedBackRequestDTO;
import com.pol.feedback_service.DTO.FeedBackResponseDTO;
import com.pol.feedback_service.service.FeedBackService;
import com.pol.feedback_service.utils.AppConstants;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/feedback")
public class FeedbackController {

    private final FeedBackService feedBackService;

    public FeedbackController(FeedBackService feedBackService) {
        this.feedBackService = feedBackService;
    }

    @PostMapping
    public ResponseEntity<FeedBackResponseDTO> createFeedback(@RequestBody @Valid FeedBackRequestDTO feedBackRequestDTO){
        return ResponseEntity.ok(feedBackService.createFeedback(feedBackRequestDTO));
    }
}
