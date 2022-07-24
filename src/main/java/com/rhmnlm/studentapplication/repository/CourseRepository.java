package com.rhmnlm.studentapplication.repository;

import com.rhmnlm.studentapplication.model.Course;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CourseRepository extends PagingAndSortingRepository<Course, Integer> {
}
