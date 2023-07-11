package org.training.courseservice.service.implementation;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.training.courseservice.Repository.CourseRepository;
import org.training.courseservice.entity.Course;
import org.training.courseservice.entity.dto.CourseDto;
import org.training.courseservice.entity.dto.ResponseDto;
import org.training.courseservice.exception.ResourceConflict;
import org.training.courseservice.external.MentorService;
import org.training.courseservice.service.CourseService;

import java.util.UUID;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private MentorService mentorService;

    @Value("${spring.application.responseCode}")
    private String responseCode;

    @Override
    public ResponseDto addCourse(CourseDto courseDto) {

        courseRepository.findCourseByNameAndMentorId(courseDto.getName(), courseDto.getMentorId())
                .ifPresent(course -> { throw new ResourceConflict("Course with same name and mentor is already present");
                });
        mentorService.getMentorById(courseDto.getMentorId());
        Course course = new Course();
        BeanUtils.copyProperties(courseDto, course);
        course.setCourseId(UUID.randomUUID().toString());
        courseRepository.save(course);
        return new ResponseDto(responseCode, "Course added successfully");
    }
}
