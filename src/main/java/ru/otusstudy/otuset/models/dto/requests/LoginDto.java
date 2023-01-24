package ru.otusstudy.otuset.models.dto.requests;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@Builder
public class LoginDto {
    @NotBlank
    private String login;
    @NotBlank
    private String password;
}
