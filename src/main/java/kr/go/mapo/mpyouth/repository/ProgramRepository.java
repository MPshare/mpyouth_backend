package kr.go.mapo.mpyouth.repository;

import kr.go.mapo.mpyouth.domain.Program;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProgramRepository extends JpaRepository<Program, Long> {
    @EntityGraph(attributePaths = {"programFiles", "programThumbnail", "organization"})
    @Query(value = "select p" +
            " from Program p" +
            " where p.title like %:keyword%" +
            " or p.description like %:keyword%", countProjection = "p")
    Page<Program> findProgramByKeyword(@Param("keyword") String keyword, Pageable pageable);

    @EntityGraph(attributePaths = {"programFiles"})
    @Query(value = "select p" +
            " from Program p", countProjection = "p")
    Page<Program> findPrograms(Pageable pageable);


}
