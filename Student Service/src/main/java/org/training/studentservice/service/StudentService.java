package org.training.studentservice.service;

import org.training.studentservice.dto.ResponseDto;
import org.training.studentservice.dto.StudentDto;

public interface StudentService {
    ResponseDto addStudent(StudentDto studentDto);
}