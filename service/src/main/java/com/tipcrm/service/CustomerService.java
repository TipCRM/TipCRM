package com.tipcrm.service;
import com.tipcrm.bo.ApproveBo;
import com.tipcrm.bo.CreateCustomerBo;
import com.tipcrm.bo.CustomerTransferRequestBo;
import com.tipcrm.bo.OptCustomerResultBo;
import com.tipcrm.bo.QueryCustomerBo;
import com.tipcrm.bo.QueryRequestBo;
import com.tipcrm.bo.QueryResultBo;

public interface CustomerService {

    OptCustomerResultBo createNewCustomer(CreateCustomerBo createCustomerBo);

    Integer approveCustomer(ApproveBo approveBo);

    OptCustomerResultBo deleteCustomer(Integer customerId);

    QueryResultBo<QueryCustomerBo> findMyCustomers(QueryRequestBo queryRequestBo);

    QueryResultBo<QueryCustomerBo> findByMyDepartmentOpenSea(QueryRequestBo queryRequestBo);

    QueryResultBo<QueryCustomerBo> findByDepartmentOpenSea(Integer departmentId, QueryRequestBo queryRequestBo);

    OptCustomerResultBo transferCustomer(CustomerTransferRequestBo transferBo);
}
