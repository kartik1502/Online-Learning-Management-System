package org.training.studentservice.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.training.studentservice.dto.ResponseDto;
import org.training.studentservice.dto.StudentDto;
import org.training.studentservice.service.StudentService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
public class StudentControllerTest {

    @InjectMocks
    private StudentController studentController;

    @Mock
    private StudentService studentService;

    @Test
    void testAddStudent() {

        StudentDto studentDto = new StudentDto();
        studentDto.setFirstName("Karthik");
        studentDto.setLastName("Kulkarni");
        studentDto.setEmailId("kartikkulkarni1411@gmail.com");
        studentDto.setContactNo("6361921186");

        ResponseDto responseDto = new ResponseDto("200", "Student added successfully");
        Mockito.when(studentService.addStudent(studentDto)).thenReturn(responseDto);

        ResponseEntity<ResponseDto> response = studentController.addStudent(studentDto);
        assertNotNull(response);
        assertEquals("Student added successfully", response.getBody().getResponseMessage());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void testDeleteStudent() {

        String studentId = "adce3e37-1b3e-4d55-9fa3-d544db25dc32";
        ResponseDto responseDto = new ResponseDto("200", "Student deleted successfully");

        Mockito.when(studentService.deleteStudent(studentId)).thenReturn(responseDto);

        ResponseEntity<ResponseDto> response = studentController.deleteById(studentId);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
