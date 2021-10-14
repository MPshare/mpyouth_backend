package kr.go.mapo.mpyouth.global.mapper;

import kr.go.mapo.mpyouth.domain.Volunteer;
import kr.go.mapo.mpyouth.payload.request.VolunteerRequest;
import kr.go.mapo.mpyouth.payload.request.VolunteerUpdateRequest;
import kr.go.mapo.mpyouth.payload.response.VolunteerResponse;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {VolunteerMapper.class, CategoryMapper.class, OrganizationMapper.class},
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface VolunteerMapper {
    @Mapping(source = "organizationId", target = "organization.id")
    @Mapping(source = "categoryId", target = "category.id")
    Volunteer saveDtoToVolunteer(VolunteerRequest volunteerRequest);

//    @Mapping(source = "organization.id", target = "organizationId")
//    @Mapping(source = "category.id", target = "categoryId")
    VolunteerResponse getVolunteerToDto(Volunteer volunteer);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateDtoToProgram(VolunteerUpdateRequest volunteerUpdateRequest, @MappingTarget Volunteer volunteer);
}
