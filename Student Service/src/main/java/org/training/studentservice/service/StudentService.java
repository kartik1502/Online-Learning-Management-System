package org.training.studentservice.service;

import org.training.studentservice.dto.ResponseDto;
import org.training.studentservice.dto.StudentDto;

import java.util.List;

public interface StudentService {
    ResponseDto addStudent(StudentDto studentDto);

    ResponseDto deleteStudent(String studentId);

}