package mk.ukim.finki.wp.lab.web;

import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.Student;
import mk.ukim.finki.wp.lab.service.CourseService;
import mk.ukim.finki.wp.lab.service.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping(value = {"/students"})
public class StudentController {
    StudentService studentService;
    CourseService courseService;

    public StudentController(StudentService studentService, CourseService courseService) {
        this.studentService = studentService;
        this.courseService = courseService;
    }

    @GetMapping({"", "/AddStudent"})
    public String getStudents(HttpServletRequest req, Model model) {
        List<Student> students = studentService.listAll();
        model.addAttribute("students", students);
        model.addAttribute("course", req.getSession().getAttribute("course"));
        return "listStudents";
    }

    @PostMapping("/AddStudent")
    public String getStudentsPage(HttpServletRequest req, Model model) {
        List<Student> students = studentService.listAll();
        model.addAttribute("students", students);
        model.addAttribute("course", req.getSession().getAttribute("course"));
        return "listStudents";
    }

    @GetMapping("/CreateStudent")
    public String getCreateStudentPage() {
        return "createStudent";
    }

    @PostMapping("/CreateStudent")
    public String createStudent(@RequestParam String username,
                                @RequestParam String password,
                                @RequestParam String name,
                                @RequestParam String surname,
                                Model model) {
        studentService.save(username, password, name, surname);
        model.addAttribute("success", "A new student has been added.");
        return "redirect:/students/AddStudent";
    }

    @PostMapping("/StudentEnrollmentSummary")
    public String getStudentEnrollmentSummary(HttpServletRequest req, @RequestParam String username, Model model) {
        Course course = courseService.addStudentInCourse(username, (Long) req.getSession().getAttribute("course"));
        if (course != null) {
            model.addAttribute("courseName", course.getName());
            model.addAttribute("students", course.getStudents());
        }
        return "studentsInCourse";
    }
}
