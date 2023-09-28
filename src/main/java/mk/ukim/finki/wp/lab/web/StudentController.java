package mk.ukim.finki.wp.lab.web;

import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.Grade;
import mk.ukim.finki.wp.lab.model.Student;
import mk.ukim.finki.wp.lab.service.CourseService;
import mk.ukim.finki.wp.lab.service.GradeService;
import mk.ukim.finki.wp.lab.service.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = {"/students"})
public class StudentController {
    StudentService studentService;
    CourseService courseService;
    GradeService gradeService;

    public StudentController(StudentService studentService, CourseService courseService, GradeService gradeService) {
        this.studentService = studentService;
        this.courseService = courseService;
        this.gradeService = gradeService;
    }

    @GetMapping({"", "/AddStudent"})
    public String getStudents(HttpServletRequest req,
                              @RequestParam(required = false) String error, Model model) {
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
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
                                @RequestParam String surname) {
        try {
            studentService.save(username, password, name, surname);
        } catch (RuntimeException e) {
            return "redirect:/students?error=" + e.getMessage();
        }
        return "redirect:/students";
    }

    @PostMapping("/StudentEnrollmentSummary")
    public String getStudentEnrollmentSummary(HttpServletRequest req, @RequestParam String username, Model model) {
        try {
            Course course = courseService.addStudentInCourse(username,
                    (Long) req.getSession().getAttribute("course"));
            List<Student> students = course.getStudents();
            Map<Student, Grade> gradesPerStudent = new HashMap<>();
            students.forEach(s -> {
                Grade grade = gradeService.getGradeByStudentAndCourse(s, course);
                gradesPerStudent.put(s, grade);
            });

            model.addAttribute("course", course);
            model.addAttribute("students", students);
            model.addAttribute("gradesPerStudent", gradesPerStudent);
        } catch (RuntimeException e) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", e.getMessage());
        }

        return "studentsInCourse";
    }
}
