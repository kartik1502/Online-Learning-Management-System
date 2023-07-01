package org.training.studentservice.service.implementation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.training.studentservice.dto.ResponseDto;
import org.training.studentservice.dto.StudentDto;
import org.training.studentservice.entity.Student;
import org.training.studentservice.exception.ResourceConflictExists;
import org.training.studentservice.repository.StudentRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
public class StudentServiceImplTest {

    @InjectMocks
    private StudentServiceImpl studentService;

    @Mock
    private StudentRepository studentRepository;

    @Test
    void testAddStudent_StudentPresent_SameEmailId(){

        StudentDto studentDto = new StudentDto();
        studentDto.setFirstName("Karthik");
        studentDto.setLastName("Kulkarni");
        studentDto.setEmailId("kartikkulkarni1411@gmail.com");
        studentDto.setContactNo("6361921186");

        Student student = new Student();
        student.setFirstName("Karthik");
        student.setLastName("Kulkarni");
        student.setEmailId("kartikkulkarni1411@gmail.com");
        student.setContactNo("6361921187");

        Mockito.when(studentRepository.findStudentByEmailIdOrContactNo(Mockito.anyString(), Mockito.anyString()))
                .thenReturn(Optional.of(student));
        ResourceConflictExists exception = assertThrows(ResourceConflictExists.class,
                () -> studentService.addStudent(studentDto));
        assertEquals("Student with the same emailId already exists", exception.getMessage());
    }

    @Test
    void testAddStudent_StudentPresent_SameContactNo(){

        StudentDto studentDto = new StudentDto();
        studentDto.setFirstName("Karthik");
        studentDto.setLastName("Kulkarni");
        studentDto.setEmailId("kartikkulkarni1411@gmail.com");
        studentDto.setContactNo("6361921186");

        Student student = new Student();
        student.setFirstName("Karthik");
        student.setLastName("Kulkarni");
        student.setEmailId("kartikkulkarni1311@gmail.com");
        student.setContactNo("6361921186");

        Mockito.when(studentRepository.findStudentByEmailIdOrContactNo(Mockito.anyString(), Mockito.anyString()))
                .thenReturn(Optional.of(student));
        ResourceConflictExists exception = assertThrows(ResourceConflictExists.class,
                () -> studentService.addStudent(studentDto));
        assertEquals("Student with the same contact number already exists", exception.getMessage());
    }

    @Test
    void testAddStudent_StudentPresent_SameEmailIdAndContactNo() {

        StudentDto studentDto = new StudentDto();
        studentDto.setFirstName("Karthik");
        studentDto.setLastName("Kulkarni");
        studentDto.setEmailId("kartikkulkarni1411@gmail.com");
        studentDto.setContactNo("6361921186");

        Student student = new Student();
        student.setFirstName("Karthik");
        student.setLastName("Kulkarni");
        student.setEmailId("kartikkulkarni1411@gmail.com");
        student.setContactNo("6361921186");

        Mockito.when(studentRepository.findStudentByEmailIdOrContactNo(Mockito.anyString(), Mockito.anyString()))
                .thenReturn(Optional.of(student));
        ResourceConflictExists exception = assertThrows(ResourceConflictExists.class,
                () -> studentService.addStudent(studentDto));
        assertEquals("Student with the same emailId and contact number exists", exception.getMessage());
    }

    @Test
    void testAddStudent_Success() {

        StudentDto studentDto = new StudentDto();
        studentDto.setFirstName("Karthik");
        studentDto.setLastName("Kulkarni");
        studentDto.setEmailId("kartikkulkarni1411@gmail.com");
        studentDto.setContactNo("6361921186");

        Mockito.when(studentRepository.findStudentByEmailIdOrContactNo(Mockito.anyString(), Mockito.anyString())).thenReturn(Optional.empty());

        ResponseDto responseDto = studentService.addStudent(studentDto);
        assertNotNull(responseDto);
        assertEquals("Student added successfully", responseDto.getResponseMessage());

    }



}
