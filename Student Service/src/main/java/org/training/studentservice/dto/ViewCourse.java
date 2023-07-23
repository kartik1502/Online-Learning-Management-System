package org.training.studentservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ViewCourse {

    private String name;

    private int credits;

    private Map<String, Integer> studentsCredits;
}
