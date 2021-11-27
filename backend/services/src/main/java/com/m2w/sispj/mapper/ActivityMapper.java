package com.m2w.sispj.mapper;

import com.m2w.sispj.domain.Activity;
import com.m2w.sispj.dto.ActivityDTO;
import com.m2w.sispj.util.SISPJUtil;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE )
public interface ActivityMapper {

    static ActivityMapper getInstance() {
        return ActivityMapperInstance.INSTANCE;
    }

    final class ActivityMapperInstance {
        private static final ActivityMapper INSTANCE = Mappers.getMapper(ActivityMapper.class);
        private ActivityMapperInstance() { }
    }

    @Mapping(source = "type.description", target = "typeDescription")
    ActivityDTO entityToDTO(Activity activity);
}