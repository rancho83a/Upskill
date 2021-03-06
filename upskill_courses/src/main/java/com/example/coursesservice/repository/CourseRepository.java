package com.example.coursesservice.repository;

import com.example.coursesservice.model.binding.LanguageNameBindingModel;
import com.example.coursesservice.model.entity.BusinessOwnerEntity;
import com.example.coursesservice.model.entity.CourseEntity;
import com.example.coursesservice.model.entity.LanguageEntity;
import com.example.coursesservice.model.enums.CategoryNameEnum;
import com.example.coursesservice.model.enums.LanguageEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<CourseEntity, String>{
    boolean existsByName(String name);

    Optional<CourseEntity> getByName(String name);

    @Query("select c from CourseEntity as c left join c.businessOwners as b where b.email = :email")
    List<CourseEntity> getAllByBusinessOwnersEmail(String email, Pageable pageable);

    @Query("select distinct c from CourseEntity as c left join c.languages as l where l.name IN :languages")
    List<CourseEntity> getAllByLanguages(List<LanguageEnum> languages);

    @Query("select distinct c from CourseEntity as c left join c.categories as cat where cat.name IN :categories")
    List<CourseEntity> getAllByCategory(List<CategoryNameEnum> categories);

    @Query("select distinct c from CourseEntity as c left join c.categories as categories join c.languages as languages where categories.name in ?1 and languages.name in ?2")
   Optional<CourseEntity[]> SearchByCategoryAndLanguage(List<CategoryNameEnum> categoryNameEnumsList, List<LanguageEnum> languageEnumList);
}
