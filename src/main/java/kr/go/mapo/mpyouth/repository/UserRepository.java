package kr.go.mapo.mpyouth.repository;

import kr.go.mapo.mpyouth.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByAdminLoginId(String adminLoginId);
    Optional<User> findByEmail(String email);
    Optional<User> findByAdminLoginIdAndEmail(String adminLoginId, String email);

    Boolean existsByAdminLoginId(String adminLoginId);

    Boolean existsByEmail(String email);
}
