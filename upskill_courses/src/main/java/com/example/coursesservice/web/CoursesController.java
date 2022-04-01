package com.example.coursesservice.web;

import com.example.coursesservice.exception.CourseDuplicationException;
import com.example.coursesservice.model.binding.CourseBindingModel;
import com.example.coursesservice.model.binding.CourseEditBindingModel;
import com.example.coursesservice.model.binding.SearchBindingModel;
import com.example.coursesservice.model.entity.CourseEntity;
import com.example.coursesservice.model.service.CategoryServiceDto;
import com.example.coursesservice.model.service.LanguageServiceDto;
import com.example.coursesservice.model.view.*;
import com.example.coursesservice.service.*;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.stream.Collectors;

//import static com.example.coursesservice.constant.AppConstants.GET_COURSE_INFO_IN;
//import static com.example.coursesservice.constant.AppConstants.GET_COURSE_INFO_OUT;

@RestController
@RequestMapping("/courses")
public class CoursesController {

    private final CourseService courseService;
    private final ModelMapper modelMapper;
    private final CategoryService categoryService;
    private final LanguageService languageService;

    public CoursesController(CourseService courseService, ModelMapper modelMapper, CategoryService categoryService, LanguageService languageService) {
        this.courseService = courseService;
        this.modelMapper = modelMapper;
        this.categoryService = categoryService;
        this.languageService = languageService;
    }


    @PostMapping(value = "/create")
    public ResponseEntity<?> create(@RequestBody CourseBindingModel courseBindingModel) throws CourseDuplicationException, IOException {

        courseService.add(courseBindingModel);
        return ResponseEntity.status(201).build();
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable String id){

        this.courseService.deleteCourse(id);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/details/{id}")
    public ResponseEntity<?> coursesDetailsList(@PathVariable String id){

        CourseDetailsView courseDetailsView = this.courseService.getCourseDetailsById(id);

        return ResponseEntity.ok(courseDetailsView);
    }

    @GetMapping("/lectures/{courseId}")
    public ResponseEntity<?> getCourseLecturesList(@PathVariable String courseId){

        CourseStreamDetailsView courseStreamDetailsView = this.courseService.getCourseLecturesDetails(courseId);

         return ResponseEntity.ok(courseStreamDetailsView);
    }

    @GetMapping("/edit/{id}")
    public ResponseEntity<?> updateCourse(@PathVariable String id){
        CourseEditBindingModel courseToEdit = this.courseService.findCourseById(id);
        return ResponseEntity.ok(courseToEdit);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<?> edit(@PathVariable String id,
                                  @RequestBody CourseEditBindingModel courseEditBindingModel) throws CourseDuplicationException, IOException {

        courseEditBindingModel.setId(id);
        courseService.edit(courseEditBindingModel);
        return ResponseEntity.status(200).build();
    }

    @GetMapping("/languages")
    public List<LanguageServiceDto> getAllLanguages() {
        return this.languageService.getAllLanguages();
    }

    @GetMapping("/categories")
    public List<CategoryServiceDto> getAllCategories() {
        return this.categoryService.getAllCategories();
    }

    @GetMapping()
    public List<CourseCardPriceView> getAllCourses(HttpServletRequest request){
        return this.courseService.getAllCourses(request);
    }

    @GetMapping("/company/catalog")
    @ResponseStatus(HttpStatus.FOUND)
    public ResponseEntity<?> getAllCoursesByBusinessOwner(
            HttpServletRequest request,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "limit", defaultValue = "4") int limit){

        String companyOwnerEmail = request.getHeader("X-User-Email");

        List<CourseCardPriceView> allCoursesByBusinessOwner = this.courseService
                .getAllCoursesByBusinessOwner(companyOwnerEmail,
                        page, limit);

        return ResponseEntity.ok(allCoursesByBusinessOwner);
    }

    @PostMapping("/add/{courseId}")
    public ResponseEntity<?> addCourseToBusinessOwner(@PathVariable String courseId,
                                                      HttpServletRequest request) throws InvocationTargetException, IllegalAccessException {

        String businessOwnerEmail = request.getHeader("X-User-Email");
        this.courseService.addBusinessOwnerToCourse(courseId,
                businessOwnerEmail);

        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @PostMapping("/remove/{courseId}")
    public ResponseEntity<?> removeCourseToBusinessOwner(@PathVariable String courseId,
                                                      HttpServletRequest request){

        String businessOwnerEmail = request.getHeader("X-User-Email");
        this.courseService.removeBusinessOwnerToCourse(courseId,
                businessOwnerEmail);

        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @PostMapping("/search")
    public ResponseEntity<?> searchBy(@RequestBody SearchBindingModel searchBindingModel, HttpServletRequest request){

        List<CourseCardPriceView> courses;

        if(searchBindingModel.getCategories().isEmpty()){
            courses = this.courseService.searchCoursesByLanguages(searchBindingModel.getLanguages());
        }else if(searchBindingModel.getLanguages().isEmpty()) {
            courses = this.courseService.searchCoursesByCategorysName(searchBindingModel.getCategories());
        }else {
            courses = this.courseService.searchCourseByLanguageAndCategories(searchBindingModel);
        }

      this.courseService.setCoursesStatus(courses, request);

        return ResponseEntity.ok(courses);
    }
}
