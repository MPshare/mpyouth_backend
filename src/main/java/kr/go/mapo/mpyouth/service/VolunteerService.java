package kr.go.mapo.mpyouth.service;

import kr.go.mapo.mpyouth.domain.Volunteer;
import kr.go.mapo.mpyouth.exception.NotFoundVolunteerException;
import kr.go.mapo.mpyouth.global.mapper.VolunteerMapper;
import kr.go.mapo.mpyouth.payload.request.VolunteerRequest;
import kr.go.mapo.mpyouth.payload.response.VolunteerResponse;
import kr.go.mapo.mpyouth.repository.VolunteerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VolunteerService {
    private final VolunteerRepository volunteerRepository;
    private final VolunteerMapper volunteerMapper;
    private final EntityManager entityManager;

    @Transactional
    public VolunteerResponse saveVolunteer(VolunteerRequest volunteerRequest) {
        Volunteer saveDtoToVolunteer = volunteerMapper.saveDtoToVolunteer(volunteerRequest);
        Long saveId = volunteerRepository.save(saveDtoToVolunteer).getId();

        entityManager.flush();
        entityManager.clear();

        Volunteer saveVolunteer = volunteerRepository.findById(saveId).orElseThrow(() -> new NotFoundVolunteerException("조건에 맞는 자원봉사가 없습니다"));

        return volunteerMapper.getVolunteerToDto(saveVolunteer);
    }

    public VolunteerResponse findVolunteer(Long id) {
        Volunteer findVolunteer = volunteerRepository.findById(id).orElseThrow(() -> new NotFoundVolunteerException("조건에 맞는 자원봉사가 없습니다"));

        return volunteerMapper.getVolunteerToDto(findVolunteer);
    }

    public Page<VolunteerResponse> findVolunteerList(Pageable pageable) {
        Page<Volunteer> all = volunteerRepository.findAll(pageable);

        return all.map(volunteerMapper::getVolunteerToDto);
    }

    @Transactional
    public VolunteerResponse updateVolunteer(Long id, VolunteerRequest volunteerRequest) {
        Volunteer updateVolunteer = volunteerRepository.findById(id).orElseThrow(() -> new NotFoundVolunteerException("조건에 맞는 자원봉사가 없습니다"));

        volunteerMapper.updateDtoToProgram(volunteerRequest, updateVolunteer);

        return volunteerMapper.getVolunteerToDto(updateVolunteer);
    }

    @Transactional
    public VolunteerResponse deleteVolunteer(Long id) {
        Volunteer deleteVolunteer = volunteerRepository.findById(id).orElseThrow(() -> new NotFoundVolunteerException("조건에 맞는 자원봉사가 없습니다"));

        volunteerRepository.deleteById(id);

        return volunteerMapper.getVolunteerToDto(deleteVolunteer);
    }

    public Page<VolunteerResponse> searchVolunteer(String keyword, Pageable pageable){
        Page<Volunteer> byVolunteerKeyword = volunteerRepository.findByVolunteerKeyword(keyword, pageable);

        return byVolunteerKeyword.map(volunteerMapper::getVolunteerToDto);
    }
}
