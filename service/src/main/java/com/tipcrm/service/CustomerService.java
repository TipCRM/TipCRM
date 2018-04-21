package com.tipcrm.service;

import com.tipcrm.bo.*;

public interface CustomerService {

    OptCustomerResultBo createNewCustomer(CreateCustomerBo createCustomerBo);

    Integer approveCustomer(ApproveBo approveBo);

    OptCustomerResultBo deleteCustomer(Integer customerId);

    QueryResultBo<QueryCustomerBo> findMyCustomers(QueryRequestBo queryRequestBo);

    QueryResultBo<QueryCustomerBo> findByMyDepartmentOpenSea(QueryRequestBo queryRequestBo);

    QueryResultBo<QueryCustomerBo> findByDepartmentOpenSea(Integer departmentId, QueryRequestBo queryRequestBo);

    OptCustomerResultBo transferCustomer(CustomerTransferRequestBo transferBo);
}
