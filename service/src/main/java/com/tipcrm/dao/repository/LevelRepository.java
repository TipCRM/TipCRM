package com.tipcrm.dao.repository;

import com.tipcrm.dao.entity.Level;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LevelRepository extends JpaRepository<Level, Integer> {

    Level findByName(String name);

    Level findByIdAndDeleteTimeIsNull(Integer id);

    List<Level> findAllByDeleteTimeIsNull();

}
