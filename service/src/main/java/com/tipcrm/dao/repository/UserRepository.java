package com.tipcrm.dao.repository;
import java.util.List;

import com.tipcrm.dao.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("SELECT u FROM User u where u.email = :loginKey OR u.phoneNo = :loginKey")
    User findByEmailOrPhoneNo(@Param(value = "loginKey") String loginKey);

    List<User> findByUserName(String userName);
}
