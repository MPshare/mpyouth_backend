package kr.go.mapo.mpyouth.repository;

import kr.go.mapo.mpyouth.domain.LifeLongEdu;
import kr.go.mapo.mpyouth.domain.Volunteer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LifeLongEduRepository extends JpaRepository<LifeLongEdu, Long> {

    @EntityGraph(attributePaths = {"category", "organization"})
    @Query(value = "select e" +
            " from LifeLongEdu e" +
            " where e.title like %:keyword%" +
            " or e.description like %:keyword%", countProjection = "e")
    Page<LifeLongEdu> findByLifeLongEduKeyword(@Param("keyword") String keyword, Pageable pageable);
}
