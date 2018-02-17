package com.tipcrm.web.publicapi;

import com.tipcrm.bo.CreateCustomerBo;
import com.tipcrm.bo.OptCustomerResultBo;
import com.tipcrm.constant.Constants;
import com.tipcrm.exception.BizException;
import com.tipcrm.service.CustomerService;
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
@RequestMapping("/public/api")
@Api
@RequiresAuthentication
public class CustomerApi {

    @Autowired
    private CustomerService customerService;

    @RequestMapping(value = "/customer", method = RequestMethod.POST)
    @RequiresPermissions(Constants.Permission.CUSTOMER_ADD_UPDATE)
    public JsonEntity<OptCustomerResultBo> createNewCustomer(@RequestBody CreateCustomerBo createCustomerBo) throws BizException {
        return ResponseHelper.createInstance(customerService.createNewCustomer(createCustomerBo));
    }

    @RequestMapping(value = "/customer/{customerId}", method = RequestMethod.PUT)
    @RequiresPermissions(Constants.Permission.CUSTOMER_ADD_UPDATE)
    public JsonEntity<Integer> updateCustomer() {
        return null;
    }

    @RequestMapping(value = "/customer/{customerId}", method = RequestMethod.DELETE)
    @RequiresPermissions(Constants.Permission.CUSTOMER_DELETE)
    public JsonEntity<OptCustomerResultBo> deleteCustomer(@PathVariable("customerId") Integer customerId) throws BizException {
        return ResponseHelper.createInstance(customerService.deleteCustomer(customerId));
    }

    @RequestMapping(value = "/transfer/customer/{customerId}", method = RequestMethod.POST)
    @RequiresPermissions(Constants.Permission.CUSTOMER_TRANSFER)
    public JsonEntity<String> transferCustomer(@PathVariable("customerId") Integer customerId) {
        return null;
    }

    @RequestMapping(value = "/my/customers", method = RequestMethod.GET)
    public JsonEntity myCustomers() {
        return null;
    }
}
