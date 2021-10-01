package kr.go.mapo.mpyouth.repository;

import kr.go.mapo.mpyouth.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
