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
import org.training.studentservice.entity.Student;
import org.training.studentservice.service.StudentService;

import java.util.ArrayList;
import java.util.List;

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

    @Test
    void testGetAllStudents() {

        List<StudentDto> students = new ArrayList<>();
        StudentDto student = new StudentDto();
        student.setFirstName("Karthik");
        student.setLastName("kulkarni");
        student.setEmailId("kartikkulkarni1411@gmail.com");
        students.add(student);
        student = new StudentDto();
        student.setFirstName("Kishan");
        student.setLastName("kulkarni");
        student.setEmailId("kulkarnikishan1502@gmail.com");
        students.add(student);

        Mockito.when(studentService.getAllStudents()).thenReturn(students);

        ResponseEntity<List<StudentDto>> response = studentController.getAllStudents();
        assertNotNull(response);
        assertEquals(2, response.getBody().size());
        assertEquals(HttpStatus.OK ,response.getStatusCode());
    }

    @Test
    void testGetStudentById() {

        StudentDto student = new StudentDto();
        student.setFirstName("Karthik");
        student.setLastName("kulkarni");
        student.setEmailId("kartikkulkarni1411@gmail.com");

        Mockito.when(studentService.getStudentById(Mockito.anyString())).thenReturn(student);

        ResponseEntity<StudentDto> response = studentController.getStudentById(Mockito.anyString());
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testUpdateStudent() {

        String studentId = "adce3e37-1b3e-4d55-9fa3-d544db25dc32";
        StudentDto student = new StudentDto();
        student.setFirstName("Karthik");
        student.setLastName("Kulkarni");
        student.setEmailId("kartikkulkarni1411@gmail.com");
        student.setContactNo("6361921186");

        ResponseDto responseDto = new ResponseDto("200", "Student updated Successfully");
        Mockito.when(studentService.updateStudent(studentId, student)).thenReturn(responseDto);
        ResponseEntity<ResponseDto> response = studentController.updateStudent(studentId, student);
        assertNotNull(response);
        assertEquals("Student updated Successfully", response.getBody().getResponseMessage());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testGetAllStudentsByMentorId() {

        List<StudentDto> students = new ArrayList<>();
        StudentDto student = new StudentDto();
        student.setFirstName("Karthik");
        student.setLastName("kulkarni");
        student.setEmailId("kartikkulkarni1411@gmail.com");
        student.setMentorId("12f00760-d63c-48e0-9739-589ecabb6e05");
        students.add(student);

        Mockito.when(studentService.getAllStudentsByMentorId(Mockito.anyString())).thenReturn(students);

        ResponseEntity<List<StudentDto>> response = studentController.getAllStudentsByMentorId(Mockito.anyString());
        assertNotNull(response);
        assertEquals(1, response.getBody().size());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
