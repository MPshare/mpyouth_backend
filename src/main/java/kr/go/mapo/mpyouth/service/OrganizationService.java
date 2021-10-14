package kr.go.mapo.mpyouth.service;

import kr.go.mapo.mpyouth.exception.NotFoundOrganizationException;
import kr.go.mapo.mpyouth.payload.request.OrganizationRequest;
import kr.go.mapo.mpyouth.payload.request.OrganizationUpdateRequest;
import kr.go.mapo.mpyouth.payload.response.OrganizationResponse;
import kr.go.mapo.mpyouth.domain.Organization;
import kr.go.mapo.mpyouth.global.mapper.OrganizationMapper;
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
        Organization organization = getOrganization(id);

        return organizationMapper.getOrganizationToResponse(organization);
    }


    @Transactional
    public OrganizationResponse saveOrganization(OrganizationRequest request) {
        Organization organization = organizationMapper.saveDtoToOrganization(request);

        organizationRepository.save(organization);

        return organizationMapper.getOrganizationToResponse(organization);
    }

    @Transactional
    public OrganizationResponse updateOrganization(Long id, OrganizationUpdateRequest organizationUpdateRequest) {
        Organization findOrganization = getOrganization(id);

        organizationMapper.updateDtoToOrganization(organizationUpdateRequest, findOrganization);

        return organizationMapper.getOrganizationToResponse(findOrganization);
    }

    @Transactional
    public OrganizationResponse deleteOrganization(Long id) {
        Organization organization = getOrganization(id);
        organizationRepository.deleteById(id);

        return organizationMapper.getOrganizationToResponse(organization);
    }

    private Organization getOrganization(Long id) {
        return organizationRepository.findById(id).orElseThrow(() -> new NotFoundOrganizationException("기관정보가 존재하지 않습니다!"));
    }
}
