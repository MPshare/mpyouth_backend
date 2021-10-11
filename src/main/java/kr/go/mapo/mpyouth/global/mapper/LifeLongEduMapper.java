package kr.go.mapo.mpyouth.global.mapper;

import kr.go.mapo.mpyouth.domain.LifeLongEdu;
import kr.go.mapo.mpyouth.domain.Volunteer;
import kr.go.mapo.mpyouth.payload.request.LifeLongEduRequest;
import kr.go.mapo.mpyouth.payload.response.LifeLongEduResponse;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {LifeLongEduMapper.class, CategoryMapper.class, OrganizationMapper.class},
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface LifeLongEduMapper {
    @Mapping(source = "organizationId", target = "organization.id")
    @Mapping(source = "categoryId", target = "category.id")
    LifeLongEdu saveDtoToLifeLongEdu(LifeLongEduRequest lifeLongEduRequest);

    LifeLongEduResponse getLifeLongEduToDto(LifeLongEdu lifeLongEdu);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateDtoToLifeLongEdu(LifeLongEduRequest lifeLongEduRequest, @MappingTarget LifeLongEdu lifeLongEdu);
}
