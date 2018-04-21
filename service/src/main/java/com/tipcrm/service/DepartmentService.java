package com.tipcrm.service;

import com.tipcrm.bo.DepartmentBo;
import com.tipcrm.bo.SaveDepartmentBo;

import java.util.List;

public interface DepartmentService {
    List<DepartmentBo> findAllDepartment();

    Integer createNewDepartment(SaveDepartmentBo saveDepartmentBo);

    DepartmentBo findDepartmentById(Integer departmentId);

    Integer updateDepartment(SaveDepartmentBo saveDepartmentBo);

    Boolean isDepartmentExist(Integer departmentId);

    void deleteDepartmentById(Integer departmentId);
}
