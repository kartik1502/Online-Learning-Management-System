package org.training.courseservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.training.courseservice.dto.CourseDto;
import org.training.courseservice.dto.ResponseDto;
import org.training.courseservice.dto.ViewCourse;
import org.training.courseservice.service.CourseService;

import javax.swing.text.View;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @PostMapping
    public ResponseEntity<ResponseDto> addCourse(@RequestBody @Valid CourseDto courseDto) {
        return new ResponseEntity<>(courseService.addCourse(courseDto), HttpStatus.CREATED);
    }

    @PutMapping("/{courseId}")
    public ResponseEntity<ResponseDto> updateCourse(@PathVariable String courseId, @RequestBody CourseDto courseDto){
        return new ResponseEntity<>(courseService.updateCourse(courseId, courseDto), HttpStatus.OK);
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<CourseDto> getCourseById(@PathVariable String courseId) {
        return new ResponseEntity<>(courseService.getCourseById(courseId), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<CourseDto>> getAllCourses() {
        return new ResponseEntity<>(courseService.getAllCourses(), HttpStatus.OK);
    }

    @GetMapping("/info/{courseId}")
    public ResponseEntity<ViewCourse> getCoursesByStudentId(@PathVariable String courseId) {
        return new ResponseEntity<>(courseService.getStudentCourseInfo(courseId), HttpStatus.OK);
    }
}
