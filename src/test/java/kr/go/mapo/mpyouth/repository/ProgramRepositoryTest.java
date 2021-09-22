package kr.go.mapo.mpyouth.repository;

import kr.go.mapo.mpyouth.domain.Program;
import kr.go.mapo.mpyouth.domain.RecruitStatus;
import kr.go.mapo.mpyouth.domain.VolunteerType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@SpringBootTest
@Transactional
class ProgramRepositoryTest {
    @Autowired
    EntityManager em;

    @Autowired
    ProgramRepository programRepository;


    @BeforeEach
    void before() {

        for (int i = 0; i < 5; i++) {

            Program program = Program.builder()
                    .title("제목" + i)
                    .startDate(LocalDateTime.now())
                    .endDate(LocalDateTime.now())
                    .description("설명")
                    .recruitNumber(10)
                    .location("지역")
                    .managerName("김매니저")
                    .managerContact("010-1234-5678")
                    .url("http://12d2d.cq3er")
                    .recruitStatus(RecruitStatus.RECRUITING)
                    .entryFee(0)
                    .targetAge("대상연령")
                    .caution("주의사항")
                    .period("기간")
                    .volunteerType(VolunteerType.INDIVIDUAL)
                    .build();
            programRepository.save(program);
        }
    }

    @Test
    void findAll() {

        List<Program> all = programRepository.findAll();

        for (Program program : all) {
            System.out.println("program = " + program);
        }

    }

    @Test
    void findById() {

        String inputString = "Bang";
        Program program = Program.builder()
                .title("제목")
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now())
                .description("설명")
                .recruitNumber(10)
                .location("지역")
                .managerName(inputString)
                .managerContact("010-1234-5678")
                .url("http://12d2d.cq3er")
                .recruitStatus(RecruitStatus.RECRUITING)
                .entryFee(0)
                .targetAge("대상연령")
                .caution("주의사항")
                .period("기간")
                .volunteerType(VolunteerType.INDIVIDUAL)
                .build();
        programRepository.save(program);

        String managerName = Objects.requireNonNull(programRepository.findById(program.getId()).orElse(null)).getManagerName();

        Assertions.assertThat(managerName).isEqualTo(inputString);
    }

    @Test
    void update() {
        Program program = Program.builder()
                .title("제목")
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now())
                .description("설명")
                .recruitNumber(10)
                .location("지역")
                .managerName("test")
                .managerContact("010-1234-5678")
                .url("http://12d2d.cq3er")
                .recruitStatus(RecruitStatus.RECRUITING)
                .entryFee(0)
                .targetAge("대상연령")
                .caution("주의사항")
                .period("기간")
                .volunteerType(VolunteerType.INDIVIDUAL)
                .build();
        programRepository.save(program);


        Program updateProgram = programRepository.findById(program.getId()).orElse(null);

//        Objects.requireNonNull(updateProgram).changeMangerName("변경확인용");

        programRepository.findById(updateProgram.getId())
                .ifPresent(findProgram -> Assertions.assertThat(findProgram.getManagerName()).isEqualTo("변경확인용"));

    }

    @Test
    void delete() {
        Program program = programRepository.findById(1L).orElse(null);

        programRepository.delete(Objects.requireNonNull(program));

        List<Program> all = programRepository.findAll();

        Assertions.assertThat(all.size()).isEqualTo(4);

    }
}