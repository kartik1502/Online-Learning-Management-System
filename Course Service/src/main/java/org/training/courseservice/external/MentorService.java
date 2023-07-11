package org.training.courseservice.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.training.courseservice.entity.dto.MentorDto;

@FeignClient(name = "MENTOR-SERVICE")
public interface MentorService {

    @GetMapping("/mentors/{mentorId}")
    MentorDto getMentorById(@PathVariable String mentorId);
}
