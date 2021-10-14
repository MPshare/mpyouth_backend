package kr.go.mapo.mpyouth.service;

import kr.go.mapo.mpyouth.domain.LifeLongEdu;
import kr.go.mapo.mpyouth.domain.Volunteer;
import kr.go.mapo.mpyouth.exception.NotFoundLifeLongEduException;
import kr.go.mapo.mpyouth.exception.NotFoundVolunteerException;
import kr.go.mapo.mpyouth.global.mapper.LifeLongEduMapper;
import kr.go.mapo.mpyouth.payload.request.LifeLongEduRequest;
import kr.go.mapo.mpyouth.payload.request.LifeLongEduUpdateRequest;
import kr.go.mapo.mpyouth.payload.request.VolunteerRequest;
import kr.go.mapo.mpyouth.payload.response.LifeLongEduResponse;
import kr.go.mapo.mpyouth.payload.response.VolunteerResponse;
import kr.go.mapo.mpyouth.repository.LifeLongEduRepository;
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
public class LifeLongEduService {
    private final LifeLongEduRepository lifeLongEduRepository;
    private final LifeLongEduMapper lifeLongEduMapper;
    private final EntityManager entityManager;

    @Transactional
    public LifeLongEduResponse saveEdu(LifeLongEduRequest lifeLongEduRequest) {
        LifeLongEdu lifeLongEdu = lifeLongEduMapper.saveDtoToLifeLongEdu(lifeLongEduRequest);
        Long saveId = lifeLongEduRepository.save(lifeLongEdu).getId();

        entityManager.flush();
        entityManager.clear();

        LifeLongEdu saveLifeLongEdu = lifeLongEduRepository.findById(saveId).orElseThrow(() -> new NotFoundLifeLongEduException("조건에 맞는 평생교육이 없습니다"));

        return lifeLongEduMapper.getLifeLongEduToDto(saveLifeLongEdu);
    }

    public LifeLongEduResponse findEdu(Long id) {
        LifeLongEdu findLifeLongEdu = lifeLongEduRepository.findById(id).orElseThrow(() -> new NotFoundLifeLongEduException("조건에 맞는 평생교육이 없습니다"));

        return lifeLongEduMapper.getLifeLongEduToDto(findLifeLongEdu);
    }

    public Page<LifeLongEduResponse> findEduList(Pageable pageable) {
        Page<LifeLongEdu> all = lifeLongEduRepository.findAll(pageable);

        return all.map(lifeLongEduMapper::getLifeLongEduToDto);
    }

    @Transactional
    public LifeLongEduResponse updateEdu(Long id, LifeLongEduUpdateRequest lifeLongEduUpdateRequest) {
        LifeLongEdu updateLifeLongEdu = lifeLongEduRepository.findById(id).orElseThrow(() -> new NotFoundLifeLongEduException("조건에 맞는 평생교육이 없습니다"));

        lifeLongEduMapper.updateDtoToLifeLongEdu(lifeLongEduUpdateRequest, updateLifeLongEdu);

        return lifeLongEduMapper.getLifeLongEduToDto(updateLifeLongEdu);
    }

    @Transactional
    public LifeLongEduResponse deleteEdu(Long id) {
        LifeLongEdu deleteVolunteer = lifeLongEduRepository.findById(id).orElseThrow(() -> new NotFoundLifeLongEduException("조건에 맞는 평생교육이 없습니다"));

        lifeLongEduRepository.deleteById(id);

        return lifeLongEduMapper.getLifeLongEduToDto(deleteVolunteer);
    }

    public Page<LifeLongEduResponse> searchEdu(String keyword, Pageable pageable) {
        Page<LifeLongEdu> byLifeLongEduKeyword = lifeLongEduRepository.findByLifeLongEduKeyword(keyword, pageable);

        return byLifeLongEduKeyword.map(lifeLongEduMapper::getLifeLongEduToDto);
    }
}
