package kr.go.mapo.mpyouth.repository;

import kr.go.mapo.mpyouth.domain.ProgramFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ProgramFileRepository extends JpaRepository<ProgramFile, Long> {

    @Query(value = "SELECT pf" +
            " FROM ProgramFile pf" +
            " join fetch pf.program p" +
            " WHERE pf.program.id = :id")
    List<ProgramFile> findByProgram(@Param("id") Long id);

    @Transactional
    void deleteAllByProgramId(Long id);
}
