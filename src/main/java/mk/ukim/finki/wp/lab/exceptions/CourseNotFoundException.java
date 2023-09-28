package mk.ukim.finki.wp.lab.exceptions;

public class CourseNotFoundException extends RuntimeException {
    public CourseNotFoundException(Long courseId) {
        super(String.format("Course with id: %d not found!", courseId));
    }
}
