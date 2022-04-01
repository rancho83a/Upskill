package com.example.coursesservice.model.binding;

import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class CourseEditBindingModel {
    private String id;
    private String name;
    private BigDecimal price;
    private String description;
    private String videoUrl;
    private File imageUrl;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<CategoryNameBindingModel> categories;
    private List<LanguageNameBindingModel> languages;
    private List<LectureEditBindingModel> lectures;
    private String lector;
    private Integer duration;
    private String skills;
    private String lectorDescription;

    public CourseEditBindingModel() {
    }

    public File getImageUrl() {
        return imageUrl;
    }

    public CourseEditBindingModel setImageUrl(File imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public String getId() {
        return id;
    }

    public CourseEditBindingModel setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public CourseEditBindingModel setName(String name) {
        this.name = name;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public CourseEditBindingModel setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public CourseEditBindingModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public CourseEditBindingModel setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
        return this;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public CourseEditBindingModel setStartDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public CourseEditBindingModel setEndDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    public List<CategoryNameBindingModel> getCategories() {
        return categories;
    }

    public CourseEditBindingModel setCategories(List<CategoryNameBindingModel> categories) {
        this.categories = categories;
        return this;
    }

    public List<LanguageNameBindingModel> getLanguages() {
        return languages;
    }

    public CourseEditBindingModel setLanguages(List<LanguageNameBindingModel> languages) {
        this.languages = languages;
        return this;
    }

    public List<LectureEditBindingModel> getLectures() {
        return lectures;
    }

    public CourseEditBindingModel setLectures(List<LectureEditBindingModel> lectures) {
        this.lectures = lectures;
        return this;
    }

    public String getLector() {
        return lector;
    }

    public CourseEditBindingModel setLector(String lector) {
        this.lector = lector;
        return this;
    }

    public Integer getDuration() {
        return duration;
    }

    public CourseEditBindingModel setDuration(Integer duration) {
        this.duration = duration;
        return this;
    }

    public String getSkills() {
        return skills;
    }

    public CourseEditBindingModel setSkills(String skills) {
        this.skills = skills;
        return this;
    }

    public String getLectorDescription() {
        return lectorDescription;
    }

    public CourseEditBindingModel setLectorDescription(String lectorDescription) {
        this.lectorDescription = lectorDescription;
        return this;
    }
}
