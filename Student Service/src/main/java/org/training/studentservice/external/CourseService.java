package org.training.studentservice.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.training.studentservice.config.FeignClientConfiguration;
import org.training.studentservice.dto.Course;
import org.training.studentservice.dto.ViewCourse;

@FeignClient(name = "COURSE-SERVICE", configuration = FeignClientConfiguration.class)
public interface CourseService {

    @GetMapping("/courses/{courseId}")
    Course getCourseById(@PathVariable String courseId);

    @GetMapping("/courses/info/{courseId}")
    ViewCourse getCoursesByStudentId(@PathVariable String courseId);
}