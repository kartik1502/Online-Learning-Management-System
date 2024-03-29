package org.training.studentservice.service.implementation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.BeanUtils;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.training.studentservice.dto.*;
import org.training.studentservice.entity.Student;
import org.training.studentservice.exception.ResourceConflictExists;
import org.training.studentservice.exception.ResourseNotFound;
import org.training.studentservice.external.CourseService;
import org.training.studentservice.external.MentorService;
import org.training.studentservice.repository.StudentRepository;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
public class StudentServiceImplTest {

    @InjectMocks
    private StudentServiceImpl studentService;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private MentorService mentorService;

    @Mock
    private CourseService courseService;
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
        studentDto.setMentorId("b54c5166-d94f-484f-9748-0a361726ce3b");

        Mockito.when(studentRepository.findStudentByEmailIdOrContactNo(Mockito.anyString(), Mockito.anyString())).thenReturn(Optional.empty());

        MentorDto mentorDto = MentorDto.builder()
                        .mentorName("Vikram")
                        .emailId("vikaramsr1234@gmail.com")
                        .contactNo("8495125784").build();
        Mockito.when(mentorService.getMentorById("b54c5166-d94f-484f-9748-0a361726ce3b")).thenReturn(mentorDto);
        ResponseDto responseDto = studentService.addStudent(studentDto);
        assertNotNull(responseDto);
        assertEquals("Student added successfully", responseDto.getResponseMessage());

    }

    @Test
    void testAddStudent_MentorNotFound() {

        StudentDto studentDto = new StudentDto();
        studentDto.setFirstName("Karthik");
        studentDto.setLastName("Kulkarni");
        studentDto.setEmailId("kartikkulkarni1411@gmail.com");
        studentDto.setContactNo("6361921186");
        studentDto.setMentorId("b54c5166-d94f-484f-9748-0a361726ce3b");

        Mockito.when(studentRepository.findStudentByEmailIdOrContactNo(Mockito.anyString(), Mockito.anyString())).thenReturn(Optional.empty());
        Mockito.when(mentorService.getMentorById(studentDto.getMentorId())).thenReturn(null);

        ResourseNotFound exception = assertThrows(ResourseNotFound.class,
                () -> studentService.addStudent(studentDto));
        assertEquals("Mentor with mentor Id: "+studentDto.getMentorId()+" not found", exception.getMessage());
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

        String mentorId = "12f00760-d63c-48e0-9739-589ecabb6e05";
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

        Mockito.when(studentRepository.findAllByMentorId(mentorId)).thenReturn(students);

        MentorDto mentorDto = MentorDto.builder()
                .mentorName("Kishan")
                .contactNo("7845921569")
                .emailId("kishankulkarni1502@gmail.com")
                .designation("Professor").build();

        Mockito.when(mentorService.getMentorById(mentorId)).thenReturn(mentorDto);

        Mentor mentor = studentService.getAllStudentsByMentorId(mentorId);
        assertNotNull(mentor);
        assertEquals(2, mentor.getStudentDtos().size());
    }

    @Test
    void testUpdateAllStudents() {

        String mentorId = "12f00760-d63c-48e0-9739-589ecabb6e05";
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

        Mockito.when(studentRepository.findAllByMentorId(mentorId)).thenReturn(students);

        ResponseDto responseDto = studentService.updateAllStudents(mentorId);
        assertNotNull(responseDto);
        assertEquals("Students updated successfully", responseDto.getResponseMessage());
    }

    @Test
    void testGetAllStudentsByIds_oneStudent() {

        List<Student> students = new ArrayList<>();
        Student student = new Student();
        student.setStudentId("b54c5166-d94f-484f-9748-0a361726ce3b");
        student.setFirstName("Karthik");
        student.setLastName("kulkarni");
        student.setEmailId("kartikkulkarni1411@gmail.com");
        student.setMentorId("1b5654b7-2fab-4990-a253-1cb071872793");
        students.add(student);
        List<String> studentsIds = List.of("b54c5166-d94f-484f-9748-0a361726ce3b");

        Mockito.when(studentRepository.findAllById(studentsIds)).thenReturn(students);

        List<StudentDto> result = studentService.getAllStudentsById(studentsIds);
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void testGetAllStudentsByIds_oneStudentId_Invalid() {

        List<Student> students = new ArrayList<>();
        Student student = new Student();
        student.setStudentId("b54c5166-d94f-484f-9748-0a361726ce3b");
        student.setFirstName("Karthik");
        student.setLastName("kulkarni");
        student.setEmailId("kartikkulkarni1411@gmail.com");
        student.setMentorId("1b5654b7-2fab-4990-a253-1cb071872793");
        students.add(student);
        List<String> studentsIds = List.of("b54c5166-d94f-484f-9748-0a361726ce3b", "1b5654b7-2fab-4990-a253-1cb071872793");

        Mockito.when(studentRepository.findAllById(studentsIds)).thenReturn(students);

        List<StudentDto> result = studentService.getAllStudentsById(studentsIds);
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void testGetStudentCourseDetails() {

        String courseId = "e34395fd-8e66-4ab7-be23-717230903ad9";
        ViewCourse viewCourse = ViewCourse.builder()
                .name("C Basics")
                .credits(5)
                .studentsCredits(Map.of("baf7a5cc-7d01-431c-8c4e-086ea64ef822", 4, "e34395fd-8e66-4ab7-be23-717230903ad9", 5))
                .build();

        Mockito.when(courseService.getCoursesByStudentId(courseId)).thenReturn(viewCourse);

        List<Student> students = new ArrayList<>();
        Student student = new Student();
        student.setStudentId("baf7a5cc-7d01-431c-8c4e-086ea64ef822");
        student.setFirstName("Karthik");
        student.setLastName("kulkarni");
        student.setEmailId("kartikkulkarni1411@gmail.com");
        students.add(student);
        student = new Student();
        student.setStudentId("e34395fd-8e66-4ab7-be23-717230903ad9");
        student.setFirstName("Kishan");
        student.setLastName("kulkarni");
        student.setEmailId("kulkarnikishan1502@gmail.com");
        students.add(student);

        Set<String> studentIds = viewCourse.getStudentsCredits().keySet();
        Mockito.when(studentRepository.findAllById(studentIds)).thenReturn(students);

        StudentCourse course = studentService.getStudentCourseDetails(courseId);
        assertNotNull(course);
        assertEquals(5, course.getCredits());
        assertEquals(2, course.getStudents().size());
    }
}
