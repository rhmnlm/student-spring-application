package com.rhmnlm.studentapplication.service;

import com.rhmnlm.studentapplication.model.Course;
import com.rhmnlm.studentapplication.model.Student;
import com.rhmnlm.studentapplication.repository.CourseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.NoSuchElementException;

@Service
public class CourseService implements ICourse{
    CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository){
        this.courseRepository = courseRepository;
    }

    public Page<Course> getAllCourse(Pageable pageable){
        Page<Course> result = courseRepository.findAll(pageable);
        return result;
    }

    public Course getCourseById(Integer id){

        return courseRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException(id.toString()));
    }

    public Course addOrUpdateCourse(Course requestCourse){
        Course responseCourse = new Course();
        if(null==requestCourse.getId()){
            fillProduct(responseCourse,requestCourse);
        }
        else{
            responseCourse = courseRepository.findById(requestCourse.getId())
                    .orElseThrow(() -> new NoSuchElementException("No student found for this id :: " + requestCourse.getId()));
            fillProduct(responseCourse, requestCourse);
            responseCourse.setId(responseCourse.getId());
        }

        return courseRepository.save(responseCourse);
    }

    public void deleteCourse(Course requestCourse){
        courseRepository.deleteById(requestCourse.getId());
    }

    void fillProduct(Course responseCourse, Course requestCourse){
        responseCourse.setName(requestCourse.getName());
    }
}
