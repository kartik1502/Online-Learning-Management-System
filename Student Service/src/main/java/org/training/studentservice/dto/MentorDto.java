package org.training.studentservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MentorDto {

    private String mentorName;

    private String emailId;

    private String contactNo;

    private String designation;
}
