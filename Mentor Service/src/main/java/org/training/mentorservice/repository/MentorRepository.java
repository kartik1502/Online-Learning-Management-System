package org.training.mentorservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.training.mentorservice.entity.Mentor;

import java.util.Optional;

public interface MentorRepository extends JpaRepository<Mentor, String> {

    Optional<Mentor> findMentorByContactNoAndEmailId(String contactNo, String emailId);
}
