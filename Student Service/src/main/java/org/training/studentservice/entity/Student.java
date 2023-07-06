package org.training.studentservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Student {

    @Id
    private String studentId;

    private String firstName;

    private String lastName;

    private String emailId;

    private String contactNo;

    private String mentorId;
}
