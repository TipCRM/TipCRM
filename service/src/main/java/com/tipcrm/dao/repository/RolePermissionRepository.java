package com.tipcrm.dao.repository;

import com.tipcrm.dao.entity.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Repository
public interface RolePermissionRepository extends JpaRepository<RolePermission, Integer> {

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM RolePermission rp where rp.role.id = :roleId and rp.permission.id in :permissionIds")
    void deleteByRoleIdAndPermissionId(@Param("roleId") Integer roleId, @Param("permissionIds") Set<Integer> permissionIds);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM RolePermission rp where rp.role.id = :roleId")
    void deleteByRoleId(@Param("roleId") Integer roleId);
}
