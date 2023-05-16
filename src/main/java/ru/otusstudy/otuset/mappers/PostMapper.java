package ru.otusstudy.otuset.mappers;

import org.mapstruct.Mapper;
import ru.otusstudy.otuset.domain.Post;
import ru.otusstudy.otuset.models.dto.responses.PostDto;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PostMapper {
    PostDto toDto(Post post);
    List<PostDto> toDtos(List<Post> posts);
}
