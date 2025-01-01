package com.pol.blog_service.service;

import com.pol.blog_service.dto.tags.TagRequestDTO;
import com.pol.blog_service.dto.tags.TagResponseDTO;
import com.pol.blog_service.entity.Tags;
import com.pol.blog_service.repository.TagsRepository;
import com.pol.blog_service.service.tags.TagsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class TagsServiceTests {

    @Mock
    private TagsRepository tagsRepository;

    @InjectMocks
    private TagsServiceImpl tagsService;

    private TagRequestDTO tagRequestDTO;

    @BeforeEach
    public void setup(){
        tagRequestDTO = TagRequestDTO.builder()
                .tagName("docker")
                .build();
    }

    @DisplayName("")
    @Test
    public void given_when_then(){
        // given - precondition or setup


        // when - action or behaviour we are going to test


        // then - verify the output
    }




}
