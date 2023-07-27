package org.training.courseservice.service.implementation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.training.courseservice.Repository.CourseRepository;
import org.training.courseservice.Repository.CourseStudentRepository;
import org.training.courseservice.dto.StudentCourseDto;
import org.training.courseservice.entity.Course;
import org.training.courseservice.entity.CourseStudent;
import org.training.courseservice.dto.ResponseDto;
import org.training.courseservice.dto.StudentDto;
import org.training.courseservice.exception.ResourceNotAcceptable;
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

    @Test
    void testUpdateCredits_StudentNotRegisteredWithCourse() {

        StudentCourseDto studentCourseDto = StudentCourseDto.builder()
                .studentId("dd544873-f854-4ca7-bf57-fb3b829b3f91")
                .courseId("baf7a5cc-7d01-431c-8c4e-086ea64ef822")
                .awardedCredits(5)
                .build();

        Mockito.when(courseStudentRepository.findByStudentIdAndCourseId(studentCourseDto.getStudentId(), studentCourseDto.getCourseId())).thenReturn(Optional.empty());
        ResourceNotFound exception = assertThrows(ResourceNotFound.class,
                () -> courseStudentService.updateCredits(studentCourseDto));
        assertNotNull(exception);
        assertEquals("Student with student Id:"+studentCourseDto.getStudentId()+" has not registered to course with course Id:"+studentCourseDto.getCourseId(), exception.getMessage());
    }

    @Test
    void testUpdateCredits_CourseNotFound() {

        StudentCourseDto studentCourseDto = StudentCourseDto.builder()
                .studentId("dd544873-f854-4ca7-bf57-fb3b829b3f91")
                .courseId("baf7a5cc-7d01-431c-8c4e-086ea64ef822")
                .awardedCredits(5)
                .build();

        CourseStudent courseStudent = Mockito.mock(CourseStudent.class);
        Mockito.when(courseStudentRepository.findByStudentIdAndCourseId(studentCourseDto.getStudentId(), studentCourseDto.getCourseId())).thenReturn(Optional.of(courseStudent));
        Mockito.when(courseRepository.findById(studentCourseDto.getCourseId())).thenReturn(Optional.empty());

        ResourceNotFound exception = assertThrows(ResourceNotFound.class,
                () -> courseStudentService.updateCredits(studentCourseDto));
        assertNotNull(exception);
        assertEquals("Course with course Id: "+studentCourseDto.getCourseId()+" not found on the server", exception.getMessage());
    }

    @Test
    void testUpdateCredits_MoreCreditsAwarded() {

        StudentCourseDto studentCourseDto = StudentCourseDto.builder()
                .studentId("dd544873-f854-4ca7-bf57-fb3b829b3f91")
                .courseId("baf7a5cc-7d01-431c-8c4e-086ea64ef822")
                .awardedCredits(5)
                .build();

        CourseStudent courseStudent = Mockito.mock(CourseStudent.class);
        Mockito.when(courseStudentRepository.findByStudentIdAndCourseId(studentCourseDto.getStudentId(), studentCourseDto.getCourseId())).thenReturn(Optional.of(courseStudent));

        Course course = Course.builder()
                .name("Java Core")
                .credits(3)
                .mentorId("dd544873-f854-4ca7-bf57-fb3b829b3f91")
                .build();

        Mockito.when(courseRepository.findById(studentCourseDto.getCourseId())).thenReturn(Optional.of(course));

        ResourceNotAcceptable exception = assertThrows(ResourceNotAcceptable.class,
                () -> courseStudentService.updateCredits(studentCourseDto));
        assertNotNull(exception);
        assertEquals("Maximum credits that can be awarded is "+course.getCredits(), exception.getMessage());
    }

    @Test
    void testUpdateCredits_Success() {

        StudentCourseDto studentCourseDto = StudentCourseDto.builder()
                .studentId("dd544873-f854-4ca7-bf57-fb3b829b3f91")
                .courseId("baf7a5cc-7d01-431c-8c4e-086ea64ef822")
                .awardedCredits(1)
                .build();

        CourseStudent courseStudent = Mockito.mock(CourseStudent.class);
        Mockito.when(courseStudentRepository.findByStudentIdAndCourseId(studentCourseDto.getStudentId(), studentCourseDto.getCourseId())).thenReturn(Optional.of(courseStudent));

        Course course = Course.builder()
                .name("Java Core")
                .credits(3)
                .mentorId("dd544873-f854-4ca7-bf57-fb3b829b3f91")
                .build();

        Mockito.when(courseRepository.findById(studentCourseDto.getCourseId())).thenReturn(Optional.of(course));

        ResponseDto responseDto = courseStudentService.updateCredits(studentCourseDto);
        assertNotNull(responseDto);
        assertEquals("Credits updated successfully", responseDto.getResponseMessage());
    }
}
