package com.tipcrm.dao.repository;

import com.tipcrm.dao.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer> {

    Department findByNameAndDeleteTimeIsNull(String name);

    Department findByIdAndDeleteTimeIsNull(Integer departmentId);

    List<Department> findByDeleteTimeIsNull();

    List<Department> findByManagerId(Integer managerId);
}
