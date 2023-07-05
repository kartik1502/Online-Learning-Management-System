package org.training.mentorservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MentorDto {

    @NotNull
    @Pattern(regexp = "[a-zA-Z ]+", message = "Name should contain only alphabets")
    private String mentorName;

    @Email
    private String emailId;

    @NotNull
    @Pattern(regexp = "[6789][0-9]{9}", message = "Contact number should be of correct format")
    private String contactNo;

    @NotNull
    @Pattern(regexp = "[a-zA-Z ]+", message = "Designation should contain only aplhabets")
    private String designation;
}

