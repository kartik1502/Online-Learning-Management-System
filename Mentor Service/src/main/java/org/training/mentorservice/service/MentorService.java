package org.training.mentorservice.service;

import org.training.mentorservice.dto.MentorDto;
import org.training.mentorservice.dto.ResponseDto;

public interface MentorService {

    ResponseDto addMentor(MentorDto mentorDto);

    MentorDto getMentorById(String mentorId);
}
