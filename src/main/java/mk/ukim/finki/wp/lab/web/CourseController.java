package mk.ukim.finki.wp.lab.web;

import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.service.CourseService;
import mk.ukim.finki.wp.lab.service.TeacherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = {"/courses"})
public class CourseController {
    CourseService courseService;
    TeacherService teacherService;

    public CourseController(CourseService courseService, TeacherService teacherService) {
        this.courseService = courseService;
        this.teacherService = teacherService;
    }

    @GetMapping({"", "/listCourses"})
    public String getCoursesPage(@RequestParam(required = false) String error, Model model) {
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        List<Course> courses = courseService.findAll();
        model.addAttribute("courses",
                courses.stream().sorted(Comparator.comparing(Course::getName)).collect(Collectors.toList()));
        return "listCourses";
    }

    @PostMapping({"", "/listCourses"})
    public String addIdToSes(HttpServletRequest req, @RequestParam(required = false) Long courseId) {
        if (courseId != null) {
            req.getSession().setAttribute("course", courseId);
        }
        return "redirect:/students";
    }

    @PostMapping("/add")
    public String saveCourse(@RequestParam(required = false) Long id,
                             @RequestParam String name,
                             @RequestParam String description,
                             @RequestParam Long teacherId) {
        if (id != null) {
            try {
                courseService.edit(id, name, description, teacherId);
                return "redirect:/courses";
            } catch (RuntimeException e) {
                return "redirect:/courses?error=" + e.getMessage();
            }
        }

        try {
            courseService.save(name, description, teacherId);
        } catch (RuntimeException e) {
            return "redirect:/courses?error=" + e.getMessage();
        }

        return "redirect:/courses";
    }

    @GetMapping("delete/{id}")
    public String deleteCourse(@PathVariable Long id) {
        try {
            courseService.delete(id);
        } catch (RuntimeException e) {
            return "redirect:/courses?error=" + e.getMessage();
        }
        return "redirect:/courses";
    }

    @GetMapping("/add-form")
    public String getAddCoursePage(Model model) {
        model.addAttribute("teachers", teacherService.findAll());
        return "add-course";
    }

    @GetMapping("/edit-form/{id}")
    public String getEditCoursePage(@PathVariable Long id, Model model) {
        model.addAttribute("teachers", teacherService.findAll());
        Course course = courseService.findById(id);
        model.addAttribute("course", course);
        return "add-course";
    }
}
