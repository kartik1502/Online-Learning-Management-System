package org.training.studentservice.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.training.studentservice.dto.Mentor;
import org.training.studentservice.dto.MentorDto;

import java.util.List;

@FeignClient(name = "MENTOR-SERVICE")
public interface MentorService {

    @GetMapping("/mentors/{mentorId}")
    MentorDto getAllMentorsByIds(@PathVariable String mentorId);
}
