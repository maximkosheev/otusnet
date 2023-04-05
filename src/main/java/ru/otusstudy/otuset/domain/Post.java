package ru.otusstudy.otuset.domain;

import lombok.Builder;
import lombok.Data;

import java.sql.Date;

@Data
@Builder
public class Post {
    private Long id;
    private Long authorId;
    private Date createDt;
    private String data;
}
