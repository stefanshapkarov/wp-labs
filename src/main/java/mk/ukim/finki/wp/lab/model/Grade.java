package mk.ukim.finki.wp.lab.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
public class Grade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    Character grade;

    @ManyToOne
    Student student;

    @ManyToOne
    Course course;

    LocalDateTime timestamp;
}
