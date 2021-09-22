package kr.go.mapo.mpyouth.service;

import kr.go.mapo.mpyouth.api.ProgramDto;
import kr.go.mapo.mpyouth.domain.Program;
import kr.go.mapo.mpyouth.global.ProgramMapper;
import kr.go.mapo.mpyouth.repository.ProgramRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProgramService {
    private final ProgramRepository programRepository;
    private final ProgramMapper programMapper;

    @Transactional
    public ProgramDto saveProgram(ProgramDto programDto) {

        Program newProgram = programMapper.saveDtoToProgram(programDto);
        programRepository.save(newProgram);

        return programMapper.getProgramToDto(newProgram);

    }

    @Transactional
    public ProgramDto updateProgram(ProgramDto programDto) {
        Long updateId = programDto.getId();
        Program findProgram = programRepository.findById(updateId).orElse(null);

        programMapper.updateDtoToProgram(programDto,findProgram);

        return programMapper.getProgramToDto(findProgram);
    }


    public List<ProgramDto> findPrograms() {
        List<Program> programs = programRepository.findAll();

        return programMapper.getProgramsToDtos(programs);
    }

    public ProgramDto findOne(Long programId) {
        Program program = programRepository.findById(programId).orElse(null);

        return programMapper.getProgramToDto(program);
    }

    @Transactional
    public void deleteProgram(Long programId) {
        programRepository.deleteById(programId);
    }

}
