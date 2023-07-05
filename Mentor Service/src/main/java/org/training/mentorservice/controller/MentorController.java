package org.training.mentorservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.training.mentorservice.dto.MentorDto;
import org.training.mentorservice.dto.ResponseDto;
import org.training.mentorservice.service.MentorService;

import javax.validation.Valid;

@RestController
@RequestMapping("/mentors")
public class MentorController {

    @Autowired
    private MentorService mentorService;

    @PostMapping
    public ResponseEntity<ResponseDto> addMentor(@RequestBody @Valid MentorDto mentorDto) {
        return new ResponseEntity<>(mentorService.addMentor(mentorDto), HttpStatus.CREATED);
    }
}
