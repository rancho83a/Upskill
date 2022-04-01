package com.example.demo.repository;

import com.example.demo.model.entity.UserProfileEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfileEntity, String> {

    Optional<UserProfileEntity> findById(String id);

    Optional<UserProfileEntity> findUserProfileEntityByEmail(String email);

    boolean existsByEmail(String email);

    List<UserProfileEntity> findAllByCompany_CompanyOwner(String companyOwner, Pageable pageableRequest);

    Optional<UserProfileEntity> findByEmail(String email);

    @Query("select u from UserProfileEntity  as u join CompanyEntity as c on c.id=u.company.id where c.id =?1 and not u.email=?2")
    UserProfileEntity[] getEmployeesByBusinessOwner(String companyId, String companyOwnerEmail);
}
