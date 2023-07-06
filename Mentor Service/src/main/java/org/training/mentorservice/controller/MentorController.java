package org.training.mentorservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.training.mentorservice.dto.MentorDto;
import org.training.mentorservice.dto.ResponseDto;
import org.training.mentorservice.service.MentorService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/mentors")
public class MentorController {

    @Autowired
    private MentorService mentorService;

    @PostMapping
    public ResponseEntity<ResponseDto> addMentor(@RequestBody @Valid MentorDto mentorDto) {
        return new ResponseEntity<>(mentorService.addMentor(mentorDto), HttpStatus.CREATED);
    }

    @GetMapping("/{mentorId}")
    public ResponseEntity<MentorDto> getMentorById(@PathVariable String mentorId) {
        return new ResponseEntity<>(mentorService.getMentorById(mentorId), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<MentorDto>> getAllMentors() {
        return new ResponseEntity<>(mentorService.getAllMentors(), HttpStatus.OK);
    }
}
