package com.tipcrm.dao.repository;
import java.util.List;

import com.tipcrm.dao.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Integer> {
    @Query("SELECT m FROM Menu m ORDER BY m.sequence ASC")
    List<Menu> findAllOrderBySequence();
}
