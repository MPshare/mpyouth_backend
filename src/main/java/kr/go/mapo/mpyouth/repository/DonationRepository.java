package kr.go.mapo.mpyouth.repository;

import kr.go.mapo.mpyouth.domain.Donation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DonationRepository extends JpaRepository<Donation, Long> {
}
