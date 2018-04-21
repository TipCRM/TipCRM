package com.tipcrm.dao.repository;

import com.tipcrm.dao.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {
    @Query("SELECT u FROM User u where u.email = :loginKey OR u.workNo = :loginKey")
    User findByEmailOrWorkNo(@Param(value = "loginKey") String loginKey);

    List<User> findByUserName(String userName);

    @Query("SELECT r.users FROM Role r where r.name = :name")
    List<User> findByRole(@Param(value = "name") String name);

    @Query("select count(u.id) from User u where u.department.id = :departmentId")
    Integer countByDepartmentId(@Param("departmentId") Integer departmentId);

    @Query("select u from User u where u.userName like %:userName% and u.id > 0")
    List<User> findByNameIncludeDismiss(@Param("userName") String userName);

    @Query("select u from User u where u.userName like %:userName% and u.dismissTime is null and u.id > 0")
    List<User> findByNameWithoutDismiss(@Param("userName") String userName);

    List<User> findByDepartmentId(Integer departmentId);

    List<User> findAllByLevelId(Integer levelId);

    User findByEmail(String email);
}
