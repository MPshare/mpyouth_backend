package kr.go.mapo.mpyouth.global.mapper;

import kr.go.mapo.mpyouth.domain.Category;
import kr.go.mapo.mpyouth.payload.response.CategoryResponse;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = CategoryMapper.class,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface CategoryMapper {
    @Mapping(source = "id", target = "categoryId")
    @Mapping(source = "parent.name", target = "parentName")
    CategoryResponse getCategoryToDto(Category category);

    List<CategoryResponse> getCategoriesToDto(List<Category> categories);
}
