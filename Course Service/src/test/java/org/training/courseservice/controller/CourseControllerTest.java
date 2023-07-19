package org.training.courseservice.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.training.courseservice.dto.CourseDto;
import org.training.courseservice.dto.ResponseDto;
import org.training.courseservice.dto.ViewCourse;
import org.training.courseservice.service.CourseService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@ExtendWith(SpringExtension.class)
public class CourseControllerTest {

    @InjectMocks
    private CourseController courseController;

    @Mock
    private CourseService courseService;

    @Test
    void testAddCourse() {

        CourseDto courseDto = Mockito.mock(CourseDto.class);
        ResponseDto responseDto = new ResponseDto("200", "Course added successfully");

        Mockito.when(courseService.addCourse(courseDto)).thenReturn(responseDto);

        ResponseEntity<ResponseDto> response = courseController.addCourse(courseDto);
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Course added successfully", response.getBody().getResponseMessage());
    }

    @Test
    void testUpdateCourse() {

        String courseId = "e34395fd-8e66-4ab7-be23-717230903ad9";
        CourseDto courseDto = Mockito.mock(CourseDto.class);

        Mockito.when(courseService.updateCourse(courseId, courseDto)).thenReturn(new ResponseDto("200", "Course updated successfully"));

        ResponseEntity<ResponseDto> response = courseController.updateCourse(courseId, courseDto);
        assertNotNull(response);
        assertEquals("Course updated successfully", response.getBody().getResponseMessage());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testGetCourseById() {

        String courseId = "e34395fd-8e66-4ab7-be23-717230903ad9";
        CourseDto courseDto = CourseDto.builder()
                .name("C Basics")
                .credits(4)
                .mentorId("baf7a5cc-7d01-431c-8c4e-086ea64ef822")
                .build();

        Mockito.when(courseService.getCourseById(courseId)).thenReturn(courseDto);

        ResponseEntity<CourseDto> response = courseController.getCourseById(courseId);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testGetAllCourses() {

        List<CourseDto> courses = new ArrayList<>();
        CourseDto course = CourseDto.builder()
                .name("C Basics")
                .credits(4)
                .mentorId("baf7a5cc-7d01-431c-8c4e-086ea64ef822")
                .build();
        courses.add(course);
        course = CourseDto.builder()
                .name("Java Core")
                .credits(5)
                .mentorId("e34395fd-8e66-4ab7-be23-717230903ad9")
                .build();
        courses.add(course);
        Mockito.when(courseService.getAllCourses()).thenReturn(courses);

        ResponseEntity<List<CourseDto>> response = courseController.getAllCourses();
        assertNotNull(response);
        assertEquals(2, response.getBody().size());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testGetCoursesByStudentId() {

        String studentId = "baf7a5cc-7d01-431c-8c4e-086ea64ef822";
        List<ViewCourse> courses = new ArrayList<>();
        ViewCourse course = ViewCourse.builder()
                .name("C Basics")
                .credits(4)
                .awardedCredits(1)
                .build();
        courses.add(course);
        course = ViewCourse.builder()
                .name("Java Core")
                .credits(5)
                .awardedCredits(2)
                .build();
        courses.add(course);
        Mockito.when(courseService.getCoursesByStudentId(studentId)).thenReturn(courses);

        ResponseEntity<List<ViewCourse>> response = courseController.getCoursesByStudentId(studentId);
        assertNotNull(response);
        assertEquals(2, response.getBody().size());
    }

}
