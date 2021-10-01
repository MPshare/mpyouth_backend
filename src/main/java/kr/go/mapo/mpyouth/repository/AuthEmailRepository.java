package kr.go.mapo.mpyouth.repository;

import kr.go.mapo.mpyouth.domain.AuthEmail;
import kr.go.mapo.mpyouth.domain.Role;
import kr.go.mapo.mpyouth.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthEmailRepository  extends JpaRepository<AuthEmail, Long> {
    Optional<AuthEmail> findByAuthKey(String authKey);
    Optional<AuthEmail> findByUser(User user);
}
