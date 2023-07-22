package org.training.courseservice.service.implementation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.BeanUtils;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.training.courseservice.Repository.CourseRepository;
import org.training.courseservice.Repository.CourseStudentRepository;
import org.training.courseservice.dto.ViewCourse;
import org.training.courseservice.entity.Course;
import org.training.courseservice.dto.CourseDto;
import org.training.courseservice.dto.MentorDto;
import org.training.courseservice.dto.ResponseDto;
import org.training.courseservice.entity.CourseStudent;
import org.training.courseservice.exception.ResourceConflict;
import org.training.courseservice.exception.ResourceNotFound;
import org.training.courseservice.external.MentorService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
public class CourseServiceImplTest {

    @InjectMocks
    private CourseServiceImpl courseService;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private CourseStudentRepository courseStudentRepository;
    @Mock
    private MentorService mentorService;

    @Test
    void testAddCourse_SameCourseNameAndMentorId() {

        CourseDto courseDto = CourseDto.builder()
                .name("Java Core")
                .credits(5)
                .mentorId("e34395fd-8e66-4ab7-be23-717230903ad9").build();

        Course course = new Course();
        BeanUtils.copyProperties(courseDto, course);

        Mockito.when(courseRepository.findCourseByNameAndMentorId(courseDto.getName(), courseDto.getMentorId())).thenReturn(Optional.of(course));

        ResourceConflict exception = assertThrows(ResourceConflict.class,
                () -> courseService.addCourse(courseDto));
        assertEquals("Course with same name and mentor is already present", exception.getMessage());
    }

    @Test
    void testAddCourse_Success() {

        CourseDto courseDto = CourseDto.builder()
                .name("Java Core")
                .credits(5)
                .mentorId("e34395fd-8e66-4ab7-be23-717230903ad9").build();

        Mockito.when(courseRepository.findCourseByNameAndMentorId(courseDto.getName(), courseDto.getMentorId())).thenReturn(Optional.empty());

        MentorDto mentorDto = Mockito.mock(MentorDto.class);

        Mockito.when(mentorService.getMentorById(courseDto.getMentorId())).thenReturn(mentorDto);

        ResponseDto responseDto = courseService.addCourse(courseDto);
        assertNotNull(responseDto);
        assertEquals("Course added successfully", responseDto.getResponseMessage());
    }

    @Test
    void testUpdateCourse_CourseNotFound() {

        String courseId = "e34395fd-8e66-4ab7-be23-717230903ad9";

        Mockito.when(courseRepository.findById(courseId)).thenReturn(Optional.empty());

        CourseDto courseDto = Mockito.mock(CourseDto.class);
        ResourceNotFound exception = assertThrows(ResourceNotFound.class,
                () -> courseService.updateCourse(courseId, courseDto));
        assertNotNull(exception);
        assertEquals("Course with course Id: "+courseId+" not found on the server", exception.getMessage());
    }

    @Test
    void testUpdateCourse_Success() {

        String courseId = "e34395fd-8e66-4ab7-be23-717230903ad9";
        String mentorId = "baf7a5cc-7d01-431c-8c4e-086ea64ef822";
        CourseDto courseDto = CourseDto.builder()
                .name("C Basics")
                .credits(4)
                .mentorId("baf7a5cc-7d01-431c-8c4e-086ea64ef822")
                .build();

        Course course = new Course();
        BeanUtils.copyProperties(courseDto, course);
        Mockito.when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));

        MentorDto mentorDto = Mockito.mock(MentorDto.class);
        Mockito.when(mentorService.getMentorById(mentorId)).thenReturn(mentorDto);

        ResponseDto responseDto = courseService.updateCourse(courseId, courseDto);
        assertNotNull(responseDto);
        assertEquals("Course updated successfully", responseDto.getResponseMessage());
    }

    @Test
    void testGetCourseById_CourseNotFound() {

        String courseId = "baf7a5cc-7d01-431c-8c4e-086ea64ef822";

        Mockito.when(courseRepository.findById(courseId)).thenReturn(Optional.empty());
        ResourceNotFound exception = assertThrows(ResourceNotFound.class,
                () -> courseService.getCourseById(courseId));
        assertNotNull(exception);
        assertEquals("Course with courseId: "+courseId+" not found on the server", exception.getMessage());
    }

    @Test
    void testGetCourseById_Success() {

        String courseId = "e34395fd-8e66-4ab7-be23-717230903ad9";
        Course course = Course.builder()
                .name("C Basics")
                .credits(4)
                .mentorId("baf7a5cc-7d01-431c-8c4e-086ea64ef822")
                .build();

        Mockito.when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));

        CourseDto courseDto = courseService.getCourseById(courseId);
        assertNotNull(courseDto);
        assertEquals(course.getName(), courseDto.getName());
    }

    @Test
    void testgetAllCourses_OneCourse() {

        List<Course> courses = new ArrayList<>();
        Course course = Course.builder()
                .name("C Basics")
                .credits(4)
                .mentorId("baf7a5cc-7d01-431c-8c4e-086ea64ef822")
                .build();
        courses.add(course);

        Mockito.when(courseRepository.findAll()).thenReturn(courses);

        List<CourseDto> result = courseService.getAllCourses();
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void testgetAllCourses_EmptyList() {

        Mockito.when(courseRepository.findAll()).thenReturn(new ArrayList<>());

        List<CourseDto> result = courseService.getAllCourses();
        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test
    void testGetStudentCourseInfo_CourseNotFound() {

        String courseId = "f4c77ecb-8d76-48b6-9c5f-0850a4e2ac3b";
        Mockito.when(courseRepository.findById(courseId)).thenReturn(Optional.empty());
        ResourceNotFound exception = assertThrows(ResourceNotFound.class,
                () -> courseService.getStudentCourseInfo(courseId));
        assertNotNull(exception);
        assertEquals("Course with courseId: "+courseId+" not found on the server", exception.getMessage());
    }

    @Test
    void testGetStudentCourseInfo_Success() {

        String courseId = "542e099d-2115-4f3f-be85-942a2ffc4749";
        Course course = Course.builder()
                .courseId("542e099d-2115-4f3f-be85-942a2ffc4749")
                .name("C Basics")
                .credits(5)
                .mentorId("baf7a5cc-7d01-431c-8c4e-086ea64ef822")
                .build();
        Mockito.when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));

        List<CourseStudent> courseStudents = new ArrayList<>();
        CourseStudent courseStudent = CourseStudent.builder()
                .studentId("e34395fd-8e66-4ab7-be23-717230903ad9")
                .courseId(course.getCourseId())
                .creditsAwarded(2)
                .build();
        courseStudents.add(courseStudent);
        courseStudent = CourseStudent.builder()
                .studentId("f4c77ecb-8d76-48b6-9c5f-0850a4e2ac3b")
                .courseId(course.getCourseId())
                .creditsAwarded(3)
                .build();
        courseStudents.add(courseStudent);

        Mockito.when(courseStudentRepository.findAllByCourseId(courseId)).thenReturn(courseStudents);

        ViewCourse viewCourse = courseService.getStudentCourseInfo(courseId);
        assertNotNull(viewCourse);
        assertEquals(2, viewCourse.getStudentsCredits().size());
    }
}
