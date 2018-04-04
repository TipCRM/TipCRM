package com.tipcrm.service.impl;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.tipcrm.bo.DepartmentBo;
import com.tipcrm.bo.SaveDepartmentBo;
import com.tipcrm.bo.UserBasicBo;
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
    public Integer createNewDepartment(SaveDepartmentBo saveDepartmentBo) {
        validateSaveDepartmentBo(saveDepartmentBo);
        Department department = convertToDepartment(saveDepartmentBo);
        department = departmentRepository.save(department);
        if (saveDepartmentBo.getManagerId() != null) {
            User manager = userRepository.findOne(saveDepartmentBo.getManagerId());
            manager.setDepartment(department);
            userRepository.save(manager);
        }
        return department.getId();
    }

    @Override
    public DepartmentBo findDepartmentById(Integer departmentId) {
        return convertToDepartmentBo(departmentRepository.findByIdAndDeleteTimeIsNull(departmentId));
    }

    @Override
    public Integer updateDepartment(SaveDepartmentBo saveDepartmentBo) {
        validateUpdateDepartment(saveDepartmentBo);
        Department department = departmentRepository.findOne(saveDepartmentBo.getId());
        department.setName(saveDepartmentBo.getName());
        User manager = null;
        if (saveDepartmentBo.getManagerId() != null) {
            if (!userService.isUserExist(saveDepartmentBo.getManagerId())) {
                throw new BizException("用户不存在");
            }
            manager = userRepository.findOne(saveDepartmentBo.getManagerId());
            manager.setDepartment(department);
            userRepository.save(manager);
        }
        department.setManager(manager);
        departmentRepository.save(department);
        return department.getId();
    }

    private void validateUpdateDepartment(SaveDepartmentBo saveDepartmentBo) {
        if (saveDepartmentBo.getId() == null) {
            throw new BizException("部门Id不能为空");
        }
        Department department = departmentRepository.findOne(saveDepartmentBo.getId());
        if (department == null) {
            throw new BizException("部门不存在");
        }
        if (StringUtils.isBlank(saveDepartmentBo.getName())) {
            throw new BizException("部门名称不能为空");
        }
        Department departmentByName = departmentRepository.findByNameAndDeleteTimeIsNull(saveDepartmentBo.getName());
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
        List<User> users = userRepository.findByDepartmentId(departmentId);
        if (!CollectionUtils.isEmpty(users)) {
            users.forEach(user -> user.setDepartment(null));
            userRepository.save(users);
        }
        departmentRepository.save(department);
    }

    public Boolean isDepartmentExist(String name) {
        if (StringUtils.isBlank(name)) {
            throw new BizException("部门名称不能为空");
        }
        Department department = departmentRepository.findByNameAndDeleteTimeIsNull(name);
        return department != null;
    }

    private void validateSaveDepartmentBo(SaveDepartmentBo saveDepartmentBo) {
        if (isDepartmentExist(saveDepartmentBo.getName())) {
            throw new BizException("部门已存在");
        }
        if (saveDepartmentBo.getManagerId() != null && !userService.isUserExist(saveDepartmentBo.getManagerId())) {
            throw new BizException("该经理不存在");
        }
    }

    private Department convertToDepartment(SaveDepartmentBo saveDepartmentBo) {
        Department department = new Department();
        department.setName(saveDepartmentBo.getName());
        User manager = null;
        if (saveDepartmentBo.getManagerId() != null) {
            manager = userRepository.findOne(saveDepartmentBo.getManagerId());
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
            UserBasicBo userBasicBo = new UserBasicBo();
            userBasicBo.setId(manager.getId());
            userBasicBo.setName(manager.getUserName());
            departmentBo.setManager(userBasicBo);
        }
        departmentBo.setName(department.getName());
        departmentBo.setEntryTime(department.getEntryTime());
        departmentBo.setEntryUser(department.getEntryUser().getUserName());
        departmentBo.setTotal(userRepository.countByDepartmentId(department.getId()));
        return departmentBo;
    }

}
