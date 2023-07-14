package org.training.courseservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseDto {

    @NotNull
    @Pattern(regexp = "[a-zA-Z0-9 ]+", message = "Only letters and numbers are allowed")
    private String name;

    @Min(2)
    @Max(10)
    private int credits;

    private String mentorId;
}
