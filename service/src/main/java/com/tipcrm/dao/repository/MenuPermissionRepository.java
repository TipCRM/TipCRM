package com.tipcrm.dao.repository;
import com.tipcrm.dao.entity.MenuPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuPermissionRepository extends JpaRepository<MenuPermission, Integer> {
}
