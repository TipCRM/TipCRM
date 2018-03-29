package com.tipcrm.dao.repository;
import java.util.List;
import java.util.Set;

import com.tipcrm.dao.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Integer> {

    @Query("SELECT m FROM Menu m where m.permission is null or m.permission.id in :permissions")
    List<Menu> findMenuByPermissionIds(@Param("permissions") Set<Integer> permissions);
}
