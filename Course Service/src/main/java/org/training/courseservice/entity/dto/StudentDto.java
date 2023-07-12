package org.training.courseservice.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class StudentDto {

    private String firstName;

    private String lastName;

    private String emailId;

    private String contactNo;
}
