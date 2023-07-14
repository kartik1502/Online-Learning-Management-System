package org.training.studentservice.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.training.studentservice.config.FeignClientConfiguration;

@FeignClient(name = "COURSE-SERVICE", configuration = FeignClientConfiguration.class)
public interface CourseService {


}
