package org.training.mentorservice.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.training.mentorservice.dto.MentorDto;
import org.training.mentorservice.dto.ResponseDto;
import org.training.mentorservice.service.MentorService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
public class MentorControllerTest {

    @InjectMocks
    private MentorController mentorController;

    @Mock
    private MentorService mentorService;

    @Test
    void testAddMentor() {

        MentorDto mentorDto = MentorDto.builder()
                .mentorName("Karthik")
                .emailId("kartikkulkarni1411@gmail.com")
                .contactNo("6361921186")
                .build();

        ResponseDto responseDto = new ResponseDto("200", "Mentor Added Successfully");
        Mockito.when(mentorService.addMentor(mentorDto)).thenReturn(responseDto);

        ResponseEntity<ResponseDto> response = mentorController.addMentor(mentorDto);
        assertNotNull(response);
        assertEquals("Mentor Added Successfully", response.getBody().getResponseMessage());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void testGetMentorById() {

        String mentorId = "eae4d3f8-6a27-4f58-a807-4d18f9dc6dfe";
        MentorDto mentor = MentorDto.builder()
                .mentorName("Karthik")
                .emailId("kartikkulkarni1411@gmail.com")
                .contactNo("6361921186")
                .build();

        Mockito.when(mentorService.getMentorById(mentorId)).thenReturn(mentor);

        ResponseEntity<MentorDto> response = mentorController.getMentorById(mentorId);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("kartikkulkarni1411@gmail.com", response.getBody().getEmailId());
    }

    @Test
    void testGetAllMentors() {

        List<MentorDto> mentors = new ArrayList<>();
        MentorDto mentor = new MentorDto();
        mentor.setMentorName("Karthik Kulkarni");
        mentor.setContactNo("6361921186");
        mentor.setEmailId("kartikkulkarni1411@gmail.com");
        mentors.add(mentor);

        Mockito.when(mentorService.getAllMentors()).thenReturn(mentors);

        ResponseEntity<List<MentorDto>> result = mentorController.getAllMentors();
        assertNotNull(result);
        assertEquals(1, result.getBody().size());
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }
}
