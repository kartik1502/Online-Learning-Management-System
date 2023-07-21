package org.training.courseservice.service.implementation;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.training.courseservice.Repository.CourseRepository;
import org.training.courseservice.Repository.CourseStudentRepository;
import org.training.courseservice.dto.CourseDto;
import org.training.courseservice.dto.ResponseDto;
import org.training.courseservice.dto.ViewCourse;
import org.training.courseservice.entity.Course;
import org.training.courseservice.entity.CourseStudent;
import org.training.courseservice.exception.ResourceConflict;
import org.training.courseservice.exception.ResourceNotFound;
import org.training.courseservice.external.MentorService;
import org.training.courseservice.service.CourseService;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private MentorService mentorService;

    @Autowired
    private CourseStudentRepository courseStudentRepository;

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

    @Override
    public ResponseDto updateCourse(String courseId, CourseDto courseDto) {

        courseRepository.findById(courseId).ifPresentOrElse(
                course -> {
                    BeanUtils.copyProperties(courseDto, course);
                    courseRepository.save(course); },
                () -> { throw new ResourceNotFound("Course with course Id: "+courseId+" not found on the server"); }
        );
        return new ResponseDto("200", "Course updated successfully");
    }

    @Override
    public CourseDto getCourseById(String courseId) {

        return courseRepository.findById(courseId).map(course -> {
            CourseDto courseDto = new CourseDto();
            BeanUtils.copyProperties(course, courseDto, "courseId");
            return courseDto;
        }).orElseThrow(() -> new ResourceNotFound("Course with courseId: "+courseId+" not found on the server"));
    }

    @Override
    public List<CourseDto> getAllCourses() {

        return courseRepository.findAll().stream().map(course -> {
            CourseDto courseDto = new CourseDto();
            BeanUtils.copyProperties(course, courseDto, "courseId");
            return courseDto;
        }).collect(Collectors.toList());
    }

    @Override
    public ViewCourse getStudentCourseInfo(String courseId) {

        List<CourseStudent> courseStudents = courseStudentRepository.findAllByCourseId(courseId);
        Course course = courseRepository.findById(courseId).orElseThrow(
                () ->new ResourceNotFound("Course with courseId: "+courseId+" not found on the server"));

        return ViewCourse.builder()
                .name(course.getName())
                .credits(course.getCredits())
                .studentsCredits(courseStudents.stream().collect(Collectors.toMap(
                        CourseStudent::getStudentId, CourseStudent::getCreditsAwarded
                )))
                .build();
    }
}
