package mk.ukim.finki.wp.lab.repository;

import mk.ukim.finki.wp.lab.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findStudentsByNameContainsOrSurnameContains(String text1, String text2);
    Student findStudentByUsername(String username);
}
