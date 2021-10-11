package kr.go.mapo.mpyouth.api;

import kr.go.mapo.mpyouth.payload.request.LifeLongEduRequest;
import kr.go.mapo.mpyouth.payload.response.CustomApiResponse;
import kr.go.mapo.mpyouth.payload.response.LifeLongEduResponse;
import kr.go.mapo.mpyouth.service.LifeLongEduService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
public class LifeLongEduController {
    private final LifeLongEduService lifeLongEduService;

    @GetMapping("/life-long-edu/{id}")
    public ResponseEntity<?> findLifeLongEdu(@PathVariable Long id){
        LifeLongEduResponse lifeLongEdu = lifeLongEduService.findEdu(id);

        CustomApiResponse<?> response = CustomApiResponse.builder()
                .success(ApiStatus.SUCCESS)
                .message("평생교육 단일 조회")
                .data(lifeLongEdu)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PostMapping("/life-long-edu")
    public ResponseEntity<?> saveLifeLongEdu(@RequestBody LifeLongEduRequest lifeLongEduRequest){
        LifeLongEduResponse LifeLongEduResponse = lifeLongEduService.saveEdu(lifeLongEduRequest);
        CustomApiResponse<?> response = CustomApiResponse.builder()
                .success(ApiStatus.SUCCESS)
                .message("평생교육 저장")
                .data(LifeLongEduResponse)
                .build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PatchMapping("/life-long-edu/{id}")
    public ResponseEntity<?> updateLifeLongEdu(@PathVariable Long id, @RequestBody LifeLongEduRequest lifeLongEduRequest){
        LifeLongEduResponse LifeLongEduResponse = lifeLongEduService.updateEdu(id, lifeLongEduRequest);

        CustomApiResponse<?> response = CustomApiResponse.builder()
                .success(ApiStatus.SUCCESS)
                .message("평생교육 수정")
                .data(LifeLongEduResponse)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @DeleteMapping("/life-long-edu/{id}")
    public ResponseEntity<?> deleteLifeLongEdu(@PathVariable Long id){
        LifeLongEduResponse deleteLifeLongEdu = lifeLongEduService.deleteEdu(id);

        CustomApiResponse<?> response = CustomApiResponse.builder()
                .success(ApiStatus.SUCCESS)
                .message("평생교육 삭제")
                .data(deleteLifeLongEdu)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/life-long-edu")
    public ResponseEntity<?> getLifeLongEduList(Pageable pageable){
        Page<LifeLongEduResponse> lifeLongEduList = lifeLongEduService.findEduList(pageable);

        CustomApiResponse<?> response = CustomApiResponse.builder()
                .success(ApiStatus.SUCCESS)
                .message("평생교육 전체 조회")
                .data(lifeLongEduList)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/life-long-edu/search")
    public ResponseEntity<?> searchLifeLongEdu(@RequestParam("keyword") String keyword, Pageable pageable){
        Page<LifeLongEduResponse> LifeLongEduResponses = lifeLongEduService.searchEdu(keyword, pageable);
        CustomApiResponse<?> response = CustomApiResponse.builder()
                .success(ApiStatus.SUCCESS)
                .message("평생교육 단일 조회")
                .data(LifeLongEduResponses)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
