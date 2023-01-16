package ru.otusstudy.otuset.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OtusetUser {
    private Long id;
    private String firstName;
    private String lastName;
    private String sex;
    private Integer age;
    private String interests;
    private String city;
}
