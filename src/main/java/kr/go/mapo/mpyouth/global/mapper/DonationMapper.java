package kr.go.mapo.mpyouth.global.mapper;

import kr.go.mapo.mpyouth.domain.Donation;
import kr.go.mapo.mpyouth.payload.request.DonationRequest;
import kr.go.mapo.mpyouth.payload.request.DonationUpdateRequest;
import kr.go.mapo.mpyouth.payload.response.DonationResponse;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", uses = {DonationMapper.class, CategoryMapper.class, OrganizationMapper.class},
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface DonationMapper {

    @Mapping(source = "organizationId", target = "organization.id")
    @Mapping(source = "categoryId", target = "category.id")
    Donation saveDtoToDonation(DonationRequest donationRequest);

    DonationResponse getDtoToDonation(Donation donation);

    List<DonationResponse> getDtosToDonations(List<Donation> donation);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateDtoToDonation(DonationUpdateRequest donationUpdateRequest, @MappingTarget Donation donation);
}
