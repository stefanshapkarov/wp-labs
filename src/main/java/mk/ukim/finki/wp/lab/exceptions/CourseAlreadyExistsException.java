package mk.ukim.finki.wp.lab.exceptions;

public class CourseAlreadyExistsException extends RuntimeException {
    public CourseAlreadyExistsException(String name) {
        super(String.format("Course with name: %s already exists!", name));
    }
}
