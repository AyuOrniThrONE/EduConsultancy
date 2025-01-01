package com.pol.blog_service.dto.blog;


import com.pol.blog_service.entity.BlogStatus;
import com.pol.blog_service.entity.Tags;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
//import org.hibernate.mapping.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogAdminResponseDTO {
    private UUID id;
    private String title;
    private String heroImg;
    private String content;
    private BlogStatus status;
}
