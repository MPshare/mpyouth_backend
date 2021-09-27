package kr.go.mapo.mpyouth.global;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import kr.go.mapo.mpyouth.api.ProgramDto;
import kr.go.mapo.mpyouth.domain.Program;
import kr.go.mapo.mpyouth.domain.Program.ProgramBuilder;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-09-27T11:30:24+0900",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 15.0.2 (Oracle Corporation)"
)
@Component
public class ProgramMapperImpl implements ProgramMapper {

    @Override
    public Program saveDtoToProgram(ProgramDto programDto) {
        if ( programDto == null ) {
            return null;
        }

        ProgramBuilder program = Program.builder();

        program.id( programDto.getId() );
        program.title( programDto.getTitle() );
        program.description( programDto.getDescription() );
        program.startDate( programDto.getStartDate() );
        program.endDate( programDto.getEndDate() );
        program.recruitStartDate( programDto.getRecruitStartDate() );
        program.recruitEndDate( programDto.getRecruitEndDate() );
        program.recruitNumber( programDto.getRecruitNumber() );
        program.location( programDto.getLocation() );
        program.managerName( programDto.getManagerName() );
        program.managerContact( programDto.getManagerContact() );
        program.url( programDto.getUrl() );
        program.recruitStatus( programDto.getRecruitStatus() );
        program.entryFee( programDto.getEntryFee() );
        program.targetAge( programDto.getTargetAge() );
        program.caution( programDto.getCaution() );
        program.period( programDto.getPeriod() );
        program.volunteerType( programDto.getVolunteerType() );

        return program.build();
    }

    @Override
    public ProgramDto getProgramToDto(Program program) {
        if ( program == null ) {
            return null;
        }

        ProgramDto programDto = new ProgramDto();

        programDto.setId( program.getId() );
        programDto.setTitle( program.getTitle() );
        programDto.setDescription( program.getDescription() );
        programDto.setStartDate( program.getStartDate() );
        programDto.setEndDate( program.getEndDate() );
        programDto.setRecruitStartDate( program.getRecruitStartDate() );
        programDto.setRecruitEndDate( program.getRecruitEndDate() );
        programDto.setRecruitNumber( program.getRecruitNumber() );
        programDto.setLocation( program.getLocation() );
        programDto.setManagerName( program.getManagerName() );
        programDto.setManagerContact( program.getManagerContact() );
        programDto.setUrl( program.getUrl() );
        programDto.setRecruitStatus( program.getRecruitStatus() );
        programDto.setEntryFee( program.getEntryFee() );
        programDto.setTargetAge( program.getTargetAge() );
        programDto.setCaution( program.getCaution() );
        programDto.setPeriod( program.getPeriod() );
        programDto.setVolunteerType( program.getVolunteerType() );

        return programDto;
    }

    @Override
    public List<ProgramDto> getProgramsToDtos(List<Program> programs) {
        if ( programs == null ) {
            return null;
        }

        List<ProgramDto> list = new ArrayList<ProgramDto>( programs.size() );
        for ( Program program : programs ) {
            list.add( getProgramToDto( program ) );
        }

        return list;
    }

    @Override
    public void updateDtoToProgram(ProgramDto programDto, Program program) {
        if ( programDto == null ) {
            return;
        }

        if ( programDto.getId() != null ) {
            program.setId( programDto.getId() );
        }
        if ( programDto.getTitle() != null ) {
            program.setTitle( programDto.getTitle() );
        }
        if ( programDto.getDescription() != null ) {
            program.setDescription( programDto.getDescription() );
        }
        if ( programDto.getStartDate() != null ) {
            program.setStartDate( programDto.getStartDate() );
        }
        if ( programDto.getEndDate() != null ) {
            program.setEndDate( programDto.getEndDate() );
        }
        if ( programDto.getRecruitStartDate() != null ) {
            program.setRecruitStartDate( programDto.getRecruitStartDate() );
        }
        if ( programDto.getRecruitEndDate() != null ) {
            program.setRecruitEndDate( programDto.getRecruitEndDate() );
        }
        if ( programDto.getRecruitNumber() != null ) {
            program.setRecruitNumber( programDto.getRecruitNumber() );
        }
        if ( programDto.getLocation() != null ) {
            program.setLocation( programDto.getLocation() );
        }
        if ( programDto.getManagerName() != null ) {
            program.setManagerName( programDto.getManagerName() );
        }
        if ( programDto.getManagerContact() != null ) {
            program.setManagerContact( programDto.getManagerContact() );
        }
        if ( programDto.getUrl() != null ) {
            program.setUrl( programDto.getUrl() );
        }
        if ( programDto.getRecruitStatus() != null ) {
            program.setRecruitStatus( programDto.getRecruitStatus() );
        }
        if ( programDto.getEntryFee() != null ) {
            program.setEntryFee( programDto.getEntryFee() );
        }
        if ( programDto.getTargetAge() != null ) {
            program.setTargetAge( programDto.getTargetAge() );
        }
        if ( programDto.getCaution() != null ) {
            program.setCaution( programDto.getCaution() );
        }
        if ( programDto.getPeriod() != null ) {
            program.setPeriod( programDto.getPeriod() );
        }
        if ( programDto.getVolunteerType() != null ) {
            program.setVolunteerType( programDto.getVolunteerType() );
        }
    }
}
