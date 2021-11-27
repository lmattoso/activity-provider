package com.m2w.sispj.mapper;

import com.m2w.sispj.domain.User;
import com.m2w.sispj.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE )
public interface UserMapper {

    static UserMapper getInstance() {
        return UserMapperInstance.INSTANCE;
    }

    final class UserMapperInstance {
        private static final UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
        private UserMapperInstance() { }
    }

    UserDTO entityToDTO(User user);

    User dtoToEntity(UserDTO userDTO);
}
