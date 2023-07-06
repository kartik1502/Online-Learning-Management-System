package org.training.studentservice.service.implementation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.BeanUtils;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.training.studentservice.dto.ResponseDto;
import org.training.studentservice.dto.StudentDto;
import org.training.studentservice.entity.Student;
import org.training.studentservice.exception.ResourceConflictExists;
import org.training.studentservice.exception.ResourseNotFound;
import org.training.studentservice.repository.StudentRepository;

import java.util.ArrayList;
import java.util.List;
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

    @Test
    void testDeleteStudent_StudentNotFound() {

        String studentId = "245jkh12-24512435nmj-kjb5k1235-524k1jb51234";
        Mockito.when(studentRepository.findById(studentId)).thenReturn(Optional.empty());
        ResourseNotFound exception = assertThrows(ResourseNotFound.class,
                () -> studentService.deleteStudent(studentId));
        assertEquals("Student with student Id: "+studentId+ " not found", exception.getMessage());
    }

    @Test
    void testDeleteStudent_Success() {

        Student student = new Student();
        student.setStudentId("245jkh12-24512435nmj-kjb5k1235-524k1jb51234");
        student.setFirstName("Karthik");
        student.setLastName("Kulkarni");
        student.setEmailId("kartikkulkarni1411@gmail.com");

        Mockito.when(studentRepository.findById(student.getStudentId())).thenReturn(Optional.of(student));
        ResponseDto responseDto = studentService.deleteStudent(student.getStudentId());
        assertNotNull(responseDto);
        assertEquals("Student deleted successfully", responseDto.getResponseMessage());
    }

    @Test
    void testGetAllStudents_OneStudentPresent() {

        List<Student> students = new ArrayList<>();
        Student student = new Student();
        student.setFirstName("Karthik");
        student.setLastName("kulkarni");
        student.setEmailId("kartikkulkarni1411@gmail.com");
        students.add(student);

        Mockito.when(studentRepository.findAll()).thenReturn(students);

        List<StudentDto> result = studentService.getAllStudents();
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void testGetAllStudent_MultipleStudent() {

        List<Student> students = new ArrayList<>();
        Student student = new Student();
        student.setFirstName("Karthik");
        student.setLastName("kulkarni");
        student.setEmailId("kartikkulkarni1411@gmail.com");
        students.add(student);
        student = new Student();
        student.setFirstName("Kishan");
        student.setLastName("kulkarni");
        student.setEmailId("kulkarnikishan1502@gmail.com");
        students.add(student);

        Mockito.when(studentRepository.findAll()).thenReturn(students);

        List<StudentDto> result = studentService.getAllStudents();
        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void testGetStudentById_StudentNotFound() {

        String studentId = "adce3e37-1b3e-4d55-9fa3-d544db25dc32";

        Mockito.when(studentRepository.findById(studentId)).thenReturn(Optional.empty());
        ResourseNotFound exception = assertThrows(ResourseNotFound.class,
                () -> studentService.getStudentById(studentId));
        assertEquals("Student with student Id: "+studentId+ " not found", exception.getMessage());
    }

    @Test
    void testGetStudentById_Success() {

        Student student = new Student();
        student.setFirstName("Karthik");
        student.setLastName("kulkarni");
        student.setEmailId("kartikkulkarni1411@gmail.com");

        Mockito.when(studentRepository.findById(Mockito.anyString())).thenReturn(Optional.of(student));

        StudentDto studentDto = studentService.getStudentById(Mockito.anyString());
        assertNotNull(studentDto);
        assertEquals("Karthik", studentDto.getFirstName());
        assertEquals("kulkarni", studentDto.getLastName());
    }

    @Test
    void testUpdateStudent_StudentNotFound() {

        String studentId = "adce3e37-1b3e-4d55-9fa3-d544db25dc32";

        Mockito.when(studentRepository.findById(studentId)).thenReturn(Optional.empty());
        StudentDto studentDto = Mockito.mock(StudentDto.class);
        ResourseNotFound exception = assertThrows(ResourseNotFound.class,
                () -> studentService.updateStudent(studentId, studentDto));
        assertEquals("Student with student Id: "+studentId+ " not found", exception.getMessage());
    }

    @Test
    void testUpdateStudent_Success() {

        Student student = new Student();
        student.setFirstName("Karthik");
        student.setLastName("Kulkarni");
        student.setEmailId("kartikkulkarni1411@gmail.com");
        student.setContactNo("6361921186");

        String studentId = "adce3e37-1b3e-4d55-9fa3-d544db25dc32";
        Mockito.when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));

        StudentDto studentDto = new StudentDto();
        BeanUtils.copyProperties(student, studentDto);
        ResponseDto responseDto = studentService.updateStudent(studentId, studentDto);
        assertNotNull(responseDto);
        assertEquals("Student updated successfully", responseDto.getResponseMessage());
    }

    @Test
    void testGetAllStudentsByMentorId() {

        List<Student> students = new ArrayList<>();
        Student student = new Student();
        student.setFirstName("Karthik");
        student.setLastName("kulkarni");
        student.setEmailId("kartikkulkarni1411@gmail.com");
        student.setMentorId("12f00760-d63c-48e0-9739-589ecabb6e05");
        students.add(student);

        Mockito.when(studentRepository.findAllByMentorId(Mockito.anyString())).thenReturn(students);

        List<StudentDto> result = studentService.getAllStudentsByMentorId("12f00760-d63c-48e0-9739-589ecabb6e05");
        assertNotNull(result);
        assertEquals(1, result.size());
    }
}
