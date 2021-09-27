package kr.go.mapo.mpyouth.service;

import kr.go.mapo.mpyouth.api.ProgramDto;
import kr.go.mapo.mpyouth.api.ProgramFileRequest;
import kr.go.mapo.mpyouth.domain.Program;
import kr.go.mapo.mpyouth.domain.ProgramFile;
import kr.go.mapo.mpyouth.global.ProgramMapper;
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
import java.util.*;

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

    @Transactional
    public ProgramDto saveProgram(ProgramDto programDto, List<MultipartFile> imageFiles) throws IOException {

        Program newProgram = programMapper.saveDtoToProgram(programDto);
        programRepository.save(newProgram);

        saveImages(newProgram.getId(), imageFiles);

        entityManager.flush();
        entityManager.clear();

        Program findProgram = programRepository.findById(newProgram.getId()).orElse(null);


        return programMapper.getProgramToDto(findProgram);

    }

    private void saveImages(Long programId, List<MultipartFile> imageFiles) throws IOException {
        if (imageFiles != null && !imageFiles.isEmpty()) {
            MultipartFile firstImage = imageFiles.get(0);

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
                            .programId(programId)
                            .build();

                    ProgramFile newProgramFile = programMapper.saveDtoToProgram(programFile);

                    Program program = programRepository.findById(programId).orElse(null);
                    newProgramFile.setProgram(program);

                    programFileRepository.save(newProgramFile);
                }
            }
        }
    }

    @Transactional
    public ProgramDto updateProgram(ProgramDto programDto) {
        Long updateId = programDto.getProgramId();
        Program findProgram = programRepository.findById(updateId).orElse(null);

        programMapper.updateDtoToProgram(programDto, findProgram);

        return programMapper.getProgramToDto(findProgram);
    }


    public List<ProgramDto> findPrograms() {
        List<Program> programs = programRepository.findAll();

        return programMapper.getProgramsToDtos(programs);
    }

    public ProgramDto findOne(Long programId) {
        Program findProgram = programRepository.findById(programId).orElse(null);

        log.info("findProgram : {}", findProgram);

        ProgramDto programToDto = programMapper.getProgramToDto(findProgram);

        log.info("programToDto : {}", programToDto);


        return programToDto;
    }

    @Transactional
    public ProgramDto deleteProgram(Long programId) {
        Program program = programRepository.findById(programId).orElse(null);
        programRepository.deleteById(programId);

        return programMapper.getProgramToDto(program);
    }

}
