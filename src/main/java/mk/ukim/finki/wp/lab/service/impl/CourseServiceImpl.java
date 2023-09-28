package mk.ukim.finki.wp.lab.service.impl;

import mk.ukim.finki.wp.lab.exceptions.CourseAlreadyExistsException;
import mk.ukim.finki.wp.lab.exceptions.CourseNotFoundException;
import mk.ukim.finki.wp.lab.exceptions.StudentAlreadyExistsException;
import mk.ukim.finki.wp.lab.exceptions.TeacherNotFoundException;
import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.Student;
import mk.ukim.finki.wp.lab.model.Teacher;
import mk.ukim.finki.wp.lab.repository.CourseRepository;
import mk.ukim.finki.wp.lab.repository.StudentRepository;
import mk.ukim.finki.wp.lab.repository.TeacherRepository;
import mk.ukim.finki.wp.lab.service.CourseService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {
    CourseRepository courseRepository;
    StudentRepository studentRepository;
    TeacherRepository teacherRepository;

    public CourseServiceImpl(CourseRepository courseRepository, StudentRepository studentRepository,
                             TeacherRepository teacherRepository) {
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
    }

    @Override
    public List<Student> listStudentsByCourse(Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseNotFoundException(courseId));

        return course.getStudents();
    }

    @Override
    public Course addStudentInCourse(String username, Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseNotFoundException(courseId));

        if (course.getStudents().stream().anyMatch(s -> s.getUsername().equals(username))) {
            throw new StudentAlreadyExistsException(username);
        }
        course.getStudents().add(studentRepository.findStudentByUsername(username));
        return courseRepository.save(course);
    }

    @Override
    public List<Course> findAll() {
        return this.courseRepository.findAll();
    }

    @Override
    public Course findById(Long courseId) {
        return this.courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseNotFoundException(courseId));
    }

    @Override
    public Course save(String name, String description, Long teacherId) {
        if (courseRepository.findAll().stream().anyMatch(c -> c.getName().equals(name))) {
            throw new CourseAlreadyExistsException(name);
        }
        return courseRepository.save(new Course(name, description,
                teacherRepository.findById(teacherId)
                        .orElseThrow(() -> new TeacherNotFoundException(teacherId))));
    }

    @Override
    public Course edit(Long courseId, String name, String description, Long teacherId) {
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new CourseNotFoundException(courseId));
        course.setName(name);
        course.setDescription(description);
        course.setTeacher(teacherRepository.findById(teacherId)
                .orElseThrow(() -> new TeacherNotFoundException(teacherId)));
        return courseRepository.save(course);
    }


    @Override
    public void delete(Long courseId) {
        courseRepository.deleteById(courseId);
    }
}
