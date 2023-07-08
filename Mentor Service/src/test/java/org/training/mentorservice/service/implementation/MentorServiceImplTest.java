package org.training.mentorservice.service.implementation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.BeanUtils;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.training.mentorservice.dto.MentorDto;
import org.training.mentorservice.dto.ResponseDto;
import org.training.mentorservice.entity.Mentor;
import org.training.mentorservice.exception.ResourceConflictException;
import org.training.mentorservice.exception.ResourceNotFound;
import org.training.mentorservice.external.StudentService;
import org.training.mentorservice.repository.MentorRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
public class MentorServiceImplTest {

    @InjectMocks
    private MentorServiceImpl mentorService;

    @Mock
    private MentorRepository mentorRepository;

    @Mock
    private StudentService studentService;

    @Test
    void testAddMentor_MentorPresent_SameEmailIdAndContactNo() {

        MentorDto mentorDto = MentorDto.builder()
                .mentorName("Karthik")
                .emailId("kartikkulkarni1411@gmail.com")
                .contactNo("6361921186")
                .build();

        Mentor mentor = new Mentor();
        BeanUtils.copyProperties(mentorDto, mentor);

        Mockito.when(mentorRepository.findMentorByContactNoOrEmailId(mentorDto.getContactNo(), mentorDto.getEmailId())).thenReturn(Optional.of(mentor));

        ResourceConflictException exception = assertThrows(ResourceConflictException.class,
                () -> mentorService.addMentor(mentorDto));
        assertEquals("Mentor with the same emailId and contact number exists", exception.getMessage());
    }

    @Test
    void testAddMentor_MentorPresent_SameContactNo() {

        MentorDto mentorDto = MentorDto.builder()
                .mentorName("Karthik")
                .emailId("kartikkulkarni1411@gmail.com")
                .contactNo("6361921186")
                .build();

        Mentor mentor = new Mentor();
        BeanUtils.copyProperties(mentorDto, mentor);
        mentor.setEmailId("kartikkulkarni1234@gmail.com");

        Mockito.when(mentorRepository.findMentorByContactNoOrEmailId(mentorDto.getContactNo(), mentorDto.getEmailId())).thenReturn(Optional.of(mentor));

        ResourceConflictException exception = assertThrows(ResourceConflictException.class,
                () -> mentorService.addMentor(mentorDto));
        assertEquals("Mentor with the same contact number already exists", exception.getMessage());
    }

    @Test
    void testAddMentor_MentorPresnt_SameEmailId() {

        MentorDto mentorDto = MentorDto.builder()
                .mentorName("Karthik")
                .emailId("kartikkulkarni1411@gmail.com")
                .contactNo("6361921186")
                .build();

        Mentor mentor = new Mentor();
        BeanUtils.copyProperties(mentorDto, mentor);
        mentor.setContactNo("6361921145");

        Mockito.when(mentorRepository.findMentorByContactNoOrEmailId(mentorDto.getContactNo(), mentorDto.getEmailId())).thenReturn(Optional.of(mentor));

        ResourceConflictException exception = assertThrows(ResourceConflictException.class,
                () -> mentorService.addMentor(mentorDto));
        assertEquals("Mentor with the same emailId already exists", exception.getMessage());
    }

    @Test
    void testAddMentor_Success() {

        MentorDto mentorDto = MentorDto.builder()
                .mentorName("Karthik")
                .emailId("kartikkulkarni1411@gmail.com")
                .contactNo("6361921186")
                .build();

        Mockito.when(mentorRepository.findMentorByContactNoOrEmailId(mentorDto.getContactNo(), mentorDto.getEmailId())).thenReturn(Optional.empty());

        ResponseDto responseDto = mentorService.addMentor(mentorDto);
        assertNotNull(responseDto);
        assertEquals("Mentor added Successfully", responseDto.getResponseMessage());
    }

    @Test
    void testGetMentorById_MentorNotFound() {

        String mentorId = "eae4d3f8-6a27-4f58-a807-4d18f9dc6dfe";
        Mockito.when(mentorRepository.findById(mentorId)).thenReturn(Optional.empty());

        ResourceNotFound exception = assertThrows(ResourceNotFound.class,
                () -> mentorService.getMentorById(mentorId));
        assertEquals("Mentor with mentor Id: "+mentorId+ " not found", exception.getMessage());
    }

    @Test
    void testGetMentorById_Success() {

        String mentorId = "eae4d3f8-6a27-4f58-a807-4d18f9dc6dfe";
        Mentor mentor = Mentor.builder()
                .mentorId("eae4d3f8-6a27-4f58-a807-4d18f9dc6dfe")
                .mentorName("Karthik")
                .emailId("kartikkulkarni1411@gmail.com")
                .contactNo("6361921186")
                .build();

        Mockito.when(mentorRepository.findById(mentorId)).thenReturn(Optional.of(mentor));

        MentorDto mentorDto = mentorService.getMentorById(mentorId);
        assertNotNull(mentorDto);
        assertEquals("Karthik", mentorDto.getMentorName());
    }

    @Test
    void testGetAllMentors_withMentors() {

        List<Mentor> mentors = new ArrayList<>();
        Mentor mentor = new Mentor();
        mentor.setMentorName("Karthik Kulkarni");
        mentor.setContactNo("6361921186");
        mentor.setEmailId("kartikkulkarni1411@gmail.com");
        mentors.add(mentor);
        mentor = new Mentor();
        mentor.setMentorName("Kishan Kulkarni");
        mentor.setContactNo("6361921187");
        mentor.setEmailId("kishankulkarni1411@gmail.com");
        mentors.add(mentor);

        Mockito.when(mentorRepository.findAll()).thenReturn(mentors);

        List<MentorDto> result = mentorService.getAllMentors();

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void testGetAllMentors_EmptyList() {

        List<Mentor> mentors = new ArrayList<>();

        Mockito.when(mentorRepository.findAll()).thenReturn(mentors);
        List<MentorDto> result = mentorService.getAllMentors();
        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test
    void testDeleteMentor_MentorNotFound() {

        String mentorId = "3766aadf-b55f-45b1-9d0b-61304007cc68";

        Mockito.when(mentorRepository.findById(mentorId)).thenReturn(Optional.empty());

        ResourceNotFound exception = assertThrows(ResourceNotFound.class,
                () -> mentorService.deleteMentor(mentorId));
        assertEquals("Mentor with mentor Id: " + mentorId + " not found", exception.getMessage());
    }

    @Test
    void testDeleteMentor_Success() {

        String mentorId = "3766aadf-b55f-45b1-9d0b-61304007cc68";
        Mentor mentor = Mentor.builder()
                .mentorId("3766aadf-b55f-45b1-9d0b-61304007cc68")
                .mentorName("Karthik")
                .emailId("kartikkulkarni1411@gmail.com")
                .contactNo("6361921186")
                .build();

        Mockito.when(mentorRepository.findById(mentorId)).thenReturn(Optional.of(mentor));

        ResponseDto studentResponse = Mockito.mock(ResponseDto.class);
        Mockito.when(studentService.updateStudents(mentorId)).thenReturn(studentResponse);

        ResponseDto responseDto = mentorService.deleteMentor(mentorId);
        assertNotNull(responseDto);
        assertEquals("Mentor Deleted Successfully", responseDto.getResponseMessage());
    }

    @Test
    void testUpdateMentor_MentorNotFound() {

        String mentorId = "12f00760-d63c-48e0-9739-589ecabb6e05";
        MentorDto mentorDto = Mockito.mock(MentorDto.class);

        Mockito.when(mentorRepository.findById(mentorId)).thenReturn(Optional.empty());

        ResourceNotFound exception = assertThrows(ResourceNotFound.class,
                () -> mentorService.updateMentor(mentorId, mentorDto));
        assertEquals("Mentor with mentor Id: " + mentorId + " not found", exception.getMessage());
    }

    @Test
    void testUpdateMentor_Success() {

        String mentorId = "12f00760-d63c-48e0-9739-589ecabb6e05";
        Mentor mentor = Mentor.builder()
                .mentorId("12f00760-d63c-48e0-9739-589ecabb6e05")
                .mentorName("Karthik")
                .emailId("kartikkulkarni1411@gmail.com")
                .contactNo("6361921186")
                .build();

        Mockito.when(mentorRepository.findById(mentorId)).thenReturn(Optional.of(mentor));

        MentorDto mentorDto = Mockito.mock(MentorDto.class);
        ResponseDto responseDto = mentorService.updateMentor(mentorId, mentorDto);
        assertNotNull(responseDto);
        assertEquals("Mentor Updated Successfully", responseDto.getResponseMessage());
    }

    @Test
    void testGetAllMentorsById() {

        List<Mentor> mentors = new ArrayList<>();
        Mentor mentor = new Mentor();
        mentor.setMentorId("eae4d3f8-6a27-4f58-a807-4d18f9dc6dfe");
        mentor.setMentorName("Karthik Kulkarni");
        mentor.setContactNo("6361921186");
        mentor.setEmailId("kartikkulkarni1411@gmail.com");
        mentors.add(mentor);
        mentor = new Mentor();
        mentor.setMentorId("12f00760-d63c-48e0-9739-589ecabb6e05");
        mentor.setMentorName("Kishan Kulkarni");
        mentor.setContactNo("6361921187");
        mentor.setEmailId("kishankulkarni1411@gmail.com");
        mentors.add(mentor);
        List<String> mentorIds = List.of("eae4d3f8-6a27-4f58-a807-4d18f9dc6dfe", "12f00760-d63c-48e0-9739-589ecabb6e05");

        Mockito.when(mentorRepository.findAllById(mentorIds)).thenReturn(mentors);

        List<Mentor> result = mentorService.getAllMentorsById(mentorIds);
        assertNotNull(result);
        assertEquals(2, result.size());
    }
}
