package org.training.studentservice.dto;

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
public class StudentDto {

    @NotNull
    @Pattern(regexp = "^[a-zA-Z]+$", message = "First name should contain only alphabets")
    private String firstName;

    @NotNull
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Last name should contain only alphabets")
    private String lastName;

    @NotNull
    @Email
    private String emailId;

    @NotNull
    @Pattern(regexp = "[6789][0-9]{9}", message = "Contact number should contain only 10 digits and should be a valid number")
    private String contactNo;
}
