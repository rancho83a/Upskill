package com.example.coursesservice.service.impl;

import com.example.coursesservice.exception.CourseDuplicationException;
import com.example.coursesservice.exception.CourseNotFoundException;
import com.example.coursesservice.model.binding.*;
import com.example.coursesservice.model.entity.*;
import com.example.coursesservice.model.enums.CategoryNameEnum;
import com.example.coursesservice.model.enums.LanguageEnum;
import com.example.coursesservice.model.view.CourseCardPriceView;
import com.example.coursesservice.model.view.CourseDetailsView;
import com.example.coursesservice.model.view.CourseStreamDetailsView;
import com.example.coursesservice.model.view.LecturesDetailsView;
import com.example.coursesservice.repository.*;
import com.example.coursesservice.service.BusinessOwnerService;
import com.example.coursesservice.service.CloudinaryService;
import com.example.coursesservice.service.CourseService;
import com.example.coursesservice.stream.StreamChannelDispatcher;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.coursesservice.constant.AppConstants.*;

@Service
public class CourseServiceImpl implements CourseService {

    private final ModelMapper modelMapper;
    private final LectureRepository lectureRepository;
    private final LanguageRepository languageRepository;
    private final CategoryRepository categoryRepository;
    private final CourseRepository courseRepository;
    private final BusinessOwnerRepository businessOwnerRepository;
    private final CloudinaryService cloudinaryService;
    private final BusinessOwnerService businessOwnerService;
    private final StreamChannelDispatcher streamChannelDispatcher;

    public CourseServiceImpl(ModelMapper modelMapper, LectureRepository lectureRepository, LanguageRepository languageRepository, CategoryRepository categoryRepository, CourseRepository courseRepository, BusinessOwnerRepository businessOwnerRepository, CloudinaryService cloudinaryService, BusinessOwnerService businessOwnerService, StreamChannelDispatcher streamChannelDispatcher) {
        this.modelMapper = modelMapper;
        this.lectureRepository = lectureRepository;
        this.languageRepository = languageRepository;
        this.categoryRepository = categoryRepository;
        this.courseRepository = courseRepository;
        this.businessOwnerRepository = businessOwnerRepository;
        this.cloudinaryService = cloudinaryService;
        this.businessOwnerService = businessOwnerService;
        this.streamChannelDispatcher = streamChannelDispatcher;
    }

    @Override
    public void add(CourseBindingModel courseBindingModel) throws CourseDuplicationException, IOException {

        if (isExist(courseBindingModel.getName())) {
            throw new CourseDuplicationException();
        }
        CourseEntity course = modelMapper.map(courseBindingModel, CourseEntity.class);

        course.setImageUrl(this.cloudinaryService.upload(convertFileToMultipartFile(courseBindingModel.getImageUrl())).getUrl());

        List<LectureEntity> lectures = courseBindingModel.getLectures().stream()
                .map(lecture -> {
                    LectureEntity lectureEntity = modelMapper.map(lecture, LectureEntity.class);

                    this.lectureRepository.save(lectureEntity);
                    return lectureEntity;
                }).collect(Collectors.toList());

        List<LanguageEntity> languages = courseBindingModel.getLanguages().stream()
                .map(l -> this.languageRepository.getByName
                        (LanguageEnum.valueOf(l.getName().toUpperCase()))).collect(Collectors.toList());

        List<CategoryEntity> categories = courseBindingModel.getCategories().stream()
                .map(c -> this.categoryRepository.getCategoryByName
                        (CategoryNameEnum.valueOf(c.getName().toUpperCase()))).collect(Collectors.toList());

        course.setCategories(categories);
        course.setLanguages(languages);
        course.setLectures(lectures);

        this.courseRepository.save(course);

    }

    private MultipartFile convertFileToMultipartFile(File file){

        Path path = Paths.get(file.getPath());
        String name = file.getName();
        String originalFileName = file.getName();
        String contentType = "application/octet-stream";
        byte[] content = null;
        try {
            content = Files.readAllBytes(path);
        } catch (final IOException e) {
            e.printStackTrace();
        }

        return new MockMultipartFile(name,
                originalFileName, contentType, content);
    }

    @Override
    public void deleteCourse(String id) {

        deleteCloudinaryPicture(this.courseRepository.getById(id));

        this.courseRepository.deleteById(id);
    }

    private boolean deleteCloudinaryPicture(CourseEntity courseToDelete) {
        String publicId = courseToDelete.getImageUrl()
                .substring(courseToDelete.getImageUrl().lastIndexOf(CLOUDINARY_CUT_START) + CLOUDINARY_CUT_INDEX,
                        courseToDelete.getImageUrl().lastIndexOf(CLOUDINARY_CUT_END));

      return this.cloudinaryService.delete(publicId);

    }


    public void edit(CourseEditBindingModel courseEditBindingModel) throws IOException {

        CourseEntity courseEntity = this.courseRepository.findById(courseEditBindingModel.getId())
                .orElseThrow(() -> new CourseNotFoundException(courseEditBindingModel.getId()));

        List<LectureEntity> lectures = courseEditBindingModel.getLectures().stream()
                .map(lecture -> {

                    LectureEntity lectureEntity = this.lectureRepository.getById(lecture.getId())
                            .setLectureName(lecture.getLectureName())
                            .setLectureDescription(lecture.getLectureDescription())
                            .setResourceUrl(lecture.getResourceUrl());

                    return this.lectureRepository.save(lectureEntity);
                }).collect(Collectors.toList());

        List<LanguageEntity> languages = courseEditBindingModel.getLanguages().stream()
                .map(l -> this.languageRepository.getByName
                        (LanguageEnum.valueOf(l.getName().toUpperCase()))).collect(Collectors.toList());

        List<CategoryEntity> categories = courseEditBindingModel.getCategories().stream()
                .map(c -> this.categoryRepository.getCategoryByName
                        (CategoryNameEnum.valueOf(c.getName().toUpperCase()))).collect(Collectors.toList());

        courseEntity.setName(courseEditBindingModel.getName())
                .setLectures(lectures)
                .setLanguages(languages)
                .setCategories(categories)
                .setDescription(courseEditBindingModel.getDescription())
                .setDuration(courseEditBindingModel.getDuration())
                .setStartDate(courseEditBindingModel.getStartDate())
                .setEndDate(courseEditBindingModel.getEndDate())
                .setLector(courseEditBindingModel.getLector())
                .setLectorDescription(courseEditBindingModel.getLectorDescription())
                .setPrice(courseEditBindingModel.getPrice())
                .setSkills(courseEditBindingModel.getSkills())
                .setImageUrl(this.cloudinaryService.upload(convertFileToMultipartFile(courseEditBindingModel.getImageUrl())).getUrl())
                .setVideoUrl(courseEditBindingModel.getVideoUrl());
        this.courseRepository.save(courseEntity);
    }


    @Override
    public CourseEditBindingModel findCourseById(String id) {
        CourseEntity courseEntity = this.courseRepository.findById(id).orElseThrow(() -> new CourseNotFoundException(id));

        return modelMapper.map(courseEntity, CourseEditBindingModel.class);
    }

    @Override
    public List<CourseCardPriceView> getAllCourses(HttpServletRequest request) {

        List<CourseCardPriceView> courses = this.courseRepository.findAll().stream()
                .map(c -> {
                    CourseCardPriceView map = modelMapper.map(c, CourseCardPriceView.class);
                    map.setImageUrl(c.getImageUrl());
                    map.setCourseStatus(COURSE_STATUS_DELETE);
                    return map;
                }).collect(Collectors.toList());

        if (request.getHeader("X-User-Roles").equals(BUSINESS_OWNER_ROLE)) {
            setCoursesStatus(courses, request);
        }
        return courses;
    }

    @Override
    public List<CourseCardPriceView> getAllCoursesByBusinessOwner(String companyOwnerEmail, int page, int limit) {

        if (page > 0) {
            page = page - 1;
        }

        Pageable pageableRequest = PageRequest.of(page, limit);

        return this.courseRepository
                .getAllByBusinessOwnersEmail(companyOwnerEmail, pageableRequest)
                .stream()
                .map(s -> {
                    CourseCardPriceView course = this.modelMapper.map(s, CourseCardPriceView.class);
                    course.setCourseStatus(COURSE_STATUS_REMOVE);

                    return course;
                }).collect(Collectors.toList());
    }

    @Override
    public void addBusinessOwnerToCourse(String courseId, String businessOwnerEmail) throws InvocationTargetException, IllegalAccessException {
        CourseEntity course = this.courseRepository.getById(courseId);

        BusinessOwnerEntity businessOwner = this.businessOwnerRepository
                .getBusinessOwnerEntityByEmail(businessOwnerEmail);

        if (businessOwner == null) {
            businessOwner = new BusinessOwnerEntity()
                    .setEmail(businessOwnerEmail)
                    .setCourses(List.of(course));
            course.getBusinessOwners().add(businessOwner);

            this.businessOwnerRepository.save(businessOwner);
            this.courseRepository.save(course);
        }

        course.getBusinessOwners().add(businessOwner);

        this.courseRepository.save(course);
    }

    @Override
    public List<CourseCardPriceView> searchCourseByLanguageAndCategories(SearchBindingModel searchBindingModel) {
        List<CategoryNameEnum> categoryNameEnumsList = searchBindingModel.getCategories().stream().map(c -> CategoryNameEnum.valueOf(c.getName().toUpperCase())).toList();
        List<LanguageEnum> languageEnumList = searchBindingModel.getLanguages().stream().map(l -> LanguageEnum.valueOf(l.getName().toUpperCase())).toList();
        Optional<CourseEntity[]> courses = this.courseRepository.SearchByCategoryAndLanguage(categoryNameEnumsList, languageEnumList);

        return Arrays.asList(this.modelMapper.map(courses.get(), CourseCardPriceView[].class));
    }

    @Override
    public List<CourseCardPriceView> searchCoursesByCategorysName(List<CategoryNameBindingModel> categories) {
        List<CategoryNameEnum> categoryEnumList = categories.stream().map(l -> CategoryNameEnum.valueOf(l.getName().toUpperCase())).toList();
        List<CourseEntity> allByLanguages = this.courseRepository.getAllByCategory(categoryEnumList);

       return allByLanguages.stream().map(c -> this.modelMapper.map(c, CourseCardPriceView.class)).collect(Collectors.toList());
    }

    @Override
    public List<CourseCardPriceView> searchCoursesByLanguages(List<LanguageNameBindingModel> languages) {
        List<LanguageEnum> languageEnumList = languages.stream().map(l -> LanguageEnum.valueOf(l.getName().toUpperCase())).toList();
        List<CourseEntity> allByLanguages = this.courseRepository.getAllByLanguages(languageEnumList);

        return allByLanguages.stream().map(c -> this.modelMapper.map(c, CourseCardPriceView.class)).collect(Collectors.toList());
    }

    @Override
    public void setCoursesStatus(List<CourseCardPriceView> courses, HttpServletRequest request) {

        String businessOwnerEmail = request.getHeader("X-User-Email");
       if(request.getHeader("X-User-Roles").equals(ADMIN_ROLE)){
           courses.forEach(c -> c.setCourseStatus(COURSE_STATUS_DELETE));
       }
        else if (this.businessOwnerService.isBusinessOwnerExist(businessOwnerEmail)) {
            List<CourseEntity> businessOwnerCourses = this.businessOwnerService
                    .getBusinessOwnerByEmail(businessOwnerEmail).getCourses();

            for (CourseCardPriceView course : courses) {

                Optional<CourseEntity> enrolledCourse = businessOwnerCourses
                        .stream()
                        .filter(c -> c.getName().equals(course.getName())).findAny();

                if (enrolledCourse.isPresent()) {
                    course.setCourseStatus(COURSE_STATUS_REMOVE);
                } else {
                    course.setCourseStatus(COURSE_STATUS_ADD);
                }
            }
        }else {
            this.businessOwnerService.createBusinessOwner(businessOwnerEmail);
        }
    }

    @Override
    public void removeBusinessOwnerToCourse(String courseId, String businessOwnerEmail) {
        CourseEntity course = this.courseRepository.getById(courseId);

        BusinessOwnerEntity businessOwner = this.businessOwnerRepository
                .getBusinessOwnerEntityByEmail(businessOwnerEmail);

        course.getBusinessOwners().remove(businessOwner);
        businessOwner.getCourses().remove(course);
        this.courseRepository.save(course);
        this.businessOwnerService.save(businessOwner);
    }

    @Override
    public CourseDetailsView getCourseDetailsById(String id) {
        CourseEntity currentCourse = this.courseRepository.getById(id);

        CourseDetailsView courseDetailsView = this.modelMapper
                .map(currentCourse, CourseDetailsView.class);

        courseDetailsView.setLecturesCount(currentCourse.
                getLectures().size());
        return courseDetailsView;
    }

    @Override
    public CourseEntity getCourseEntityById(String id) {
        return this.courseRepository.getById(id);
    }

    @Override
    public CourseStreamDetailsView getCourseLecturesDetails(String courseId) {
        CourseEntity currentCourse = this.getCourseEntityById(courseId);

        List<LecturesDetailsView> lectures = currentCourse.
                getLectures().stream()
                .map(l ->  this.modelMapper.map(l, LecturesDetailsView.class))
                .collect(Collectors.toList());

        CourseStreamDetailsView courseStreamDetailsView = this.modelMapper.map(currentCourse, CourseStreamDetailsView.class)
                .setLectures(lectures);

        return courseStreamDetailsView;
    }

    private boolean isExist(String name) {
        return this.courseRepository.existsByName(name);
    }
}
