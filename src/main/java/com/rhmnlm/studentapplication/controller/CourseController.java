package com.rhmnlm.studentapplication.controller;

import com.rhmnlm.studentapplication.model.Course;
import com.rhmnlm.studentapplication.model.StringResponse;
import com.rhmnlm.studentapplication.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/course")
public class CourseController {

    CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService){
        this.courseService = courseService;
    }

    @GetMapping("/lists")
    public Page<Course> getCourses(
            @RequestParam(name="pageNum", required = false, defaultValue = "0") int pageNum,
            @RequestParam(name="pageSize", required = false, defaultValue = "5") int pageSize
    ){
        Pageable pageable = PageRequest.of(pageNum, pageSize,  Sort.by("id"));
        return courseService.getAllCourse(pageable);
    }

    @GetMapping("/{id}")
    public Course getCourseById(
            @PathVariable Integer id
    ){
        return courseService.getCourseById(id);
    }

    @PostMapping("/add")
    public Course addNewCourse(
            @RequestBody Course requestCourse
    ){
        return courseService.addOrUpdateCourse(requestCourse);
    }

    @PutMapping("/update/{id}")
    public Course updateStudent(
            @RequestBody Course course,
            @PathVariable String id
    ){
        Course requestCourse = course;
        requestCourse.setId(Integer.valueOf(id));
        return courseService.addOrUpdateCourse(requestCourse);
    }

    @DeleteMapping("/delete")
    public StringResponse deleteCourse(
            @RequestBody Course requestCourse
    ){
        courseService.deleteCourse(requestCourse);
        StringResponse response = new StringResponse();
        response.setMessage("Student succesfully removed.");
        return response;
    }

}
