package com.m2w.sispj.mapper;

import com.m2w.sispj.domain.Profile;
import com.m2w.sispj.dto.ProfileDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE )
public interface ProfileMapper {

    static ProfileMapper getInstance() {
        return ProfileMapperInstance.INSTANCE;
    }

    final class ProfileMapperInstance {
        private static final ProfileMapper INSTANCE = Mappers.getMapper(ProfileMapper.class);
        private ProfileMapperInstance() { }
    }

    ProfileDTO entityToDTO(Profile profile);
    Profile dtoToEntity(ProfileDTO profileDTO);
    List<Profile> dtosToEntities(List<ProfileDTO> serviceTypeDTO);
}