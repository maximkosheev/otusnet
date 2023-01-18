package ru.otusstudy.otuset.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import ru.otusstudy.otuset.domain.OtusetUser;
import ru.otusstudy.otuset.models.dto.requests.CreateUserDto;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class UserMapper {

    @Mapping(target = "interests", source = "interests", qualifiedByName = "interestsString")
    public abstract OtusetUser toOtusetUser(CreateUserDto createUserDto);

    @Named("interestsString")
    protected String interestsString(List<String> src) {
        if (src == null) {
            return null;
        }
        return String.join(",", src);
    }
}
