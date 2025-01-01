package com.pol.blog_service.service;

import com.pol.blog_service.repository.BlogRepository;
import com.pol.blog_service.service.blog.BlogService;
import com.pol.blog_service.service.blog.BlogServiceImpl;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class BlogServiceTests {

    @Mock
    private BlogRepository blogRepository;

    @InjectMocks
    private BlogServiceImpl blogService;


}
