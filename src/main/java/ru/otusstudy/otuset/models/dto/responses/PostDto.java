package ru.otusstudy.otuset.models.dto.responses;

import lombok.Builder;
import lombok.Data;

import java.sql.Date;

@Data
@Builder
public class PostDto {
    private Long id;
    private Long authorId;
    private Date createDt;
    private String data;
}
