package mk.ukim.finki.wp.lab.service;

import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.Student;

import java.util.List;

public interface CourseService {
    List<Student> listStudentsByCourse(Long courseId);

    Course addStudentInCourse(String username, Long courseId);

    List<Course> findAll();

    Course findById(Long courseId);

    Course save(String name, String description, Long teacherId);

    Course edit(Long courseId, String name, String description, Long teacherId);

    void delete(Long courseId);
}
