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
import org.training.mentorservice.entity.Mentor;
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

    @Test
    void testDeleteMentor() {

        String mentorId = "3766aadf-b55f-45b1-9d0b-61304007cc68";
        ResponseDto responseDto = new ResponseDto("200", "Mentor deleted Successfully");

        Mockito.when(mentorService.deleteMentor(mentorId)).thenReturn(responseDto);

        ResponseEntity<ResponseDto> response = mentorController.deleteById(mentorId);
        assertNotNull(response);
        assertEquals("Mentor deleted Successfully", response.getBody().getResponseMessage());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testUpdateMentor() {

        String mentorId = "12f00760-d63c-48e0-9739-589ecabb6e05";
        MentorDto mentorDto = Mockito.mock(MentorDto.class);
        ResponseDto responseDto = new ResponseDto("200", "Mentor Updated Successfully");

        Mockito.when(mentorService.updateMentor(mentorId, mentorDto)).thenReturn(responseDto);

        ResponseEntity<ResponseDto> response = mentorController.updateMentor(mentorId, mentorDto);
        assertNotNull(response);
        assertEquals("Mentor Updated Successfully", response.getBody().getResponseMessage());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testGetAllMentorsById() {

        List<MentorDto> mentors = new ArrayList<>();
        MentorDto mentor = new MentorDto();
        mentor.setMentorName("Karthik Kulkarni");
        mentor.setContactNo("6361921186");
        mentor.setEmailId("kartikkulkarni1411@gmail.com");
        mentors.add(mentor);
        mentor = new MentorDto();
        mentor.setMentorName("Kishan Kulkarni");
        mentor.setContactNo("6361921187");
        mentor.setEmailId("kishankulkarni1411@gmail.com");
        mentors.add(mentor);
        List<String> mentorIds = List.of("eae4d3f8-6a27-4f58-a807-4d18f9dc6dfe", "12f00760-d63c-48e0-9739-589ecabb6e05");

        Mockito.when(mentorService.getAllMentorsById(mentorIds)).thenReturn(mentors);

        ResponseEntity<List<MentorDto>> result = mentorController.getAllMentorsById(mentorIds);
        assertNotNull(result);
        assertEquals(2, result.getBody().size());
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }
}
