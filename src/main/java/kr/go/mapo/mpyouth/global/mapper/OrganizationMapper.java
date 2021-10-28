package kr.go.mapo.mpyouth.global.mapper;

import kr.go.mapo.mpyouth.domain.Organization;
import kr.go.mapo.mpyouth.payload.request.OrganizationRequest;
import kr.go.mapo.mpyouth.payload.response.OrganizationResponse;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", uses = OrganizationMapper.class,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface OrganizationMapper {

    @Mapping(source = "organizationId", target = "id")
    Organization saveDtoToOrganization(OrganizationRequest organizationRequest);

    @Mapping(source = "id", target = "organizationId")
    OrganizationResponse getOrganizationToResponse(Organization organization);

    List<OrganizationResponse> getOrganizationsToResponses(List<Organization> organizations);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateDtoToOrganization(OrganizationRequest organizationRequest, @MappingTarget Organization organization);
}
