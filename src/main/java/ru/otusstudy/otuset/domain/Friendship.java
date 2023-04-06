package ru.otusstudy.otuset.domain;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Friendship {
    private Long id;
    private Long userId;
    private List<Long> friends;
}
