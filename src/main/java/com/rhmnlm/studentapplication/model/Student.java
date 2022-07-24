package com.rhmnlm.studentapplication.model;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="student")
public class Student implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STUDENT_SEQ")
    @SequenceGenerator(name = "STUDENT_SEQ", sequenceName = "SEQUENCE_STUDENT",initialValue = 5, allocationSize = 1)
    private Integer id;
    private String firstname;
    private String lastname;

    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
    })
    @JoinTable(
            name="registeredcourse",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    Set<Course> courseRegistered;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        int idx = -1;
        if (id != null) {
            sb.append(++idx > 0 ? ", " : "").append("id=").append(id);
        }
        if (firstname != null) {
            sb.append(++idx > 0 ? ", " : "").append("name=").append(firstname);
        }
        if (lastname != null) {
            sb.append(++idx > 0 ? ", " : "").append("name=").append(lastname);
        }
        return sb.toString();
    }
}
