package mk.ukim.finki.wp.lab.web;

import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.service.CourseService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping(value = {"/courses"})
public class CourseController {
    CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping({"", "/listCourses"})
    public String getCoursesPage(@RequestParam(required = false) String error, Model model) {
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        List<Course> courses = courseService.findAll();
        model.addAttribute("courses", courses);
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
    public String saveCourse(@RequestParam String name,
                             @RequestParam String description,
                             @RequestParam Long teacherId) {
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
}
