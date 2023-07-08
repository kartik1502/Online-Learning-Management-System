package org.training.studentservice.service.implementation;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.training.studentservice.dto.Mentor;
import org.training.studentservice.dto.MentorDto;
import org.training.studentservice.dto.ResponseDto;
import org.training.studentservice.dto.StudentDto;
import org.training.studentservice.entity.Student;
import org.training.studentservice.exception.ResourceConflictExists;
import org.training.studentservice.exception.ResourseNotFound;
import org.training.studentservice.external.MentorService;
import org.training.studentservice.repository.StudentRepository;
import org.training.studentservice.service.StudentService;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private MentorService mentorService;

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

    @Override
    public List<StudentDto> getAllStudents() {

        return studentRepository.findAll().stream().map(student -> {
            StudentDto studentDto = new StudentDto();
            BeanUtils.copyProperties(student, studentDto, "studentId");
            return studentDto;
        }).collect(Collectors.toList());
    }

    @Override
    public StudentDto getStudentById(String studentId) {

        return studentRepository.findById(studentId).map(student -> {
            StudentDto studentDto = new StudentDto();
            BeanUtils.copyProperties(student, studentDto, "studentId");
            return studentDto;
        }).orElseThrow(() -> new ResourseNotFound("Student with student Id: "+studentId+ " not found"));
    }

    @Override
    public ResponseDto updateStudent(String studentId, StudentDto studentDto) {

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourseNotFound("Student with student Id: "+studentId+ " not found"));
        BeanUtils.copyProperties(studentDto, student, "studentId");
        studentRepository.save(student);
        return new ResponseDto(responseCode, "Student updated successfully");
    }

    @Override
    public Mentor getAllStudentsByMentorId(String mentorId) {

        List<StudentDto> students = studentRepository.findAllByMentorId(mentorId)
                .stream().map(student -> {
                    StudentDto studentDto = new StudentDto();
                    BeanUtils.copyProperties(student, studentDto, "studentId");
                    return studentDto;
                }).collect(Collectors.toList());

        MentorDto mentorDto = mentorService.getAllMentorsByIds(mentorId);
        Mentor mentor = new Mentor();
        BeanUtils.copyProperties(mentorDto, mentor);
        mentor.setStudentDtos(students);
        return mentor;
    }

    @Override
    public ResponseDto updateAllStudents(String mentorId) {

        List<Student> students = studentRepository.findAllByMentorId(mentorId);
        List<Student> updateStudents = new ArrayList<>();
        students.forEach(student -> {
            student.setMentorId(null);
            updateStudents.add(student);
        });
        System.out.println(students);
        studentRepository.saveAll(updateStudents);
        return new ResponseDto(responseCode, "Students updated successfully");
    }
}
