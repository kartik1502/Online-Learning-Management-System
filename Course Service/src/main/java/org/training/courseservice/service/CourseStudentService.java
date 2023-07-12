package org.training.courseservice.service;

import org.training.courseservice.entity.dto.ResponseDto;

public interface CourseStudentService {

    ResponseDto registerStudent(String courseId, String studentId);
}
