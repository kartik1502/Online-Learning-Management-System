package org.training.courseservice.service.implementation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.BeanUtils;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.training.courseservice.Repository.CourseRepository;
import org.training.courseservice.entity.Course;
import org.training.courseservice.entity.dto.CourseDto;
import org.training.courseservice.entity.dto.MentorDto;
import org.training.courseservice.entity.dto.ResponseDto;
import org.training.courseservice.exception.ResourceConflict;
import org.training.courseservice.external.MentorService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
public class CourseServiceImplTest {

    @InjectMocks
    private CourseServiceImpl courseService;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private MentorService mentorService;

    @Test
    void testAddCourse_SameCourseNameAndMentorId() {

        CourseDto courseDto = CourseDto.builder()
                .name("Java Core")
                .credits(5)
                .mentorId("e34395fd-8e66-4ab7-be23-717230903ad9").build();

        Course course = new Course();
        BeanUtils.copyProperties(courseDto, course);

        Mockito.when(courseRepository.findCourseByNameAndMentorId(courseDto.getName(), courseDto.getMentorId())).thenReturn(Optional.of(course));

        ResourceConflict exception = assertThrows(ResourceConflict.class,
                () -> courseService.addCourse(courseDto));
        assertEquals("Course with same name and mentor is already present", exception.getMessage());
    }

    @Test
    void testAddCourse_Success() {

        CourseDto courseDto = CourseDto.builder()
                .name("Java Core")
                .credits(5)
                .mentorId("e34395fd-8e66-4ab7-be23-717230903ad9").build();

        Mockito.when(courseRepository.findCourseByNameAndMentorId(courseDto.getName(), courseDto.getMentorId())).thenReturn(Optional.empty());

        MentorDto mentorDto = Mockito.mock(MentorDto.class);

        Mockito.when(mentorService.getMentorById(courseDto.getMentorId())).thenReturn(mentorDto);

        ResponseDto responseDto = courseService.addCourse(courseDto);
        assertNotNull(responseDto);
        assertEquals("Course added successfully", responseDto.getResponseMessage());
    }
}
