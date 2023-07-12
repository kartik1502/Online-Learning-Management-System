package org.training.courseservice.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.training.courseservice.entity.CourseStudent;

public interface CourseStudentRepository extends JpaRepository<CourseStudent, String> {
}
