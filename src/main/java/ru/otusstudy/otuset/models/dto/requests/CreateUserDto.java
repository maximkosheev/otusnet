package ru.otusstudy.otuset.models.dto.requests;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class CreateUserDto {
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotNull
    private Integer age;
    @NotBlank
    private String sex;
    private List<String> interests;
    @NotBlank
    private String city;
}
