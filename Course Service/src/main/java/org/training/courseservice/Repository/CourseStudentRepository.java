package org.training.courseservice.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.training.courseservice.entity.CourseStudent;

import java.util.List;
import java.util.Optional;

public interface CourseStudentRepository extends JpaRepository<CourseStudent, String> {

    List<CourseStudent> findAllByCourseId(String courseId);

    Optional<CourseStudent> findByStudentIdAndCourseId(String studentId, String courseId);
}
