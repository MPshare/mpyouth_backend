package kr.go.mapo.mpyouth.api;

import kr.go.mapo.mpyouth.domain.Category;
import kr.go.mapo.mpyouth.global.mapper.CategoryMapper;
import kr.go.mapo.mpyouth.payload.response.CategoryResponse;
import kr.go.mapo.mpyouth.payload.response.CustomApiResponse;
import kr.go.mapo.mpyouth.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @GetMapping("/category")
    public ResponseEntity<CustomApiResponse<List<CategoryResponse>>> getCategories(){
        List<Category> all = categoryRepository.findAll();

        List<CategoryResponse> categoriesToDto = categoryMapper.getCategoriesToDto(all);

        CustomApiResponse<List<CategoryResponse>> response = CustomApiResponse.<List<CategoryResponse>>builder()
                .success(ApiStatus.SUCCESS)
                .message("카테고리 조회")
                .data(categoriesToDto)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
