package org.training.courseservice.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.training.courseservice.entity.CourseStudent;

import java.util.List;

public interface CourseStudentRepository extends JpaRepository<CourseStudent, String> {

    List<CourseStudent> findAllByCourseId(String courseId);
}
