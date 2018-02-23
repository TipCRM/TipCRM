package com.tipcrm.service;
import java.util.List;

import com.tipcrm.bo.CreateDepartmentBo;
import com.tipcrm.bo.DepartmentBo;
import com.tipcrm.exception.BizException;

public interface DepartmentService {
    List<DepartmentBo> findAllDepartment();

    Integer createNewDepartment(CreateDepartmentBo createDepartmentBo) throws BizException;

    DepartmentBo findDepartmentById(Integer departmentId);

    Integer updateDepartment(Integer departmentId, CreateDepartmentBo createDepartmentBo) throws BizException;

    Boolean isDepartmentExist(Integer departmentId) throws BizException;

    void deleteDepartmentById(Integer departmentId) throws BizException;
}
