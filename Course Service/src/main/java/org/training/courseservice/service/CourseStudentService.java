package org.training.courseservice.service;

import org.training.courseservice.dto.ResponseDto;
import org.training.courseservice.dto.StudentCourseDto;

public interface CourseStudentService {

    ResponseDto registerStudent(String courseId, String studentId);

    ResponseDto updateCredits(StudentCourseDto studentCourseDto);
}
