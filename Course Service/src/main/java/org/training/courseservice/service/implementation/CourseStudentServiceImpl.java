package org.training.courseservice.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.training.courseservice.Repository.CourseRepository;
import org.training.courseservice.Repository.CourseStudentRepository;
import org.training.courseservice.entity.CourseStudent;
import org.training.courseservice.entity.dto.ResponseDto;
import org.training.courseservice.exception.ResourceNotFound;
import org.training.courseservice.external.StudentService;
import org.training.courseservice.service.CourseStudentService;

import java.util.UUID;

@Service
public class CourseStudentServiceImpl implements CourseStudentService {

    @Autowired
    private CourseStudentRepository repository;

    @Autowired
    private StudentService studentService;

    @Autowired
    private CourseRepository courseRepository;

    @Value("${spring.application.responseCode}")
    private String responseCode;

    @Override
    public ResponseDto registerStudent(String courseId, String studentId) {

        studentService.getStudentById(studentId);

        courseRepository.findById(courseId).orElseThrow(
                () -> new ResourceNotFound("Course with course Id: "+courseId+" not found on the server")
        );
        CourseStudent student = CourseStudent.builder()
                .courseStudentId(UUID.randomUUID().toString())
                .studentId(studentId)
                .courseId(courseId)
                .creditsAwarded(0).build();

        repository.save(student);
        return new ResponseDto(responseCode, "Student registered for the course successfully");
    }
}
