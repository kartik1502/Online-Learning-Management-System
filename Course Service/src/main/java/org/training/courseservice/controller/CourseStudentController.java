package org.training.courseservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.training.courseservice.dto.ResponseDto;
import org.training.courseservice.dto.StudentCourseDto;
import org.training.courseservice.service.CourseStudentService;

@RestController
@RequestMapping("/courses-students")
public class CourseStudentController {

    @Autowired
    private CourseStudentService courseStudentService;

    @PostMapping("/courses/{courseId}/students/{studentId}")
    public ResponseEntity<ResponseDto> registerStudent(@PathVariable String courseId, @PathVariable String studentId) {
        return new ResponseEntity<>(courseStudentService.registerStudent(courseId, studentId), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<ResponseDto> updateCredits(@RequestBody StudentCourseDto studentCourseDto) {
        return new ResponseEntity<>(courseStudentService.updateCredits(studentCourseDto), HttpStatus.OK);
    }
}
