package kr.go.mapo.mpyouth.api;

import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Api(tags = "카테고리")
public class CategoryController {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Operation(summary = "카테고리 전체 조회", description = "카테고리 목록을 조회합니다.",
    responses = {
            @ApiResponse(responseCode = "400", description = "BAD_REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT_FOUND")
    })
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
