package kr.go.mapo.mpyouth.repository;

import kr.go.mapo.mpyouth.domain.Category;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Commit
class DonationRepositoryTest {
    @Autowired
    CategoryRepository categoryRepository;

    @Test
    void saveCategory(){
        Category category = Category.builder()
                .level(1)
                .name("테스트 부모 카테고리")
                .build();

        Category childCategory = Category.builder()
                .name("테스트 자식 카테고리")
//                .parent(category)
                .build();

        category.addChildCategory(childCategory);

        categoryRepository.save(category);

//        categoryRepository.save(childCategory);

//        categoryRepository.save(category);


        Category findCategory = categoryRepository.findById(1L).orElse(null);

        Assertions.assertThat(findCategory).isEqualTo(category);
    }
}