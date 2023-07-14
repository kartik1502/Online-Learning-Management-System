package org.training.courseservice.service;

import org.training.courseservice.dto.CourseDto;
import org.training.courseservice.dto.ResponseDto;

import java.util.List;

public interface CourseService {

    ResponseDto addCourse(CourseDto courseDto);

    ResponseDto updateCourse(String courseId, CourseDto courseDto);

    CourseDto getCourseById(String courseId);

    List<CourseDto> getAllCourses();
}
