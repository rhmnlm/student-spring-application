package com.rhmnlm.studentapplication.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="course")
public class Course implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "COURSE_SEQ")
    @SequenceGenerator(name = "COURSE_SEQ", sequenceName = "SEQUENCE_COURSE",initialValue = 5, allocationSize = 1)
    private Integer id;
    private String name;
    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            },
            mappedBy = "courseRegistered")
//    @JsonBackReference
    @JsonIgnore
    private Set<Student> registeredStudent;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        int idx = -1;
        if (id != null) {
            sb.append(++idx > 0 ? ", " : "").append("id=").append(id);
        }
        if (name != null) {
            sb.append(++idx > 0 ? ", " : "").append("name=").append(name);
        }
        if (name != null) {
            sb.append(++idx > 0 ? ", " : "").append("name=").append(name);
        }
        return sb.toString();
    }
}
