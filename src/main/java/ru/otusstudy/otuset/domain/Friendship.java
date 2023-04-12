package ru.otusstudy.otuset.domain;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class Friendship {
    private Long id;
    private Long userId;
    private Set<Long> friends;
}
