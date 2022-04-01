package com.example.coursesservice.model.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "business_owner")
public class BusinessOwnerEntity extends BaseEntity{

    private String email;
    private List<CourseEntity> courses;
    private List<EmployeeEntity> employees;

    public BusinessOwnerEntity() {
    }

    public BusinessOwnerEntity(String email, List<CourseEntity> courses, List<EmployeeEntity> employees) {
        this.email = email;
        this.courses = courses;
        this.employees = employees;
    }

    @OneToMany
    public List<EmployeeEntity> getEmployees() {
        return employees;
    }

    public BusinessOwnerEntity setEmployees(List<EmployeeEntity> employees) {
        this.employees = employees;
        return this;
    }

    @ManyToMany(mappedBy = "businessOwners")
    public List<CourseEntity> getCourses() {
        return courses;
    }

    public BusinessOwnerEntity setCourses(List<CourseEntity> courses) {
        this.courses = courses;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public BusinessOwnerEntity setEmail(String email) {
        this.email = email;
        return this;
    }
}
