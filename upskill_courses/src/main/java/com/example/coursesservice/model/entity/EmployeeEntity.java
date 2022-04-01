package com.example.coursesservice.model.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name= "employees")
public class EmployeeEntity extends BaseEntity{

    private String email;
    private String fullName;
    private List<CourseEntity> courses;

    public EmployeeEntity() {
    }

    public EmployeeEntity(String email, String fullName, List<CourseEntity> courses) {
        this.email = email;
        this.fullName = fullName;
        this.courses = courses;
    }

    public String getEmail() {
        return email;
    }

    public EmployeeEntity setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getFullName() {
        return fullName;
    }

    public EmployeeEntity setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    @ManyToMany
    public List<CourseEntity> getCourses() {
        return courses;
    }

    public EmployeeEntity setCourses(List<CourseEntity> courses) {
        this.courses = courses;
        return this;
    }
}
