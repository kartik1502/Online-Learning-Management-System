package org.training.mentorservice.service.implementation;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.training.mentorservice.dto.MentorDto;
import org.training.mentorservice.dto.ResponseDto;
import org.training.mentorservice.entity.Mentor;
import org.training.mentorservice.exception.ResourceConflictException;
import org.training.mentorservice.exception.ResourceNotFound;
import org.training.mentorservice.repository.MentorRepository;
import org.training.mentorservice.service.MentorService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MentorServiceImpl implements MentorService {

    @Autowired
    private MentorRepository mentorRepository;

    @Value("${spring.application.responseCode}")
    private String responseCode;

    @Override
    public ResponseDto addMentor(MentorDto mentorDto) {

        Optional<Mentor> mentor = mentorRepository.findMentorByContactNoOrEmailId(mentorDto.getContactNo(), mentorDto.getEmailId());
        mentor.ifPresent(existingMentor -> {
            if (existingMentor.getEmailId().equals(mentorDto.getEmailId()) && existingMentor.getContactNo().equals(mentorDto.getContactNo())) {
                throw new ResourceConflictException("Mentor with the same emailId and contact number exists");
            } else if (existingMentor.getEmailId().equals(mentorDto.getEmailId())) {
                throw new ResourceConflictException("Mentor with the same emailId already exists");
            } else if (existingMentor.getContactNo().equals(mentorDto.getContactNo())) {
                throw new ResourceConflictException("Mentor with the same contact number already exists");
            }
        });

        Mentor newMentor = new Mentor();
        BeanUtils.copyProperties(mentorDto, newMentor);
        newMentor.setMentorId(UUID.randomUUID().toString());
        mentorRepository.save(newMentor);
        return new ResponseDto(responseCode, "Mentor added Successfully");
    }

    @Override
    public MentorDto getMentorById(String mentorId) {

        return mentorRepository.findById(mentorId).map(mentor -> {
            MentorDto mentorDto = new MentorDto();
            BeanUtils.copyProperties(mentor, mentorDto, "mentorId");
            return mentorDto;
        }).orElseThrow(() -> new ResourceNotFound("Mentor with mentor Id: "+mentorId+ " not found"));
    }

    @Override
    public List<MentorDto> getAllMentors() {

        return mentorRepository.findAll().stream().map(mentor -> {
            MentorDto mentorDto = new MentorDto();
            BeanUtils.copyProperties(mentor, mentorDto, "mentorId");
            return mentorDto;
        }).collect(Collectors.toList());
    }

    @Override
    public ResponseDto deleteMentor(String mentorId) {

        mentorRepository.findById(mentorId).ifPresentOrElse(
                mentor -> mentorRepository.deleteById(mentorId),
                () -> {
                    throw new ResourceNotFound("Mentor with mentor Id: " + mentorId + " not found");
                });
        return new ResponseDto(responseCode,"Mentor Deleted Successfully");
    }

    @Override
    public ResponseDto updateMentor(String mentorId, MentorDto mentorDto) {

        mentorRepository.findById(mentorId).ifPresentOrElse(
                mentor -> {
                    BeanUtils.copyProperties(mentorDto, mentor);
                    mentorRepository.save(mentor);
                },
                () -> {
                    throw new ResourceNotFound("Mentor with mentor Id: " + mentorId + " not found");
                });
        return new ResponseDto(responseCode,"Mentor Updated Successfully");
    }

    @Override
    public List<MentorDto> getAllMentorsById(List<String> mentorIds) {

        return mentorRepository.findAllById(mentorIds).stream().map(mentor -> {
            MentorDto mentorDto = new MentorDto();
            BeanUtils.copyProperties(mentor, mentorDto, "mentorId");
            return mentorDto;
        }).collect(Collectors.toList());
    }
}
