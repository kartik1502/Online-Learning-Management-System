package org.training.courseservice.service;

import org.training.courseservice.dto.ResponseDto;

public interface CourseStudentService {

    ResponseDto registerStudent(String courseId, String studentId);
}
