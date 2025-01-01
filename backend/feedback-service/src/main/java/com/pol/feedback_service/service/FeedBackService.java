package com.pol.feedback_service.service;


import com.pol.feedback_service.DTO.FeedBackPageResponseDTO;
import com.pol.feedback_service.DTO.FeedBackRequestDTO;
import com.pol.feedback_service.DTO.FeedBackResponseDTO;
import com.pol.feedback_service.entity.Feedback;
import com.pol.feedback_service.exception.customException.EntityNotFound;
import com.pol.feedback_service.mapper.FeedBackMapper;
import com.pol.feedback_service.repository.FeedBackRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class FeedBackService {
    private final FeedBackRepository feedBackRepository;


    public FeedBackService(FeedBackRepository feedBackRepository) {
        this.feedBackRepository = feedBackRepository;
    }

    public FeedBackResponseDTO createFeedback(FeedBackRequestDTO feedBackRequestDTO) {
        Feedback feedback = new Feedback();
        feedback.setName(feedBackRequestDTO.getName());
        feedback.setEmail(feedBackRequestDTO.getEmail());
        feedback.setFeedback(feedBackRequestDTO.getFeedback());

        return FeedBackMapper.toResponse(feedBackRepository.save(feedback));
    }

    public void deleteFeedBackById(UUID id){
        Feedback feedback = feedBackRepository.findById(id).orElseThrow(()->new EntityNotFound("FeedBack not found with id : "+id));
        feedBackRepository.deleteById(id);
    }

    public FeedBackPageResponseDTO getAllFeedbacks(int page, int size, String sortBy, String order){
        String[] sortFields = sortBy.split(",");
        Sort sort = Sort.by(order.equalsIgnoreCase("asc")?Sort.Order.asc(sortFields[0]):Sort.Order.desc(sortFields[0]));
        for(int i=1;i<sortFields.length;i++){
            sort= Sort.by(order.equalsIgnoreCase("asc")?Sort.Order.asc(sortFields[i]):Sort.Order.desc(sortFields[i]));
        }
        Pageable pageable = PageRequest.of(page,size,sort);
        Page<FeedBackResponseDTO> feedBackResponseDTOPage = feedBackRepository.findAllFeedBacks(pageable);
        return FeedBackPageResponseDTO.builder()
                .feedbacks(feedBackResponseDTOPage.getContent())
                .currentPage(feedBackResponseDTOPage.getNumber())
                .totalPages(feedBackResponseDTOPage.getTotalPages())
                .totalElements(feedBackResponseDTOPage.getTotalElements())
                .pageSize(feedBackResponseDTOPage.getSize())
                .hasNext(feedBackResponseDTOPage.hasNext())
                .hasPrevious(feedBackResponseDTOPage.hasPrevious())
                .build();
    }
}
