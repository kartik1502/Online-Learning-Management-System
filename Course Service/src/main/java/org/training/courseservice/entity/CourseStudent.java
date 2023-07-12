package org.training.courseservice.entity;

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
public class CourseStudent {

    @Id
    private String courseStudentId;

    private String studentId;

    private String courseId;

    private int creditsAwarded;
}
