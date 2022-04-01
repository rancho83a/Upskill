package com.example.demo.model.binding;

import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class UserProfileEditBindingModel implements Serializable {

    private String fullName;
    private MultipartFile picture;
    private String summary;

    public UserProfileEditBindingModel() {
    }

    @Length(min = 3, message = "Please enter minimum 3 characters")
    @NotNull(message = "Please provide information")
    public String getFullName() {
        return fullName;
    }


    public UserProfileEditBindingModel setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    @Length(min = 3, message = "Please enter minimum 3 characters")
    @NotNull(message = "Please provide information")
    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

//    @NotNull(message = "Please provide information")
    public MultipartFile getPicture() {
        return picture;
    }

    public UserProfileEditBindingModel setPicture(MultipartFile picture) {
        this.picture = picture;
        return this;
    }
}
