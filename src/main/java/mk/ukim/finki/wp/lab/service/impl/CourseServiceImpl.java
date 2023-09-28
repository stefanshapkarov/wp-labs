package mk.ukim.finki.wp.lab.service.impl;

import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.Student;
import mk.ukim.finki.wp.lab.repository.CourseRepository;
import mk.ukim.finki.wp.lab.repository.StudentRepository;
import mk.ukim.finki.wp.lab.service.CourseService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {
    CourseRepository courseRepository;
    StudentRepository studentRepository;

    public CourseServiceImpl(CourseRepository courseRepository, StudentRepository studentRepository) {
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public List<Student> listStudentsByCourse(Long courseId) {
        Course course = courseRepository.findById(courseId).orElse(null);
        if (course != null) {
            return course.getStudents();
        }
        return new ArrayList<>();
    }

    @Override
    public Course addStudentInCourse(String username, Long courseId) {
        Course course = courseRepository.findById(courseId).orElse(null);
        Student student = null;
        if (course != null) {
            student = studentRepository.findStudentByUsername(username);
            course.getStudents().add(student);
            student.getCourses().add(course);

            courseRepository.save(course);
            studentRepository.save(student);
            return course;
        }
        return null;
    }

    @Override
    public List<Course> findAll() {
        return this.courseRepository.findAll();
    }

    @Override
    public Course findById(Long courseId) {
        return this.courseRepository.findById(courseId).orElse(null);
    }
}
