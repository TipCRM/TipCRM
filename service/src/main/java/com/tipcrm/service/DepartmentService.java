package com.tipcrm.service;
import java.util.List;

import com.tipcrm.bo.CreateDepartmentBo;
import com.tipcrm.bo.DepartmentBo;

public interface DepartmentService {
    List<DepartmentBo> findAllDepartment();

    Integer createNewDepartment(CreateDepartmentBo createDepartmentBo);

    DepartmentBo findDepartmentById(Integer departmentId);

    Integer updateDepartment(Integer departmentId, CreateDepartmentBo createDepartmentBo);

    Boolean isDepartmentExist(Integer departmentId);

    void deleteDepartmentById(Integer departmentId);
}
