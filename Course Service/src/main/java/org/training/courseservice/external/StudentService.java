package org.training.courseservice.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.training.courseservice.config.FeignClientConfiguration;
import org.training.courseservice.entity.dto.StudentDto;

@FeignClient(name = "STUDENT-SERVICE", configuration = FeignClientConfiguration.class)
public interface StudentService {


    @GetMapping("/students/{studentId}")
    StudentDto getStudentById(@PathVariable String studentId);
}
