package mk.ukim.finki.wp.lab.exceptions;

public class TeacherNotFoundException extends RuntimeException {
    public TeacherNotFoundException(Long teacherId) {
        super(String.format("Teacher with id: %d not found!", teacherId));
    }
}
