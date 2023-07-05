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
import org.training.mentorservice.repository.MentorRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
public class MentorServiceImplTest {

    @InjectMocks
    private MentorServiceImpl mentorService;

    @Mock
    private MentorRepository mentorRepository;

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
}
