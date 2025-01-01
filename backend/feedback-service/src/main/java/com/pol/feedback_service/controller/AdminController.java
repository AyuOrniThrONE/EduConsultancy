package com.pol.feedback_service.controller;

import com.pol.feedback_service.DTO.FeedBackPageResponseDTO;
import com.pol.feedback_service.service.FeedBackService;
import com.pol.feedback_service.utils.AppConstants;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private final FeedBackService feedBackService;


    public AdminController(FeedBackService feedBackService) {
        this.feedBackService = feedBackService;
    }

    @GetMapping("/feedbacks")
    public ResponseEntity<FeedBackPageResponseDTO> getAllFeedbacks(
            @RequestParam(defaultValue = AppConstants.PAGE,required = false) int page,
            @RequestParam(defaultValue = AppConstants.SIZE,required = false) int size,
            @RequestParam(defaultValue = AppConstants.SORT_BY_EMAIL,required = false) String sortBy,
            @RequestParam(defaultValue = AppConstants.ORDER,required = false) String order
    ){
        FeedBackPageResponseDTO response = feedBackService.getAllFeedbacks(page, size, sortBy, order);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("feedbacks/{id}")
    public void deleteBlogById(@PathVariable UUID id){
        feedBackService.deleteFeedBackById(id);
    }
}
