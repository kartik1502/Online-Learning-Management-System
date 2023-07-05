package org.training.mentorservice.entity;

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
public class Mentor {

    @Id
    private String mentorId;

    private String mentorName;

    private String emailId;

    private String contactNo;

    private String designation;
}
