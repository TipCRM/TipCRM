package com.tipcrm.service;
import com.tipcrm.bo.ApproveBo;
import com.tipcrm.bo.CreateCustomerBo;
import com.tipcrm.bo.CustomerTransferRequestBo;
import com.tipcrm.bo.OptCustomerResultBo;
import com.tipcrm.bo.QueryCustomerBo;
import com.tipcrm.bo.QueryRequestBo;
import com.tipcrm.bo.QueryResultBo;
import com.tipcrm.exception.BizException;
import com.tipcrm.exception.QueryException;

public interface CustomerService {

    OptCustomerResultBo createNewCustomer(CreateCustomerBo createCustomerBo) throws BizException;

    Integer approveCustomer(ApproveBo approveBo) throws BizException;

    OptCustomerResultBo deleteCustomer(Integer customerId) throws BizException;

    QueryResultBo<QueryCustomerBo> findMyCustomers(QueryRequestBo queryRequestBo) throws QueryException;

    QueryResultBo<QueryCustomerBo> findByMyDepartmentOpenSea(QueryRequestBo queryRequestBo) throws BizException, QueryException;

    QueryResultBo<QueryCustomerBo> findByDepartmentOpenSea(Integer departmentId, QueryRequestBo queryRequestBo) throws QueryException;

    OptCustomerResultBo transferCustomer(CustomerTransferRequestBo transferBo) throws BizException;
}
