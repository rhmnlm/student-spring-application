package com.rhmnlm.studentapplication.repository;

import com.rhmnlm.studentapplication.model.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends PagingAndSortingRepository<Student, Integer> {
    Optional<Student> findById(Integer id);

    Page<Student> findStudentsBycourseRegisteredId(Integer id, Pageable pageable);
}
