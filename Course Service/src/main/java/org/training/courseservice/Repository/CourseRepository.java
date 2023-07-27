package org.training.courseservice.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.training.courseservice.entity.Course;

import java.util.List;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, String> {

    Optional<Course> findCourseByNameAndMentorId(String name, String mentorId);

    List<Course> findByNameIsContainingIgnoreCase(String name);
}
