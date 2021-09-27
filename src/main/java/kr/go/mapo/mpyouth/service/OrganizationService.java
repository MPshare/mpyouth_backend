package kr.go.mapo.mpyouth.service;

import kr.go.mapo.mpyouth.api.OrganizationRequest;
import kr.go.mapo.mpyouth.api.OrganizationResponse;
import kr.go.mapo.mpyouth.domain.Organization;
import kr.go.mapo.mpyouth.global.OrganizationMapper;
import kr.go.mapo.mpyouth.repository.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrganizationService {
    private final OrganizationRepository organizationRepository;
    private final OrganizationMapper organizationMapper;

    public List<OrganizationResponse> findOrganizations() {
        List<Organization> organizations = organizationRepository.findAll();


        return organizationMapper.getOrganizationsToResponses(organizations);
    }

    public OrganizationResponse findOne(Long id) {
        Organization organization = organizationRepository.findById(id).orElse(null);

        return organizationMapper.getOrganizationToResponse(organization);
    }


    @Transactional
    public OrganizationResponse saveOrganization(OrganizationRequest request) {
        Organization organization = organizationMapper.saveDtoToOrganization(request);

        organizationRepository.save(organization);

        return organizationMapper.getOrganizationToResponse(organization);
    }

    @Transactional
    public OrganizationResponse updateOrganization(Long id, OrganizationRequest organizationRequest) {
        Organization findOrganization = organizationRepository.findById(id).orElse(null);

        organizationMapper.updateDtoToOrganization(organizationRequest,findOrganization);

        return organizationMapper.getOrganizationToResponse(findOrganization);
    }

    @Transactional
    public OrganizationResponse deleteOrganization(Long id) {
        Organization organization = organizationRepository.findById(id).orElse(null);
        organizationRepository.deleteById(id);

        return organizationMapper.getOrganizationToResponse(organization);
    }
}
