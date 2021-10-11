package kr.go.mapo.mpyouth.payload.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import kr.go.mapo.mpyouth.domain.Category;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ApiModel(description = "카테고리 Response")
public class CategoryResponse {
    @Schema(description = "카테고리 ID")
    private Long categoryId;
    @Schema(description = "카테고리 이름")
    private String name;
    @Schema(description = "카테고리 레벨")
    private Integer level;
    @Schema(description = "상위 카테고리 이름")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String parentName;

}
