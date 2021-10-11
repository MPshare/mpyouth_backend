package kr.go.mapo.mpyouth.repository;

import kr.go.mapo.mpyouth.domain.Donation;
import kr.go.mapo.mpyouth.domain.LifeLongEdu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DonationRepository extends JpaRepository<Donation, Long> {
    @EntityGraph(attributePaths = {"category", "organization"})
    @Query(value = "select d" +
            " from Donation d" +
            " where d.title like %:keyword%" +
            " or d.description like %:keyword%", countProjection = "d")
    Page<Donation> findByLifeDonation(@Param("keyword") String keyword, Pageable pageable);
}
