package org.training.studentservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.training.studentservice.dto.ResponseDto;
import org.training.studentservice.dto.StudentDto;
import org.training.studentservice.service.StudentService;

import javax.validation.Valid;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping
    public ResponseEntity<ResponseDto> addStudent(@RequestBody @Valid StudentDto studentDto){

        return new ResponseEntity<>(studentService.addStudent(studentDto), HttpStatus.CREATED);
    }
}
