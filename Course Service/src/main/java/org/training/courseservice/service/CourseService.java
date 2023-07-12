package org.training.courseservice.service;

import org.training.courseservice.entity.dto.CourseDto;
import org.training.courseservice.entity.dto.ResponseDto;

public interface CourseService {

    ResponseDto addCourse(CourseDto courseDto);

}
