package org.training.courseservice.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.training.courseservice.dto.ResponseDto;
import org.training.courseservice.dto.StudentCourseDto;
import org.training.courseservice.service.CourseStudentService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
public class CourseStudentControllerTest {

    @InjectMocks
    private CourseStudentController courseStudentController;

    @Mock
    private CourseStudentService courseStudentService;

    @Test
    void testRegisterStudent() {

        String courseId = "baf7a5cc-7d01-431c-8c4e-086ea64ef822";
        String studentId = "dd544873-f854-4ca7-bf57-fb3b829b3f91";

        ResponseDto responseDto = new ResponseDto("200","Student registered for the course successfully");

        Mockito.when(courseStudentService.registerStudent(courseId, studentId)).thenReturn(responseDto);

        ResponseEntity<ResponseDto> response = courseStudentController.registerStudent(courseId, studentId);
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Student registered for the course successfully", response.getBody().getResponseMessage());
    }

    @Test
    void testUpdateCredits() {

        StudentCourseDto studentCourseDto = Mockito.mock(StudentCourseDto.class);
        ResponseDto responseDto = new ResponseDto("200","Credits updated successfully");

        Mockito.when(courseStudentService.updateCredits(studentCourseDto)).thenReturn(responseDto);

        ResponseEntity<ResponseDto> response = courseStudentController.updateCredits(studentCourseDto);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Credits updated successfully", response.getBody().getResponseMessage());
    }
}
