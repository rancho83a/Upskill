package com.example.coursesservice.service;

import com.example.coursesservice.exception.CourseDuplicationException;
import com.example.coursesservice.model.binding.*;
import com.example.coursesservice.model.entity.BusinessOwnerEntity;
import com.example.coursesservice.model.entity.CourseEntity;
import com.example.coursesservice.model.view.CourseCardLectureCountView;
import com.example.coursesservice.model.view.CourseCardPriceView;
import com.example.coursesservice.model.view.CourseDetailsView;
import com.example.coursesservice.model.view.CourseStreamDetailsView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public interface CourseService {
    void add(CourseBindingModel courseBindingModel) throws CourseDuplicationException, IOException;

    void deleteCourse(String id);


    void edit(CourseEditBindingModel courseEditBindingModel) throws IOException;

    CourseEditBindingModel findCourseById(String id);

    List<CourseCardPriceView> getAllCourses(HttpServletRequest request);

    List<CourseCardPriceView> getAllCoursesByBusinessOwner(String companyOwnerEmail, int page, int limit);

    void addBusinessOwnerToCourse(String courseId, String businessOwnerEmail) throws InvocationTargetException, IllegalAccessException;

    List<CourseCardPriceView> searchCourseByLanguageAndCategories(SearchBindingModel searchBindingModel);

    List<CourseCardPriceView> searchCoursesByCategorysName(List<CategoryNameBindingModel> categories);

    List<CourseCardPriceView> searchCoursesByLanguages(List<LanguageNameBindingModel> languages);

    void setCoursesStatus( List<CourseCardPriceView> courses, HttpServletRequest request);

    void removeBusinessOwnerToCourse(String courseId, String businessOwnerEmail);

    CourseDetailsView getCourseDetailsById(String id);

    CourseEntity getCourseEntityById(String id);

    CourseStreamDetailsView getCourseLecturesDetails(String courseId);
}







