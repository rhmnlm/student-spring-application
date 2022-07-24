package com.rhmnlm.studentapplication.controller;

import com.rhmnlm.studentapplication.model.Course;
import com.rhmnlm.studentapplication.model.StringResponse;
import com.rhmnlm.studentapplication.model.Student;
import com.rhmnlm.studentapplication.service.CourseService;
import com.rhmnlm.studentapplication.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/students")
public class StudentController {

    StudentService studentService;
    CourseService courseService;

    @Autowired
    public StudentController(
            StudentService studentService,
            CourseService courseService
    ){

        this.studentService = studentService;
        this.courseService = courseService;
    }

    @GetMapping("/lists")
    public Page<Student> getStudents(
            @RequestParam(name="pageNum", required = false, defaultValue = "0") int pageNum,
            @RequestParam(name="pageSize", required = false, defaultValue = "5") int pageSize
    ){
        Pageable pageable = PageRequest.of(pageNum, pageSize,  Sort.by("id"));
        return studentService.getStudentsWithPagination(pageable);
    }

    @GetMapping("/{id}")
    public Student getStudentById(
            @PathVariable Integer id
    ){
        return studentService.getStudentById(id);
    }

    @GetMapping("/course/{id}")
    public Page<Student> getStudentByCourseId(
            @RequestParam(name="pageNum", required = false, defaultValue = "0") int pageNum,
            @RequestParam(name="pageSize", required = false, defaultValue = "5") int pageSize,
            @PathVariable Integer id
    ){
        Pageable pageable = PageRequest.of(pageNum, pageSize,  Sort.by("id"));
        return studentService.findStudentByCourseId(id, pageable);
    }

    @PostMapping("/add")
    public Student addNewStudent(
            @RequestBody Student requestStudent
    ){
        return studentService.addOrUpdateStudent(requestStudent);
    }

    //register course for a student 1 by 1
    @PostMapping("/{studentId}/courses")
    public Student registerCourse(
            @PathVariable(value = "studentId") Integer studentId,
            @RequestBody Course courseRequest
    ) {
        Student responseStudent = studentService.getStudentById(studentId);
        Set<Course> course = responseStudent.getCourseRegistered();
        Course courseToAdd = courseService.getCourseById(courseRequest.getId());
        if(course.contains(courseToAdd)){
            throw new Error("Student already registered for the course");
        }

        else{
            course.add(courseToAdd);
            responseStudent.setCourseRegistered(course);
            responseStudent = studentService.addOrUpdateStudent(responseStudent);
        }
        return responseStudent;
    }

    @PostMapping("/{studentId}/courses/drop")
    public Student removeCourse(
            @PathVariable(value = "studentId") Integer studentId,
            @RequestBody Course courseRequest
    ){
        Student responseStudent = studentService.getStudentById(studentId);
        Set<Course> course = responseStudent.getCourseRegistered();
        Course courseToRemove = courseService.getCourseById(courseRequest.getId());
        if(course.contains(courseToRemove)){
            course.remove(courseToRemove);
            responseStudent.setCourseRegistered(course);
            responseStudent = studentService.addOrUpdateStudent(responseStudent);
        }
        else{
            throw new Error( courseToRemove.getName() + " course has either be removed or unavailable.");
        }
        return responseStudent;
    }

    @PutMapping("/update/{id}")
    public Student updateStudent(
            @RequestBody Student student,
            @PathVariable String id
    ){
        Student requestStudent = student;
        requestStudent.setId(Integer.valueOf(id));
        return studentService.addOrUpdateStudent(requestStudent);
    }


    @DeleteMapping("/delete")
    public StringResponse deleteProduct(
            @RequestBody Student requestStudent
    ){
        studentService.deleteStudent(requestStudent);
        StringResponse response = new StringResponse();
        response.setMessage("Student succesfully removed.");
        return response;
    }

}
