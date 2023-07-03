package org.training.studentservice.controllers;

import org.apache.catalina.LifecycleState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.training.studentservice.dto.ResponseDto;
import org.training.studentservice.dto.StudentDto;
import org.training.studentservice.service.StudentService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping
    public ResponseEntity<ResponseDto> addStudent(@RequestBody @Valid StudentDto studentDto){
        return new ResponseEntity<>(studentService.addStudent(studentDto), HttpStatus.CREATED);
    }

    @DeleteMapping("/{studentId}")
    public ResponseEntity<ResponseDto> deleteById(@PathVariable String studentId){
        return new ResponseEntity<>(studentService.deleteStudent(studentId), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<StudentDto>> getAllStudents() {
        return new ResponseEntity<>(studentService.getAllStudents(), HttpStatus.OK);
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<StudentDto> getStudentById(@PathVariable String studentId) {
        return new ResponseEntity<>(studentService.getStudentById(studentId), HttpStatus.OK);
    }
}
