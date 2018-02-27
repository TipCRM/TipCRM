package com.tipcrm.service.impl;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.tipcrm.bo.CreateDepartmentBo;
import com.tipcrm.bo.DepartmentBo;
import com.tipcrm.dao.entity.Department;
import com.tipcrm.dao.entity.User;
import com.tipcrm.dao.repository.DepartmentRepository;
import com.tipcrm.dao.repository.UserRepository;
import com.tipcrm.exception.BizException;
import com.tipcrm.service.DepartmentService;
import com.tipcrm.service.UserService;
import com.tipcrm.service.WebContext;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Service
@Transactional
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WebContext webContext;

    @Autowired
    private UserService userService;

    @Override
    public List<DepartmentBo> findAllDepartment() {
        List<Department> departments = departmentRepository.findByDeleteTimeIsNull();
        return convertToDepartmentBos(departments);
    }

    @Override
    public Integer createNewDepartment(CreateDepartmentBo createDepartmentBo) {
        validateCreateDepartmentBo(createDepartmentBo);
        Department department = convertToDepartment(createDepartmentBo);
        department = departmentRepository.save(department);
        return department.getId();
    }

    @Override
    public DepartmentBo findDepartmentById(Integer departmentId) {
        return convertToDepartmentBo(departmentRepository.findByIdAndDeleteTimeIsNull(departmentId));
    }

    @Override
    public Integer updateDepartment(Integer departmentId, CreateDepartmentBo createDepartmentBo) {
        validateUpdateDepartment(departmentId, createDepartmentBo);
        Department department = departmentRepository.findOne(departmentId);
        department.setName(createDepartmentBo.getName());
        User manager = null;
        if (createDepartmentBo.getManagerId() != null) {
            if (!userService.isUserExist(createDepartmentBo.getManagerId())) {
                throw new BizException("经理不存在");
            }
            manager = userRepository.findOne(createDepartmentBo.getManagerId());
        }
        department.setManager(manager);
        departmentRepository.save(department);
        return department.getId();
    }

    private void validateUpdateDepartment(Integer departmentId, CreateDepartmentBo createDepartmentBo) {
        if (departmentId == null) {
            throw new BizException("部门Id不能为空");
        }
        Department department = departmentRepository.findOne(departmentId);
        if (department == null) {
            throw new BizException("部门不存在");
        }
        if (StringUtils.isBlank(createDepartmentBo.getName())) {
            throw new BizException("部门名称不能为空");
        }
        Department departmentByName = departmentRepository.findByNameAndDeleteTimeIsNull(createDepartmentBo.getName());
        if (departmentByName != null && !departmentByName.getId().equals(department.getId())) {
            throw new BizException("部门名称已被占用");
        }
    }

    @Override
    public Boolean isDepartmentExist(Integer departmentId) {
        if (departmentId == null) {
            throw new BizException("部门Id不能为空");
        }
        Department department = departmentRepository.findOne(departmentId);
        return department != null;
    }

    @Override
    public void deleteDepartmentById(Integer departmentId) {
        if (!isDepartmentExist(departmentId)) {
            throw new BizException("部门不存在");
        }
        Department department = departmentRepository.findOne(departmentId);
        department.setDeleteTime(new Date());
        department.setDeleteUser(webContext.getCurrentUser());
        departmentRepository.save(department);
    }

    public Boolean isDepartmentExist(String name) {
        if (StringUtils.isBlank(name)) {
            throw new BizException("部门名称不能为空");
        }
        Department department = departmentRepository.findByNameAndDeleteTimeIsNull(name);
        return department != null;
    }

    private void validateCreateDepartmentBo(CreateDepartmentBo createDepartmentBo) {
        if (isDepartmentExist(createDepartmentBo.getName())) {
            throw new BizException("部门已存在");
        }
        if (createDepartmentBo.getManagerId() != null && !userService.isUserExist(createDepartmentBo.getManagerId())) {
            throw new BizException("该经理不存在");
        }
    }

    private Department convertToDepartment(CreateDepartmentBo createDepartmentBo) {
        Department department = new Department();
        department.setName(createDepartmentBo.getName());
        User manager = null;
        if (createDepartmentBo.getManagerId() != null) {
            manager = userRepository.findOne(createDepartmentBo.getManagerId());
        }
        department.setManager(manager);
        department.setEntryTime(new Date());
        department.setEntryUser(webContext.getCurrentUser());
        return department;
    }

    private List<DepartmentBo> convertToDepartmentBos(List<Department> departments) {
        List<DepartmentBo> departmentBos = new ArrayList<DepartmentBo>();
        if (!CollectionUtils.isEmpty(departments)) {
            for (Department department : departments) {
                departmentBos.add(convertToDepartmentBo(department));
            }
        }
        return departmentBos;
    }

    private DepartmentBo convertToDepartmentBo(Department department) {
        if (department == null) {
            return null;
        }
        DepartmentBo departmentBo = new DepartmentBo();
        departmentBo.setId(department.getId());
        User manager = department.getManager();
        if (manager != null) {
            departmentBo.setManagerId(manager.getId());
            departmentBo.setManagerName(manager.getUserName());
        }
        departmentBo.setName(department.getName());
        departmentBo.setEntryTime(department.getEntryTime());
        departmentBo.setEntryUser(department.getEntryUser().getUserName());
        return departmentBo;
    }

}
