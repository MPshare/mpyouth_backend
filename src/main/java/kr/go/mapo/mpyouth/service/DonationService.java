package kr.go.mapo.mpyouth.service;

import kr.go.mapo.mpyouth.domain.Donation;
import kr.go.mapo.mpyouth.global.mapper.DonationMapper;
import kr.go.mapo.mpyouth.payload.request.DonationRequest;
import kr.go.mapo.mpyouth.payload.request.DonationUpdateRequest;
import kr.go.mapo.mpyouth.payload.response.DonationResponse;
import kr.go.mapo.mpyouth.repository.DonationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DonationService {
    private final DonationRepository donationRepository;
    private final DonationMapper donationMapper;
    private final EntityManager entityManager;

    public DonationResponse getDonation(Long id){
        Donation donation = findEntity(id);

        return donationMapper.getDtoToDonation(donation);
    }

    public Page<DonationResponse> getDonations(Pageable pageable){
        Page<Donation> all = donationRepository.findAll(pageable);

        return all.map(donationMapper::getDtoToDonation);
    }

    @Transactional
    public DonationResponse saveDonation(DonationRequest donationRequest){

        Donation donation = donationMapper.saveDtoToDonation(donationRequest);

        donationRepository.save(donation);

        entityManager.flush();
        entityManager.clear();

        Donation saveDonation = donationRepository.findById(donation.getId()).orElseThrow(RuntimeException::new);

        return donationMapper.getDtoToDonation(saveDonation);
    }

    @Transactional
    public DonationResponse updateDonation(Long id, DonationUpdateRequest donationUpdateRequest){
        Donation updateDonation = findEntity(id);

        donationMapper.updateDtoToDonation(donationUpdateRequest, updateDonation);

        return donationMapper.getDtoToDonation(updateDonation);
    }

    @Transactional
    public DonationResponse deleteDonation(Long id){
        Donation donation = findEntity(id);

        donationRepository.deleteById(id);

        return donationMapper.getDtoToDonation(donation);
    }

    public Page<DonationResponse> searchDonation(String keyword, Pageable pageable){
        Page<Donation> byLifeDonation = donationRepository.findByLifeDonation(keyword, pageable);

        return byLifeDonation.map(donationMapper::getDtoToDonation);
    }

    private Donation findEntity(Long id) {
        return donationRepository.findById(id)
                .orElseThrow(() -> new NoResultException("조건에 맞는 재능기부가 없습니다."));
    }
}
