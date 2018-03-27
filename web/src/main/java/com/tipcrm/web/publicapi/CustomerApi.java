package com.tipcrm.web.publicapi;

import com.tipcrm.bo.CreateCustomerBo;
import com.tipcrm.bo.CustomerTransferRequestBo;
import com.tipcrm.bo.OptCustomerResultBo;
import com.tipcrm.bo.QueryCustomerBo;
import com.tipcrm.bo.QueryRequestBo;
import com.tipcrm.bo.QueryResultBo;
import com.tipcrm.constant.Constants;
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
    @RequiresPermissions(Constants.Permission.CUSTOMER_ADD)
    public JsonEntity<OptCustomerResultBo> createNewCustomer(@RequestBody CreateCustomerBo createCustomerBo) {
        return ResponseHelper.createInstance(customerService.createNewCustomer(createCustomerBo));
    }

    @RequestMapping(value = "/customer/{customerId}", method = RequestMethod.PUT)
    @RequiresPermissions(Constants.Permission.CUSTOMER_UPDATE)
    public JsonEntity<Integer> updateCustomer() {
        return null;
    }

    @RequestMapping(value = "/customer/{customerId}", method = RequestMethod.DELETE)
    @RequiresPermissions(Constants.Permission.CUSTOMER_DELETE)
    public JsonEntity<OptCustomerResultBo> deleteCustomer(@PathVariable("customerId") Integer customerId) {
        return ResponseHelper.createInstance(customerService.deleteCustomer(customerId));
    }

    @RequestMapping(value = "/customer/transfer/out", method = RequestMethod.POST)
    @RequiresPermissions(Constants.Permission.CUSTOMER_TRANSFER)
    public JsonEntity<OptCustomerResultBo> transferCustomerOut(@RequestBody CustomerTransferRequestBo transferBo) {
        return ResponseHelper.createInstance(customerService.transferCustomer(transferBo));
    }

    @RequestMapping(value = "/my/customers", method = RequestMethod.POST)
    public JsonEntity<QueryResultBo> myCustomers(@RequestBody QueryRequestBo requestBo) {
        return ResponseHelper.createInstance(customerService.findMyCustomers(requestBo));
    }

    @RequestMapping(value = "/customer/openSea/myDepartment", method = RequestMethod.POST)
    public JsonEntity<QueryResultBo<QueryCustomerBo>> findMyDepartmentOpenSea(@RequestBody QueryRequestBo requestBo) {
        return ResponseHelper.createInstance(customerService.findByMyDepartmentOpenSea(requestBo));
    }

    @RequestMapping(value = "/customer/openSea/department/{departmentId}", method = RequestMethod.POST)
    public JsonEntity<QueryResultBo<QueryCustomerBo>> findByDepartmentOpenSea(@PathVariable("departmentId") Integer departmentId,
                                                                              @RequestBody QueryRequestBo requestBo) {
        return ResponseHelper.createInstance(customerService.findByDepartmentOpenSea(departmentId, requestBo));
    }
}
