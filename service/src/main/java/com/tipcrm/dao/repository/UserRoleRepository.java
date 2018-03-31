package com.tipcrm.dao.repository;
import java.util.List;

import com.tipcrm.dao.entity.Role;
import com.tipcrm.dao.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {

    @Query("select ur.role from UserRole ur where ur.user.id = :userId")
    List<Role> findRoleByUserId(@Param("userId") Integer userId);

    @Modifying
    @Transactional
    @Query("delete from UserRole ur where ur.role.id = :roleId")
    void deleteByRoleId(@Param("roleId") Integer roleId);

    @Modifying
    @Transactional
    @Query("delete from UserRole ur where ur.user.id = :userId")
    void deleteByUserId(@Param("userId") Integer userId);
}
