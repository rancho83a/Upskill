package com.example.coursesservice.model.view;

import java.io.Serializable;

public class EmployeesByCompanyOwnerView implements Serializable {

    private String fullName;
    private String email;


    public EmployeesByCompanyOwnerView() {
    }

    public String getFullName() {
        return fullName;
    }

    public EmployeesByCompanyOwnerView setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public EmployeesByCompanyOwnerView setEmail(String email) {
        this.email = email;
        return this;
    }
}
