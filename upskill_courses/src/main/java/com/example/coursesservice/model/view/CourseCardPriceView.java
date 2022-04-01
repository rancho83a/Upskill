package com.example.coursesservice.model.view;

import java.math.BigDecimal;

public class CourseCardPriceView {

    private String id;
    private String imageUrl;
    private String name;
    private String lector;
    private BigDecimal price;
    private String courseStatus;

    public CourseCardPriceView() {
    }

    public CourseCardPriceView(String id, String imageUrl, String name, String lector, BigDecimal price, String courseStatus) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.name = name;
        this.lector = lector;
        this.price = price;
        this.courseStatus = courseStatus;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public CourseCardPriceView setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public String getId() {
        return id;
    }

    public CourseCardPriceView setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public CourseCardPriceView setName(String name) {
        this.name = name;
        return this;
    }

    public String getLector() {
        return lector;
    }

    public CourseCardPriceView setLector(String lector) {
        this.lector = lector;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public CourseCardPriceView setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public String getCourseStatus() {
        return courseStatus;
    }

    public CourseCardPriceView setCourseStatus(String courseStatus) {
        this.courseStatus = courseStatus;
        return this;
    }
}
