package ru.otusstudy.otuset.models.dto.responses;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private Integer age;
    private String sex;
    private List<String> interests;
    private String city;
}
