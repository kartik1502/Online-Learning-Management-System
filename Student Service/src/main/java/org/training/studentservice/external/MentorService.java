package org.training.studentservice.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.training.studentservice.config.FeignClientConfiguration;
import org.training.studentservice.dto.MentorDto;

@FeignClient(name = "MENTOR-SERVICE", configuration = FeignClientConfiguration.class)
public interface MentorService {

    @GetMapping("/mentors/{mentorId}")
    MentorDto getMentorById(@PathVariable String mentorId);


}
