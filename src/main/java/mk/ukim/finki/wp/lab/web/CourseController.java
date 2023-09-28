package mk.ukim.finki.wp.lab.web;

import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.service.CourseService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String getCourses(Model model) {
        List<Course> courses = courseService.findAll();
        model.addAttribute("courses", courses);
        return "listCourses";
    }

    @PostMapping({"", "/listCourses"})
    public String addIdToSes(HttpServletRequest req, @RequestParam Long courseId) {
        req.getSession().setAttribute("course", courseId);
        return "redirect:/students";
    }
}
