package org.training.studentservice.service;

import org.training.studentservice.dto.Mentor;
import org.training.studentservice.dto.ResponseDto;
import org.training.studentservice.dto.StudentDto;

import java.util.List;
import java.util.Map;

public interface StudentService {
    ResponseDto addStudent(StudentDto studentDto);

    ResponseDto deleteStudent(String studentId);

    List<StudentDto> getAllStudents();

    StudentDto getStudentById(String studentId);

    ResponseDto updateStudent(String studentId, StudentDto studentDto);

    Mentor getAllStudentsByMentorId(String mentorId);

    ResponseDto updateAllStudents(String mentorId);

    List<StudentDto> getAllStudentsById(List<String> studentIds);
}