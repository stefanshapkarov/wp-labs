package mk.ukim.finki.wp.lab.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String name;
    String description;
    @ManyToMany
    List<Student> students;
}
