package com.tipcrm.web.publicapi;
import com.tipcrm.bo.ApproveBo;
import com.tipcrm.constant.Constants;
import com.tipcrm.service.CustomerService;
import com.tipcrm.web.util.JsonEntity;
import com.tipcrm.web.util.ResponseHelper;
import io.swagger.annotations.Api;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public/api")
@Api
@RequiresAuthentication
public class ApprovalApi {

    @Autowired
    private CustomerService customerService;

    @RequestMapping(value = "/approval/customer", method = RequestMethod.POST)
    @RequiresPermissions(Constants.Permission.CUSTOMER_APPROVAL)
    public JsonEntity<String> approveCustomer(@RequestBody ApproveBo approveBo) {
        customerService.approveCustomer(approveBo);
        return ResponseHelper.createInstance(Constants.RequestResult.SUCCESS);
    }
}
