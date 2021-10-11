package kr.go.mapo.mpyouth.payload.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import java.util.List;

@Getter
@Setter
@ApiModel(description = "페이징 request")
public class RequestPage {
//    @Schema(description = "페이지 번호")
    @ApiModelProperty(value = "페이지 번호, 0부터 페이지번호 시작(0, 1, ...)", notes = "추가설명")
    @Min(0)
    private Integer page;

//    @Schema(description = "페이지 크기", allowableValues = "range[0, 100]")
    @ApiModelProperty(value = "페이지 크기, 기본값 10개", allowableValues = "range[0, 100]")
    @Min(0)
    private Integer size;

//    @Schema(description = "정렬(컬럼명,ASC|DESC)")
    @ApiModelProperty(value = "정렬 example : 컬럼명 or 컬럼명,ASC|DESC")
    private List<String> sort;
}
