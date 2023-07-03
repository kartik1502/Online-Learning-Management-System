package org.training.studentservice.service.implementation;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.training.studentservice.dto.ResponseDto;
import org.training.studentservice.dto.StudentDto;
import org.training.studentservice.entity.Student;
import org.training.studentservice.exception.ResourceConflictExists;
import org.training.studentservice.exception.ResourseNotFound;
import org.training.studentservice.repository.StudentRepository;
import org.training.studentservice.service.StudentService;

import java.util.Optional;
import java.util.UUID;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Value("${spring.application.responseCode}")
    private String responseCode;

    @Override
    public ResponseDto addStudent(StudentDto studentDto) {

        Optional<Student> student = studentRepository.findStudentByEmailIdOrContactNo(studentDto.getEmailId(), studentDto.getContactNo());
        student.ifPresent(existingStudent -> {
            System.out.println(existingStudent);
            if (existingStudent.getEmailId().equals(studentDto.getEmailId()) && existingStudent.getContactNo().equals(studentDto.getContactNo())) {
                throw new ResourceConflictExists("Student with the same emailId and contact number exists");
            } else if (existingStudent.getEmailId().equals(studentDto.getEmailId())) {
                throw new ResourceConflictExists("Student with the same emailId already exists");
            } else {
                throw new ResourceConflictExists("Student with the same contact number already exists");
            }
        });
        Student newStudent = new Student();
        BeanUtils.copyProperties(studentDto, newStudent);
        newStudent.setStudentId(UUID.randomUUID().toString());
        studentRepository.save(newStudent);
        return new ResponseDto(responseCode, "Student added successfully");
    }

    @Override
    public ResponseDto deleteStudent(String studentId) {

        studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourseNotFound("Student with student Id: "+studentId+ " not found"));
        studentRepository.deleteById(studentId);
        return new ResponseDto("200", "Student deleted successfully");
    }


}
