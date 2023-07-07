package org.training.mentorservice.service;

import org.training.mentorservice.dto.MentorDto;
import org.training.mentorservice.dto.ResponseDto;

import java.util.List;

public interface MentorService {

    ResponseDto addMentor(MentorDto mentorDto);

    MentorDto getMentorById(String mentorId);

    List<MentorDto> getAllMentors();

    ResponseDto deleteMentor(String mentorId);

    ResponseDto updateMentor(String mentorId, MentorDto mentorDto);

    List<MentorDto> getAllMentorsById(List<String> mentorIds);
}
