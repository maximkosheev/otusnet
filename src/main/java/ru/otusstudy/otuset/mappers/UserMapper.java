package ru.otusstudy.otuset.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import ru.otusstudy.otuset.domain.OtusetUser;
import ru.otusstudy.otuset.models.dto.requests.CreateUserDto;
import ru.otusstudy.otuset.models.dto.responses.UserDto;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class UserMapper {

    @Mapping(target = "interests", source = "interests", qualifiedByName = "interestsString")
    public abstract OtusetUser toOtusetUser(CreateUserDto src);

    @Mapping(target = "interests", source = "interests", qualifiedByName = "interestsList")
    public abstract UserDto toUserDto(OtusetUser src);

    @Named("interestsString")
    protected String interestsString(List<String> src) {
        if (src == null) {
            return null;
        }
        return String.join(",", src);
    }

    @Named("interestsList")
    protected List<String> interestsList(String src) {
        if (src == null) {
            return null;
        }
        return Arrays.stream(src.split(","))
                .map(String::stripLeading)
                .collect(Collectors.toList());
    }
}
