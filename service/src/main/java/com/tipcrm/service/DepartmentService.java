package com.tipcrm.service;

import java.util.List;

import com.tipcrm.bo.DepartmentBo;
import com.tipcrm.bo.SaveDepartmentBo;

public interface DepartmentService {
    List<DepartmentBo> findAllDepartment();

    Integer createNewDepartment(SaveDepartmentBo saveDepartmentBo);

    DepartmentBo findDepartmentById(Integer departmentId);

    Integer updateDepartment(SaveDepartmentBo saveDepartmentBo);

    Boolean isDepartmentExist(Integer departmentId);

    void deleteDepartmentById(Integer departmentId);
}
