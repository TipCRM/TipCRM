package com.tipcrm.dao.repository;
import java.util.List;
import java.util.Set;

import com.tipcrm.dao.entity.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolePermissionRepository extends JpaRepository<RolePermission, Integer> {
    void deleteByRoleIdAndPermissionIdInAndDeletableIsTrue(Integer roleId, Set<Integer> permissionIds);
}
