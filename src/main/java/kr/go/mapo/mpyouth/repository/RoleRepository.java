package kr.go.mapo.mpyouth.repository;

import kr.go.mapo.mpyouth.domain.ERole;
import kr.go.mapo.mpyouth.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
