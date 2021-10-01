package kr.go.mapo.mpyouth.service;


import kr.go.mapo.mpyouth.domain.Program;
import kr.go.mapo.mpyouth.domain.ProgramFile;
import kr.go.mapo.mpyouth.exception.NotFoundProgramException;
import kr.go.mapo.mpyouth.global.mapper.ProgramMapper;
import kr.go.mapo.mpyouth.payload.request.ProgramFileRequest;
import kr.go.mapo.mpyouth.payload.request.ProgramRequest;
import kr.go.mapo.mpyouth.payload.response.ProgramResponse;
import kr.go.mapo.mpyouth.repository.ProgramFileRepository;
import kr.go.mapo.mpyouth.repository.ProgramRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.persistence.EntityManager;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProgramService {
    private final ProgramRepository programRepository;
    private final ProgramFileRepository programFileRepository;
    private final ProgramMapper programMapper;
    private final EntityManager entityManager;

    @Value("${file.dir}")
    String dir;

    @Value("${file.uri_path}")
    String uriPath;

    @Transactional
    public ProgramResponse saveProgram(ProgramRequest programRequest, List<MultipartFile> imageFiles, String fileUri) throws IOException {
        String filePath = fileUri + uriPath;

        log.info("filePath : {}", filePath);

        Program newProgram = programMapper.saveDtoToProgram(programRequest);
        programRepository.save(newProgram);

        List<ProgramFile> programFiles = saveImageFiles(newProgram, imageFiles);

        programFileRepository.saveAll(programFiles);

        entityManager.flush();
        entityManager.clear();

        Program findProgram = programRepository.findById(newProgram.getId()).orElseThrow(NotFoundProgramException::new);

        return programMapper.getProgramToDto(findProgram);

    }

    private List<ProgramFile> saveImageFiles(Program program, List<MultipartFile> imageFiles) throws IOException {
        List<ProgramFile> result = new ArrayList<>();

        if (imageFiles != null && !imageFiles.isEmpty()) {
            MultipartFile firstImage = imageFiles.get(0);

            makeThumbnail(firstImage);

            for (MultipartFile imageFile : imageFiles) {
                if (!imageFile.isEmpty()) {

                    BufferedImage image = ImageIO.read(imageFile.getInputStream());

                    String newName = UUID.randomUUID()
                            + "."
                            + FilenameUtils.getExtension(imageFile.getOriginalFilename());

                    ProgramFileRequest programFile = ProgramFileRequest.builder()
                            .originalFileName(imageFile.getOriginalFilename())
                            .fileName(newName)
                            .filePath(dir + newName)
                            .fileSize(imageFile.getSize() / 1024)
                            .programId(program.getId())
                            .build();

                    imageFile.transferTo(new File(dir + newName));

                    ProgramFile newProgramFile = programMapper.saveDtoToProgram(programFile);

//                    Program program = programRepository.findById(programId).orElseThrow(NotFoundProgramException::new);
                    newProgramFile.setProgram(program);

//                    programFileRepository.save(newProgramFile);
                    result.add(newProgramFile);
                }
            }
        }
        return result;
    }


    private void makeThumbnail(MultipartFile firstImage) throws IOException {
        if (!firstImage.isEmpty()) {
            BufferedImage read = ImageIO.read(firstImage.getInputStream());
            BufferedImage resize = Scalr.resize(read, Scalr.Method.AUTOMATIC, Scalr.Mode.FIT_EXACT, 200);

            String thumbnailName = "thumb_" + UUID.randomUUID()
                    + "."
                    + FilenameUtils.getExtension(firstImage.getOriginalFilename());


            ImageIO.write(resize,
                    Objects.requireNonNull(FilenameUtils.getExtension(firstImage.getOriginalFilename())),
                    new File(dir + thumbnailName)
            );
        }
    }

    @Transactional
    public ProgramResponse updateProgram(ProgramRequest programRequest, List<MultipartFile> imageFiles, String fileUri) throws IOException {
        Long updateId = programRequest.getProgramId();
        String filePath = fileUri + uriPath;
        Program updateProgram = programRepository.findById(updateId).orElseThrow(NotFoundProgramException::new);

        if (imageFiles != null && !imageFiles.isEmpty()) {
            updateProgram.getProgramFiles().clear();

            List<ProgramFile> programFiles = saveImageFiles(updateProgram, imageFiles);

            programFileRepository.saveAll(programFiles);

            entityManager.flush();
            entityManager.clear();

            updateProgram = programRepository.findById(updateId).orElseThrow(NotFoundProgramException::new);
        }
        //
        //

        programMapper.updateDtoToProgram(programRequest, updateProgram);

        return programMapper.getProgramToDto(updateProgram);
    }


    public List<ProgramResponse> findPrograms() {
        List<Program> programs = programRepository.findAll();

        return programMapper.getProgramsToDtos(programs);
    }

    public ProgramResponse findOne(Long programId) {
        Program findProgram = programRepository.findById(programId).orElseThrow(NotFoundProgramException::new);

        log.info("findProgram : {}", findProgram);


        ProgramResponse programToDto = programMapper.getProgramToDto(findProgram);

        log.info("programToDto : {}", programToDto);


        return programToDto;
    }

    @Transactional
    public ProgramResponse deleteProgram(Long programId) {
        Program program = programRepository.findById(programId).orElseThrow(NotFoundProgramException::new);
        programRepository.deleteById(programId);

        return programMapper.getProgramToDto(program);
    }

}
