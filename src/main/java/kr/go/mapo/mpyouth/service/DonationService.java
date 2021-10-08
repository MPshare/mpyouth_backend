package kr.go.mapo.mpyouth.service;

import kr.go.mapo.mpyouth.domain.Donation;
import kr.go.mapo.mpyouth.exception.NotFoundDonationException;
import kr.go.mapo.mpyouth.global.mapper.DonationMapper;
import kr.go.mapo.mpyouth.payload.request.DonationRequest;
import kr.go.mapo.mpyouth.payload.response.DonationResponse;
import kr.go.mapo.mpyouth.repository.DonationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DonationService {
    private final DonationRepository donationRepository;
    private final DonationMapper donationMapper;
    private final EntityManager entityManager;

    public DonationResponse getDonation(Long id){
        Donation donation = donationRepository.findById(id)
                .orElseThrow(()->new NotFoundDonationException("조건에 맞는 재능기부가 없습니다."));

        return donationMapper.getDtoToDonation(donation);
    }

    public List<DonationResponse> getDonations(){
        List<Donation> donations = donationRepository.findAll();

        return donationMapper.getDtosToDonations(donations);
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
    public DonationResponse updateDonation(Long id, DonationRequest donationRequest){
        Donation updateDonation = donationRepository.findById(id).orElseThrow(()->new NotFoundDonationException("조건에 맞는 재능기부가 없습니다."));

        donationMapper.updateDtoToDonation(donationRequest, updateDonation);

        return donationMapper.getDtoToDonation(updateDonation);
    }

    @Transactional
    public DonationResponse deleteDonation(Long id){
        Donation donation = donationRepository.findById(id).orElseThrow(()->new NotFoundDonationException("조건에 맞는 재능기부가 없습니다."));

        donationRepository.deleteById(id);

        return donationMapper.getDtoToDonation(donation);
    }
}