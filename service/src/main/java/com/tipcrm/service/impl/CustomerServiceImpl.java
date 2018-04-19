package com.tipcrm.service.impl;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.tipcrm.bo.ApproveAction;
import com.tipcrm.bo.ApproveBo;
import com.tipcrm.bo.CreateCustomerBo;
import com.tipcrm.bo.CustomerTransferRequestBo;
import com.tipcrm.bo.OperationType;
import com.tipcrm.bo.OptCustomerResultBo;
import com.tipcrm.bo.QueryCriteriaBo;
import com.tipcrm.bo.QueryCustomerBo;
import com.tipcrm.bo.QueryRequestBo;
import com.tipcrm.bo.QueryResultBo;
import com.tipcrm.bo.QuerySortBo;
import com.tipcrm.constant.ApprovalStatus;
import com.tipcrm.constant.ApprovalType;
import com.tipcrm.constant.Constants;
import com.tipcrm.constant.CriteriaMethod;
import com.tipcrm.constant.CustomerStatus;
import com.tipcrm.constant.CustomerTransferTarget;
import com.tipcrm.constant.ListBoxCategory;
import com.tipcrm.constant.NotificationType;
import com.tipcrm.constant.OptCustomerResultType;
import com.tipcrm.constant.ReviewType;
import com.tipcrm.dao.entity.ApprovalRequest;
import com.tipcrm.dao.entity.Communication;
import com.tipcrm.dao.entity.Customer;
import com.tipcrm.dao.entity.CustomerApproval;
import com.tipcrm.dao.entity.CustomerContact;
import com.tipcrm.dao.entity.Department;
import com.tipcrm.dao.entity.ListBox;
import com.tipcrm.dao.entity.User;
import com.tipcrm.dao.repository.ApprovalRequestRepository;
import com.tipcrm.dao.repository.CustomerApprovalRepository;
import com.tipcrm.dao.repository.CustomerContactRepository;
import com.tipcrm.dao.repository.CustomerRepository;
import com.tipcrm.dao.repository.DepartmentRepository;
import com.tipcrm.dao.repository.UserRepository;
import com.tipcrm.exception.BizException;
import com.tipcrm.exception.QueryException;
import com.tipcrm.service.ApprovalService;
import com.tipcrm.service.CustomerService;
import com.tipcrm.service.ListBoxService;
import com.tipcrm.service.NotificationService;
import com.tipcrm.service.PermissionService;
import com.tipcrm.service.UserService;
import com.tipcrm.service.WebContext;
import com.tipcrm.util.MessageUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private static Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CustomerApprovalRepository customerApprovalRepository;
    @Autowired
    private CustomerContactRepository customerContactRepository;
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private WebContext webContext;
    @Autowired
    private ListBoxService listBoxService;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private ApprovalService approvalService;
    @Autowired
    private ApprovalRequestRepository approvalRequestRepository;

    @Override
    public OptCustomerResultBo createNewCustomer(CreateCustomerBo createCustomerBo) {
        validateCreateNewCustomer(createCustomerBo);
        CustomerApproval approval = createNewCustomerApprovalByAddMethod(createCustomerBo);
        if (hasPermissionToApproveCustomer(approval.getId())) {
            ApproveBo approveBo = new ApproveBo();
            approveBo.setAction(ApproveAction.APPROVE.name());
            approveBo.setTargetId(approval.getId());
            Integer customerId = approveCustomer(approveBo, false);
            return new OptCustomerResultBo(OptCustomerResultType.CUSTOMER.name(), customerId);
        } else {
            // todo : add notification
            return new OptCustomerResultBo(OptCustomerResultType.CUSTOMER_APPROVAL.name(), approval.getId());
        }
    }

    private CustomerApproval createNewCustomerApprovalByAddMethod(CreateCustomerBo createCustomerBo) {
        Date date = new Date();
        User currentUser = webContext.getCurrentUser();
        CustomerApproval customerApproval = new CustomerApproval();
        customerApproval.setName(createCustomerBo.getName());
        customerApproval.setAddress(createCustomerBo.getAddress());
        customerApproval.setNote(createCustomerBo.getNote());
        customerApproval.setEntryUser(currentUser);
        customerApproval.setEntryTime(date);
        ListBox fastApprovalType = listBoxService.findByCategoryAndName(ListBoxCategory.REVIEW_TYPE.name(), ReviewType.FAST.name());
        customerApproval.setReviewType(fastApprovalType);
        ListBox newCustomer = listBoxService.findByCategoryAndName(ListBoxCategory.CUSTOMER_STATUS.name(), CustomerStatus.NEW_CUSTOMER.name());
        customerApproval.setStatus(newCustomer);
        ListBox addOpt = listBoxService.findByCategoryAndName(ListBoxCategory.OPERATION_TYPE.name(), OperationType.ADD.name());
        customerApproval.setOptType(addOpt);
        ListBox reviewStatusPending = listBoxService.findByCategoryAndName(ListBoxCategory.APPROVAL_STATUS.name(), ApprovalStatus.PENDING.name());
        customerApproval.setReviewStatus(reviewStatusPending);
        customerApproval = approvalService.saveApprovalRequest(customerApproval);

        if (StringUtils.isNotBlank(createCustomerBo.getContactName())) {
            CustomerContact customerContact = new CustomerContact();
            customerContact.setCustomerApproval(customerApproval);
            customerContact.setName(createCustomerBo.getContactName());
            customerContact.setPhoneNo(createCustomerBo.getContactPhone());
            customerContact.setEntryUser(currentUser);
            customerContact.setEntryTime(date);
            customerContact.setActive(true);
            customerContactRepository.save(customerContact);
        }
        return customerApproval;
    }

    public void validateCreateNewCustomer(CreateCustomerBo createCustomerBo) {
        if (StringUtils.isBlank(createCustomerBo.getName())) {
            throw new BizException("客户名不能为空");
        }
        if (!StringUtils.isBlank(createCustomerBo.getContactName()) && StringUtils.isBlank(createCustomerBo.getContactPhone())
            || StringUtils.isBlank(createCustomerBo.getContactName()) && !StringUtils.isBlank(createCustomerBo.getContactPhone())) {
            throw new BizException("联系人和电话应该都填或都不填");
        }
        List<Customer> customers = customerRepository.findByNameAndDeleteTimeIsNull(createCustomerBo.getName());
        if (!CollectionUtils.isEmpty(customers)) {
            throw new BizException("客户已存在");
        }
    }

    @Override
    public Integer approveCustomer(ApproveBo approveBo) {
        return approveCustomer(approveBo, true);
    }

    private Integer approveCustomer(ApproveBo approveBo, Boolean needNotify) {
        validateApproveBo(approveBo);
        if (!hasPermissionToApproveCustomer(approveBo.getTargetId())) {
            throw new BizException("您没有权限审批这个申请");
        }
        CustomerApproval customerApproval = customerApprovalRepository.findOne(approveBo.getTargetId());
        if (ReviewType.FAST.name().equals(customerApproval.getReviewType().getName())) {
            return approvalCustomerByFast(approveBo, needNotify);
        } else {
            throw new BizException("目前只支持快速审批");
        }
    }

    private Boolean hasPermissionToApproveCustomer(Integer approvalId) {
        ListBox approvalType = listBoxService.findByCategoryAndName(ListBoxCategory.APPROVAL_TYPE.name(), ApprovalType.CUSTOMER.name());
        User currentUser = webContext.getCurrentUser();
        List<ApprovalRequest> approvalRequests = approvalRequestRepository.findByApprovalTypeIdAndApprovalId(approvalType.getId(), approvalId);
        for (ApprovalRequest approvalRequest : approvalRequests) {
            if (approvalRequest.getReviewer().getId().equals(currentUser.getId())) {
                if (approvalRequest.getReviewTime() != null) {
                    throw new BizException("您已经审批了这个申请");
                } else {
                    return true;
                }
            }
        }
        return false;
    }

    private Integer approvalCustomerByFast(ApproveBo approveBo, Boolean needNotify) {
        Date date = new Date();
        User user = webContext.getCurrentUser();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        CustomerApproval customerApproval = customerApprovalRepository.findOne(approveBo.getTargetId());
        ListBox approveTypeCustomer = listBoxService.findByCategoryAndName(ListBoxCategory.APPROVAL_TYPE.name(), ApprovalType.CUSTOMER.name());
        ApprovalRequest request = approvalRequestRepository.findByApprovalTypeIdAndApprovalIdAndReviewerId(approveTypeCustomer.getId(), approveBo.getTargetId(),
                                                                                                           webContext.getCurrentUserId());
        request.setReviewTime(date);
        request.setReviewNote(approveBo.getNote());
        Integer customerId = null;
        if (ApproveAction.REJECT.name().equals(approveBo.getAction())) {
            ListBox rejectStatus = listBoxService.findByCategoryAndName(ListBoxCategory.APPROVAL_STATUS.name(), ApprovalStatus.REJECTED.name());
            customerApproval.setReviewStatus(rejectStatus);
            customerApproval.setFinalApprovalTime(date);
            customerApprovalRepository.save(customerApproval);

            request.setReviewStatus(rejectStatus);
            approvalRequestRepository.save(request);
            if (needNotify) {
                notificationService.createNotification(customerApproval.getEntryUser().getId(),
                                                       MessageUtil.getMessage(Constants.Notification.Subject.NEW_CUSTOMER_APPROVAL, "已驳回"),
                                                       MessageUtil.getMessage(Constants.Notification.Content.NEW_CUSTOMER_APPROVAL, "被驳回",
                                                                              customerApproval.getId().toString(), user.getUserName(),
                                                                              dateFormat.format(date), request.getReviewNote()),
                                                       NotificationType.SYSTEM_NOTIFICATION);
            }
        } else {
            ListBox approve = listBoxService.findByCategoryAndName(ListBoxCategory.APPROVAL_STATUS.name(), ApprovalStatus.APPROVED.name());
            request.setReviewStatus(approve);
            approvalRequestRepository.save(request);
            Customer customer = null;
            if (OperationType.REMOVE.name().equals(customerApproval.getOptType().getName())) {
                customer = customerApproval.getCustomer();
                customer.setDeleteUser(customerApproval.getEntryUser());
                customer.setDeleteTime(customerApproval.getEntryTime());
                customerRepository.save(customer);
                customerId = customer.getId();
            } else {
                List<Customer> existCustomer = customerRepository.findByNameAndDeleteTimeIsNull(customerApproval.getName());
                if (!CollectionUtils.isEmpty(existCustomer)) {
                    throw new BizException("客户名称已存在");
                }
                if (OperationType.ADD.name().equals(customerApproval.getOptType().getName())) {
                    customer = new Customer();
                    customer.setEntryUser(customerApproval.getEntryUser());
                    customer.setEntryTime(customerApproval.getEntryTime());
                } else {
                    customer = customerApproval.getCustomer();
                    customer.setUpdateUser(customerApproval.getEntryUser());
                    customer.setUpdateTime(customerApproval.getEntryTime());
                }
                customer.setName(customerApproval.getName());
                customer.setNote(customerApproval.getNote());
                customer.setAddress(customerApproval.getAddress());
                customer.setStatus(customerApproval.getStatus());
                customer.setFollowDepartment(customerApproval.getFollowDepartment());
                customer.setFollowUser(customerApproval.getFollowUser());
                customerRepository.save(customer);
                customerId = customer.getId();
                // find contact
                List<CustomerContact> customerContacts = customerContactRepository.findByCustomerApprovalId(customerApproval.getId());
                for (CustomerContact customerContact : customerContacts) {
                    customerContact.setCustomerApproval(null);
                    customerContact.setCustomer(customer);
                }
                customerContactRepository.save(customerContacts);
                if (needNotify) {
                    notificationService.createNotification(customerApproval.getEntryUser().getId(),
                                                           MessageUtil.getMessage(Constants.Notification.Subject.NEW_CUSTOMER_APPROVAL, "已通过"),
                                                           MessageUtil.getMessage(Constants.Notification.Content.NEW_CUSTOMER_APPROVAL, "通过",
                                                                                  customerApproval.getId().toString(), user.getUserName(),
                                                                                  dateFormat.format(date), request.getReviewNote()),
                                                           NotificationType.SYSTEM_NOTIFICATION);
                }
            }
            customerApproval.setReviewStatus(approve);
            customerApproval.setFinalApprovalTime(date);
            customerApproval.setCustomer(customer);
            customerApprovalRepository.save(customerApproval);
        }
        return customerId;
    }

    @Override
    public OptCustomerResultBo deleteCustomer(Integer customerId) {
        Customer customer = customerRepository.findOne(customerId);
        if (customer == null) {
            throw new BizException("客户不存在");
        }
        if (customer.getDeleteTime() != null) {
            throw new BizException("客户已删除");
        }
        CustomerApproval approval = createCustomerByApprovalByRemoveMethod(customer);
        Set<String> permissions = permissionService.getPermissionValuesByUserId(webContext.getCurrentUserId());
        if (!permissions.contains(Constants.Permission.CUSTOMER_APPROVAL)) {
            //todo : add notification
            return new OptCustomerResultBo(OptCustomerResultType.CUSTOMER_APPROVAL.name(), approval.getId());
        } else {
            // approve
            ApproveBo approveBo = new ApproveBo();
            approveBo.setAction(ApproveAction.APPROVE.name());
            approveBo.setTargetId(approval.getId());
            customerId = approveCustomer(approveBo, false);
            return new OptCustomerResultBo(OptCustomerResultType.CUSTOMER.name(), customerId);
        }
    }

    private CustomerApproval buildRemoveCustomerApproval(Customer customer) {
        User user = webContext.getCurrentUser();
        Date now = new Date();
        CustomerApproval customerApproval = new CustomerApproval();
        ListBox removeOptType = listBoxService.findByCategoryAndName(ListBoxCategory.OPERATION_TYPE.name(), OperationType.REMOVE.name());
        customerApproval.setOptType(removeOptType);
        customerApproval.setCustomer(customer);
        customerApproval.setEntryUser(user);
        customerApproval.setEntryTime(now);
        customerApproval.setName(customer.getName());
        customerApproval.setStatus(customer.getStatus());
        ListBox pending = listBoxService.findByCategoryAndName(ListBoxCategory.APPROVAL_STATUS.name(), ApprovalStatus.PENDING.name());
        customerApproval.setReviewStatus(pending);
        ListBox fast = listBoxService.findByCategoryAndName(ListBoxCategory.REVIEW_TYPE.name(), ReviewType.FAST.name());
        customerApproval.setReviewType(fast);
        return customerApproval;
    }

    private CustomerApproval createCustomerByApprovalByRemoveMethod(Customer customer) {
        CustomerApproval customerApproval = buildRemoveCustomerApproval(customer);
        customerApprovalRepository.save(customerApproval);
        approvalService.saveApprovalRequest(customerApproval);
        return customerApproval;
    }

    private void validateApproveBo(ApproveBo approveBo) {
        if (approveBo.getTargetId() == null) {
            throw new BizException("审批目标为空");
        }
        ListBox reviewStatusPending = listBoxService.findByCategoryAndName(ListBoxCategory.APPROVAL_STATUS.name(), ApprovalStatus.PENDING.name());
        CustomerApproval customerApproval = customerApprovalRepository.findByIdAndReviewStatusId(approveBo.getTargetId(), reviewStatusPending.getId());
        if (customerApproval == null) {
            throw new BizException("审批目标不存在或者已经审批");
        }
        if (!ApproveAction.REJECT.name().equals(approveBo.getAction())
            && !ApproveAction.APPROVE.name().equals(approveBo.getAction())) {
            throw new BizException("不支持的操作");
        }
    }

    @Override
    public QueryResultBo<QueryCustomerBo> findMyCustomers(final QueryRequestBo queryRequestBo) {
        List<QueryCriteriaBo> queryCriteriaBos = queryRequestBo.getCriteria();
        if (!CollectionUtils.isEmpty(queryCriteriaBos)) {
            Set<String> fieldSet = Sets.newHashSet();
            for (QueryCriteriaBo queryCriteriaBo : queryCriteriaBos) {
                fieldSet.add(queryCriteriaBo.getFieldName());
            }
            if (fieldSet.size() != queryCriteriaBos.size()) {
                throw new BizException("查询字段有重复");
            }
            Iterator<QueryCriteriaBo> iterator = queryCriteriaBos.iterator();
            while (iterator.hasNext()) {
                QueryCriteriaBo queryCriteriaBo = iterator.next();
                if (Constants.QueryFieldName.Customer.FOLLOW_USER.equals(queryCriteriaBo.getFieldName())
                    || Constants.QueryFieldName.Customer.FOLLOW_DEPARTMENT.equals(queryCriteriaBo.getFieldName())) {
                    iterator.remove();
                }
            }
        }
        final Integer userId = webContext.getCurrentUserId();
        final QueryCriteriaBo queryCriteriaBo = new QueryCriteriaBo();
        queryCriteriaBo.setFieldName(Constants.QueryFieldName.Customer.FOLLOW_USER);
        queryCriteriaBo.setMethod(CriteriaMethod.EQUALS);
        queryCriteriaBo.setValue(userId);
        if (CollectionUtils.isEmpty(queryRequestBo.getCriteria())) {
            queryRequestBo.setCriteria(Lists.newArrayList(queryCriteriaBo));
        } else {
            queryRequestBo.getCriteria().add(queryCriteriaBo);
        }
        try {
            QuerySortBo querySortBo = queryRequestBo.getSort();
            PageRequest page;
            if (querySortBo == null) {
                page = new PageRequest(queryRequestBo.getPage() - 1, queryRequestBo.getSize());
            } else {
                page = new PageRequest(queryRequestBo.getPage() - 1, queryRequestBo.getSize(),
                                       new Sort(querySortBo.getDirection(),
                                                Constants.SortFieldName.Customer.fieldMap.get(querySortBo.getFieldName())));
            }
            Specification<Customer> specification = new CustomerSpecification(queryRequestBo);
            Page<Customer> customers = customerRepository.findAll(specification, page);
            List<QueryCustomerBo> customerBos = convertToQueryCustomerResultBos(customers.getContent());
            QueryResultBo<QueryCustomerBo> queryResultBo = new QueryResultBo<>(customerBos, queryRequestBo.getPage(), queryRequestBo.getSize(),
                                                                               customers.getTotalElements(), customers.getTotalPages());
            return queryResultBo;
        } catch (Exception e) {
            throw new QueryException("查询条件错误", e);
        }
    }

    private List<QueryCustomerBo> convertToQueryCustomerResultBos(List<Customer> customers) {
        if (CollectionUtils.isEmpty(customers)) {
            return null;
        }
        List<QueryCustomerBo> customerResultBos = new ArrayList<QueryCustomerBo>();
        for (Customer customer : customers) {
            customerResultBos.add(convertToQueryCustomerResultBo(customer));
        }
        return customerResultBos;
    }

    private QueryCustomerBo convertToQueryCustomerResultBo(Customer customer) {
        if (customer == null) {
            return null;
        }
        QueryCustomerBo customerBo = new QueryCustomerBo();
        customerBo.setCustomerId(customer.getId());
        customerBo.setCustomerName(customer.getName());
        customerBo.setStatus(customer.getStatus().getDisplayName());
        List<CustomerContact> customerContacts = customerContactRepository.findByCustomerIdOrderByEntryTimeDesc(customer.getId());
        if (!CollectionUtils.isEmpty(customerContacts)) {
            CustomerContact contact = customerContacts.get(0);
            customerBo.setContactId(contact.getId());
            customerBo.setContactName(contact.getName());
            customerBo.setContactPhone(contact.getPhoneNo());
        }
        Communication communication = customer.getLastCommunication();
        if (communication != null) {
            customerBo.setLastCommunicationTime(communication.getCommunicateTime());
            customerBo.setLastCommunicationContent(communication.getNote());
            customerBo.setNextCommunicationTime(communication.getNextCommunicateTime());
        }
        // todo: query intentional amount and sign amount
        // customerBo.setIntentionalAmount(customer.getValue);
        // customerBo.setSignAmount(customer.getValue);
        return customerBo;
    }

    @Override
    public QueryResultBo<QueryCustomerBo> findByMyDepartmentOpenSea(QueryRequestBo queryRequestBo) {
        User user = webContext.getCurrentUser();
        Department department = user.getDepartment();
        if (department == null) {
            throw new BizException("您不属于任何一个部门");
        } else {
            return findByDepartmentOpenSea(department.getId(), queryRequestBo);
        }
    }

    @Override
    public QueryResultBo<QueryCustomerBo> findByDepartmentOpenSea(Integer departmentId, QueryRequestBo queryRequestBo) {
        List<QueryCriteriaBo> queryCriteriaBos = queryRequestBo.getCriteria();
        if (!CollectionUtils.isEmpty(queryCriteriaBos)) {
            Set<String> fieldSet = Sets.newHashSet();
            for (QueryCriteriaBo queryCriteriaBo : queryCriteriaBos) {
                fieldSet.add(queryCriteriaBo.getFieldName());
            }
            if (fieldSet.size() != queryCriteriaBos.size()) {
                throw new BizException("查询字段有重复");
            }
            Iterator<QueryCriteriaBo> iterator = queryCriteriaBos.iterator();
            while (iterator.hasNext()) {
                QueryCriteriaBo queryCriteriaBo = iterator.next();
                if (Constants.QueryFieldName.Customer.FOLLOW_USER.equals(queryCriteriaBo.getFieldName())
                    || Constants.QueryFieldName.Customer.FOLLOW_DEPARTMENT.equals(queryCriteriaBo.getFieldName())) {
                    iterator.remove();
                }
            }
        }
        QueryCriteriaBo queryCriteriaBo = new QueryCriteriaBo();
        queryCriteriaBo.setFieldName(Constants.QueryFieldName.Customer.FOLLOW_DEPARTMENT);
        queryCriteriaBo.setMethod(CriteriaMethod.EQUALS);
        queryCriteriaBo.setValue(departmentId);
        if (CollectionUtils.isEmpty(queryRequestBo.getCriteria())) {
            queryRequestBo.setCriteria(Lists.newArrayList(queryCriteriaBo));
        } else {
            queryRequestBo.getCriteria().add(queryCriteriaBo);
        }
        try {
            QuerySortBo querySortBo = queryRequestBo.getSort();
            PageRequest page;
            if (querySortBo == null) {
                page = new PageRequest(queryRequestBo.getPage() - 1, queryRequestBo.getSize());
            } else {
                page = new PageRequest(queryRequestBo.getPage() - 1, queryRequestBo.getSize(),
                                       new Sort(querySortBo.getDirection(),
                                                Constants.SortFieldName.Customer.fieldMap.get(querySortBo.getFieldName())));
            }
            Specification<Customer> specification = new CustomerSpecification(queryRequestBo);
            Page<Customer> customers = customerRepository.findAll(specification, page);
            List<QueryCustomerBo> customerBos = convertToQueryCustomerResultBos(customers.getContent());
            QueryResultBo<QueryCustomerBo> queryResultBo = new QueryResultBo<QueryCustomerBo>(customerBos, queryRequestBo.getPage(), queryRequestBo.getSize(),
                                                                                              customers.getTotalElements(), customers.getTotalPages());
            return queryResultBo;
        } catch (Exception e) {
            throw new QueryException("查询条件错误", e);
        }
    }

    @Override
    public OptCustomerResultBo transferCustomer(CustomerTransferRequestBo transferBo) {
        User currentUser = webContext.getCurrentUser();
        if (currentUser.getDepartment() == null && !userService.isGeneralManager(currentUser.getId())) {
            throw new BizException("您不属于任何一个部门，不允许进行客户转移操作");
        }
        validateTransferBo(transferBo);
        // todo : refactor and add notification
        Set<String> permissions = permissionService.getPermissionValuesByUserId(webContext.getCurrentUserId());
        if (permissions.contains(Constants.Permission.CUSTOMER_APPROVAL)) {
            return new OptCustomerResultBo(OptCustomerResultType.CUSTOMER.name(), transferCustomerByApproval(transferBo));
        } else {
            return new OptCustomerResultBo(OptCustomerResultType.CUSTOMER_APPROVAL.name(), transferCustomerByNormal(transferBo));
        }
    }

    private void validateTransferBo(CustomerTransferRequestBo transferBo) {
        User currentUser = webContext.getCurrentUser();
        if (transferBo.getTarget() == null) {
            throw new BizException("没有提供转移目标类型");
        }
        if (transferBo.getCustomerId() == null) {
            throw new BizException("没有提供转移对象");
        }
        if (!CustomerTransferTarget.COMMON_OPEN_SEA.equals(transferBo.getTarget())
            && transferBo.getTargetId() == null) {
            throw new BizException("没有提供转移目标");
        }
        Customer customer = customerRepository.findOne(transferBo.getCustomerId());
        if (customer == null) {
            throw new BizException("该客户不存在");
        }
        if (customer.getFollowUser() != null && !customer.getFollowUser().getId().equals(currentUser.getId())) {
            throw new BizException("该客户已被分配给其他业务员");
        }
        if (customer.getFollowDepartment() != null && !userService.isGeneralManager(currentUser.getId())) {
            Department currentDepartment = currentUser.getDepartment();
            if (!customer.getFollowDepartment().getId().equals(currentDepartment.getId())) {
                throw new BizException("您只能操作自己部门的客户");
            }

        }
        if (CustomerTransferTarget.DEPARTMENT_OPEN_SEA.equals(transferBo.getTarget())) {
            Department department = departmentRepository.findOne(transferBo.getTargetId());
            if (department == null) {
                throw new BizException("目标部门不存在");
            }
        }
        if (CustomerTransferTarget.USER.equals(transferBo.getTarget())) {
            User user = userRepository.findOne(transferBo.getTargetId());
            if (user == null) {
                throw new BizException("目标用户不存在");
            }
        }
    }

    private Integer transferCustomerByApproval(CustomerTransferRequestBo transferBo) {
        return null;
    }

    private Integer transferCustomerByNormal(CustomerTransferRequestBo transferBo) {
        return null;
    }

    static class CustomerSpecification implements Specification<Customer> {
        private QueryRequestBo queryRequestBo;
        private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        public CustomerSpecification(QueryRequestBo queryRequestBo) {
            this.queryRequestBo = queryRequestBo;
        }

        @Override
        public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
            List<Predicate> predicates = new ArrayList<Predicate>();
            if (!CollectionUtils.isEmpty(queryRequestBo.getCriteria())) {
                for (QueryCriteriaBo criteria : queryRequestBo.getCriteria()) {
                    Path path = null;
                    switch (criteria.getFieldName()) {
                        case Constants.QueryFieldName.Customer.CUSTOMER_NAME:
                            path = root.get("name");
                            String name = (String) criteria.getValue();
                            if (StringUtils.isNotBlank(name)) {
                                predicates.add(criteriaBuilder.like(path, "%" + name + "%"));
                            }
                            break;
                        case Constants.QueryFieldName.Customer.STATUS:
                            path = root.get("status").get("id");
                            List statuses = (List) criteria.getValue();
                            if (!CollectionUtils.isEmpty(statuses)) {
                                predicates.add(path.in(statuses.toArray()));
                            }
                            break;
                        case Constants.QueryFieldName.Customer.LAST_COMMUNICATION_TIME:
                            path = root.get("lastCommunication").get("communicateTime");
                            List<String> lastCommunicationTime = (List<String>) criteria.getValue();
                            if (!CollectionUtils.isEmpty(lastCommunicationTime) && lastCommunicationTime.size() == 2) {
                                try {
                                    Date from = sdf.parse(lastCommunicationTime.get(0));
                                    Date to = sdf.parse(lastCommunicationTime.get(1));
                                    predicates.add(criteriaBuilder.between(path, from, to));
                                } catch (ParseException e) {
                                    logger.error("日期转换失败，略过该查询条件", e);
                                }
                            }
                            break;
                        case Constants.QueryFieldName.Customer.LAST_COMMUNICATION_CONTENT:
                            path = root.get("lastCommunication").get("note");
                            String lastCommunicationNote = (String) criteria.getValue();
                            if (StringUtils.isNotBlank(lastCommunicationNote)) {
                                predicates.add(criteriaBuilder.like(path, "%" + lastCommunicationNote + "%"));
                            }
                            break;
                        case Constants.QueryFieldName.Customer.NEXT_COMMUNICATION_TIME:
                            path = root.get("lastCommunication").get("nextCommunicateTime");
                            List<String> nextCommunicateTime = (List<String>) criteria.getValue();
                            if (!CollectionUtils.isEmpty(nextCommunicateTime) && nextCommunicateTime.size() == 2) {
                                try {
                                    Date from = sdf.parse(nextCommunicateTime.get(0));
                                    Date to = sdf.parse(nextCommunicateTime.get(1));
                                    predicates.add(criteriaBuilder.between(path, from, to));
                                } catch (ParseException e) {
                                    logger.error("日期转换失败，略过该查询条件", e);
                                }
                            }
                            break;
                        case Constants.QueryFieldName.Customer.FOLLOW_USER:
                            path = root.get("followUser").get("id");
                            Integer followUserId = (Integer) criteria.getValue();
                            if (followUserId != null) {
                                predicates.add(criteriaBuilder.equal(path, followUserId));
                            }
                            break;
                        case Constants.QueryFieldName.Customer.FOLLOW_DEPARTMENT:
                            path = root.get("followDepartment").get("id");
                            Integer followDepartmentId = (Integer) criteria.getValue();
                            if (followDepartmentId != null) {
                                predicates.add(criteriaBuilder.equal(path, followDepartmentId));
                            }
                            break;
                        default:
                            break;
                    }
                }
                Predicate[] pre = new Predicate[predicates.size()];
                criteriaQuery.where(predicates.toArray(pre));
            }
            return criteriaQuery.getRestriction();
        }
    }
}
