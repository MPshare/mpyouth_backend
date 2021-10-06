package kr.go.mapo.mpyouth.payload.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import kr.go.mapo.mpyouth.domain.Category;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CategoryResponse {
    private Long categoryId;
    private String name;
    private Integer level;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String parentName;

}
