package com.example.coursesservice.model.entity;

import com.example.coursesservice.model.enums.StatusNameEnum;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "courses")
public class CourseEntity extends BaseEntity {

    private String name;
    private BigDecimal price;
    private StatusNameEnum status;
    private String description;
    private String videoUrl;
    private String imageUrl;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer duration;
    private List<CategoryEntity> categories;
    private List<LanguageEntity> languages;
    private List<LectureEntity> lectures;
    private String lector;
    private String lectorDescription;
    private List<BusinessOwnerEntity> businessOwners;
//    private List<BookingEntity> bookings;
    private String skills;

    public CourseEntity() {
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public CourseEntity setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    @ManyToMany
    public List<BusinessOwnerEntity> getBusinessOwners() {
        return businessOwners;
    }

    public CourseEntity setBusinessOwners(List<BusinessOwnerEntity> businessOwners) {
        this.businessOwners = businessOwners;
        return this;
    }

    @Column(unique = true)
    public String getName() {
        return name;
    }

    public CourseEntity setName(String name) {
        this.name = name;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public CourseEntity setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public StatusNameEnum getStatus() {
        return status;
    }

    public CourseEntity setStatus(StatusNameEnum status) {
        this.status = status;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public CourseEntity setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getLector() {
        return lector;
    }

    public CourseEntity setLector(String lector) {
        this.lector = lector;
        return this;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public CourseEntity setStartDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public CourseEntity setEndDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    public Integer getDuration() {
        return duration;
    }

    public CourseEntity setDuration(Integer duration) {
        this.duration = duration;
        return this;
    }

    public String getSkills() {
        return skills;
    }

    public CourseEntity setSkills(String skills) {
        this.skills = skills;
        return this;
    }

    @ManyToMany
    public List<CategoryEntity> getCategories() {
        return categories;
    }

    public CourseEntity setCategories(List<CategoryEntity> categories) {
        this.categories = categories;
        return this;
    }

    @ManyToMany
    public List<LanguageEntity> getLanguages() {
        return languages;
    }

    public CourseEntity setLanguages(List<LanguageEntity> languages) {
        this.languages = languages;
        return this;
    }

    @OneToMany(cascade = CascadeType.REMOVE)
    public List<LectureEntity> getLectures() {
        return lectures;
    }

    public CourseEntity setLectures(List<LectureEntity> lectures) {
        this.lectures = lectures;
        return this;
    }

    public String getLectorDescription() {
        return lectorDescription;
    }

    public CourseEntity setLectorDescription(String lectorDescription) {
        this.lectorDescription = lectorDescription;
        return this;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public CourseEntity setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
        return this;
    }
}
