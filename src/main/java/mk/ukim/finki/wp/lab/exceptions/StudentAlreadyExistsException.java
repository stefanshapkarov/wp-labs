package mk.ukim.finki.wp.lab.exceptions;

public class StudentAlreadyExistsException extends RuntimeException {
    public StudentAlreadyExistsException(String username) {
        super(String.format("Student with username: %s already exists!", username));
    }
}
