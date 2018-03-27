package com.tipcrm.web.publicapi;
import java.util.List;

import com.tipcrm.bo.CreateDepartmentBo;
import com.tipcrm.bo.DepartmentBo;
import com.tipcrm.constant.Constants;
import com.tipcrm.service.DepartmentService;
import com.tipcrm.web.util.JsonEntity;
import com.tipcrm.web.util.ResponseHelper;
import io.swagger.annotations.Api;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/public/api")
@Api
@RequiresAuthentication
public class DepartmentApi {

    @Autowired
    private DepartmentService departmentService;

    @RequestMapping(value = "/departments", method = RequestMethod.GET)
    public JsonEntity<List<DepartmentBo>> findAllDepartment() {
        return ResponseHelper.createInstance(departmentService.findAllDepartment());
    }

    @RequestMapping(value = "/department/{departmentId}", method = RequestMethod.GET)
    public JsonEntity<DepartmentBo> findDepartmentById(@PathVariable(value = "departmentId") Integer departmentId) {
        return ResponseHelper.createInstance(departmentService.findDepartmentById(departmentId));
    }

    @RequestMapping(value = "/department", method = RequestMethod.POST)
    @RequiresPermissions(value = Constants.Permission.DEPARTMENT_ADD)
    public JsonEntity<Integer> addDepartment(@RequestBody CreateDepartmentBo createDepartmentBo) {
        return ResponseHelper.createInstance(departmentService.createNewDepartment(createDepartmentBo));
    }

    @RequestMapping(value = "/department/{departmentId}", method = RequestMethod.PUT)
    @RequiresPermissions(value = Constants.Permission.DEPARTMENT_UPDATE)
    public JsonEntity<Integer> updateDepartment(@PathVariable("departmentId") Integer departmentId, @RequestBody CreateDepartmentBo createDepartmentBo)
        {
        return ResponseHelper.createInstance(departmentService.updateDepartment(departmentId, createDepartmentBo));
    }

    @RequestMapping(value = "/department/{departmentId}", method = RequestMethod.DELETE)
    @RequiresPermissions(value = Constants.Permission.DEPARTMENT_DELETE)
    public JsonEntity<String> deleteDepartmentById(@PathVariable("departmentId") Integer departmentId) {
        departmentService.deleteDepartmentById(departmentId);
        return ResponseHelper.createInstance(Constants.RequestResult.SUCCESS);
    }
}
