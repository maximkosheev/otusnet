package ru.monsterdev.study.otusnet.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationInfo {
    private String username;

    public Map<String, Object> toMap() {
        return Map.of("username", username);
    }
}
