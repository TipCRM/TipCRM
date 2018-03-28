package com.tipcrm.dao.repository;
import java.util.List;
import java.util.Set;

import com.tipcrm.dao.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Integer> {
    List<Permission> findByIdIn(Set<Integer> ids);
}
