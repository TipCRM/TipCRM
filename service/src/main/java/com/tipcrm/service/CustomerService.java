package com.tipcrm.service;
import com.tipcrm.bo.ApproveBo;
import com.tipcrm.bo.CreateCustomerBo;
import com.tipcrm.bo.OptCustomerResultBo;
import com.tipcrm.exception.BizException;

public interface CustomerService {

    OptCustomerResultBo createNewCustomer(CreateCustomerBo createCustomerBo) throws BizException;

    Integer approveCustomer(ApproveBo approveBo) throws BizException;

    OptCustomerResultBo deleteCustomer(Integer customerId) throws BizException;
}
