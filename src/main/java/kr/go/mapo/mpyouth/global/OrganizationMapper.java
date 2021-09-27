package kr.go.mapo.mpyouth.global;

import kr.go.mapo.mpyouth.api.OrganizationRequest;
import kr.go.mapo.mpyouth.api.OrganizationResponse;
import kr.go.mapo.mpyouth.api.ProgramDto;
import kr.go.mapo.mpyouth.domain.Organization;
import kr.go.mapo.mpyouth.domain.Program;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrganizationMapper {
    OrganizationMapper instance = Mappers.getMapper(OrganizationMapper.class);

    @Mapping(source = "organizationId", target = "id")
    Organization saveDtoToOrganization(OrganizationRequest organizationRequest);

    @Mapping(source = "id", target = "organizationId")
    OrganizationResponse getOrganizationToResponse(Organization organization);

    List<OrganizationResponse> getOrganizationsToResponses(List<Organization> organizations);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateDtoToOrganization(OrganizationRequest organizationRequest, @MappingTarget Organization organization);
}
