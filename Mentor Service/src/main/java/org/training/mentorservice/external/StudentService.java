package org.training.mentorservice.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.training.mentorservice.dto.ResponseDto;

@FeignClient(name = "STUDENT-SERVICE")
public interface StudentService {

    @PutMapping("/students/mentors/{mentorId}")
    ResponseDto updateStudents(@PathVariable String mentorId);
}
