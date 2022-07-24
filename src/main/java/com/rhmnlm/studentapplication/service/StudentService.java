package com.rhmnlm.studentapplication.service;

import com.rhmnlm.studentapplication.model.Student;
import com.rhmnlm.studentapplication.repository.StudentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.NoSuchElementException;

@Service
public class StudentService implements IStudent {

    StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository){
        this.studentRepository = studentRepository;
    }

    public Page<Student> getStudentsWithPagination(Pageable pageable){
        Page<Student> result = studentRepository.findAll(pageable);
        return result;
    }

    public Student getStudentById(Integer id){

        return studentRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException(id.toString()));
    }

    public Page<Student> findStudentByCourseId(Integer id, Pageable pageable){
        return studentRepository.findStudentsBycourseRegisteredId(id, pageable);
    }

    public Student addOrUpdateStudent(Student requestStudent){

        Student responseStudent = new Student();
        if(null==requestStudent.getId()){
            fillProduct(responseStudent,requestStudent);
        }
        else{
            responseStudent = studentRepository.findById(requestStudent.getId())
                    .orElseThrow(() -> new NoSuchElementException("No student found for this id :: " + requestStudent.getId()));
            fillProduct(responseStudent, requestStudent);
            responseStudent.setId(responseStudent.getId());
        }

        return studentRepository.save(responseStudent);
    }

    public void deleteStudent(Student requestStudent){
        studentRepository.deleteById(requestStudent.getId());
    }

    void fillProduct(Student responseStudent, Student requestStudent){
        responseStudent.setFirstname(requestStudent.getFirstname());
        responseStudent.setLastname(requestStudent.getLastname());
    }


}
