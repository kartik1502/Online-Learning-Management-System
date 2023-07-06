package org.training.studentservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.training.studentservice.entity.Student;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, String> {

    Optional<Student> findStudentByEmailIdOrContactNo(String emailId, String contactNo);

    List<Student> findAllByMentorId(String mentorId);
}
