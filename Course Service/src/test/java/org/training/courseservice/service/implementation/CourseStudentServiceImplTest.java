package org.training.courseservice.service.implementation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.training.courseservice.Repository.CourseRepository;
import org.training.courseservice.Repository.CourseStudentRepository;
import org.training.courseservice.entity.Course;
import org.training.courseservice.entity.CourseStudent;
import org.training.courseservice.entity.dto.CourseDto;
import org.training.courseservice.entity.dto.ResponseDto;
import org.training.courseservice.entity.dto.StudentDto;
import org.training.courseservice.exception.ResourceNotFound;
import org.training.courseservice.external.StudentService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
public class CourseStudentServiceImplTest {

    @InjectMocks
    private CourseStudentServiceImpl courseStudentService;

    @Mock
    private CourseStudentRepository courseStudentRepository;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private StudentService studentService;

    @Test
    void testRegisterStudent_CourseNotFound() {

        String courseId = "baf7a5cc-7d01-431c-8c4e-086ea64ef822";
        String studentId = "dd544873-f854-4ca7-bf57-fb3b829b3f91";

        StudentDto studentDto = Mockito.mock(StudentDto.class);
        Mockito.when(studentService.getStudentById(studentId)).thenReturn(studentDto);
        Mockito.when(courseRepository.findById(courseId)).thenReturn(Optional.empty());

        ResourceNotFound exception = assertThrows(ResourceNotFound.class,
                () -> courseStudentService.registerStudent(courseId, studentId));
        assertNotNull(exception);
        assertEquals("Course with course Id: "+courseId+" not found on the server", exception.getMessage());
    }

    @Test
    void testRegisterStudent_Success() {

        String courseId = "baf7a5cc-7d01-431c-8c4e-086ea64ef822";
        String studentId = "dd544873-f854-4ca7-bf57-fb3b829b3f91";

        StudentDto studentDto = Mockito.mock(StudentDto.class);
        Mockito.when(studentService.getStudentById(studentId)).thenReturn(studentDto);

        Course course = Mockito.mock(Course.class);
        Mockito.when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));

        CourseStudent courseStudent = CourseStudent.builder()
                .courseId(courseId)
                .studentId(studentId).build();

        Mockito.when(courseStudentRepository.save(courseStudent)).thenReturn(courseStudent);

        ResponseDto responseDto = courseStudentService.registerStudent(courseId, studentId);
        assertNotNull(responseDto);
        assertEquals("Student registered for the course successfully", responseDto.getResponseMessage());
    }
}
