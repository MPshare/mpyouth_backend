package kr.go.mapo.mpyouth.repository;

import kr.go.mapo.mpyouth.domain.Volunteer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface VolunteerRepository extends JpaRepository<Volunteer, Long> {

    @EntityGraph(attributePaths = {"category", "organization"})
    @Query(value = "select v" +
            " from Volunteer v" +
            " where v.title like %:keyword%" +
            " or v.description like %:keyword%", countProjection = "v")
    Page<Volunteer> findByVolunteerKeyword(@Param("keyword") String keyword, Pageable pageable);
}
