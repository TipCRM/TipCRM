package com.tipcrm.service.impl;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.google.common.collect.Lists;
import com.tipcrm.bo.ApproveAction;
import com.tipcrm.bo.ApproveBo;
import com.tipcrm.bo.CreateCustomerBo;
import com.tipcrm.bo.OperationType;
import com.tipcrm.bo.OptCustomerResultBo;
import com.tipcrm.constant.ApprovalStatus;
import com.tipcrm.constant.Constants;
import com.tipcrm.constant.CustomerStatus;
import com.tipcrm.constant.ListBoxCategory;
import com.tipcrm.constant.NotificationType;
import com.tipcrm.constant.OptCustomerResultType;
import com.tipcrm.dao.entity.Customer;
import com.tipcrm.dao.entity.CustomerApproval;
import com.tipcrm.dao.entity.CustomerContact;
import com.tipcrm.dao.entity.ListBox;
import com.tipcrm.dao.entity.User;
import com.tipcrm.dao.repository.CustomerApprovalRepository;
import com.tipcrm.dao.repository.CustomerContactRepository;
import com.tipcrm.dao.repository.CustomerRepository;
import com.tipcrm.dao.repository.ListBoxRepository;
import com.tipcrm.exception.BizException;
import com.tipcrm.service.CustomerService;
import com.tipcrm.service.NotificationService;
import com.tipcrm.service.PermissionService;
import com.tipcrm.service.WebContext;
import com.tipcrm.util.MessageUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

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
    private ListBoxRepository listBoxRepository;

    @Autowired
    private NotificationService notificationService;

    @Override
    public OptCustomerResultBo createNewCustomer(CreateCustomerBo createCustomerBo) throws BizException {
        validateCreateNewCustomer(createCustomerBo);
        Set<String> permissions = permissionService.getPermissionValueListByUserId(webContext.getCurrentUserId());
        if (permissions.contains(Constants.Permission.CUSTOMER_APPROVAL)) {
            return new OptCustomerResultBo(OptCustomerResultType.CUSTOMER.name(), createNewCustomerByApproval(createCustomerBo));
        }
        return new OptCustomerResultBo(OptCustomerResultType.CUSTOMER_APPROVAL.name(), createNewCustomerByNormal(createCustomerBo));
    }

    private Integer createNewCustomerByNormal(CreateCustomerBo createCustomerBo) {
        Date date = new Date();
        User currentUser = webContext.getCurrentUser();
        CustomerApproval customerApproval = new CustomerApproval();
        customerApproval.setName(createCustomerBo.getName());
        customerApproval.setAddress(createCustomerBo.getAddress());
        customerApproval.setNote(createCustomerBo.getNote());
        customerApproval.setEntryUser(currentUser);
        customerApproval.setEntryTime(date);
        ListBox newCustomer = listBoxRepository.findByCategoryNameAndName(ListBoxCategory.CUSTOMER_STATUS.name(), CustomerStatus.NEW_CUSTOMER.name());
        customerApproval.setStatus(newCustomer);
        ListBox addOpt = listBoxRepository.findByCategoryNameAndName(ListBoxCategory.OPERATION_TYPE.name(), OperationType.ADD.name());
        customerApproval.setOptType(addOpt);
        customerApproval = customerApprovalRepository.save(customerApproval);
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
        return customerApproval.getId();
    }

    private Integer createNewCustomerByApproval(CreateCustomerBo createCustomerBo) {
        Date date = new Date();
        User currentUser = webContext.getCurrentUser();
        Customer customer = new Customer();
        CustomerApproval customerApproval = new CustomerApproval();
        customer.setName(createCustomerBo.getName());
        customerApproval.setName(createCustomerBo.getName());
        customer.setAddress(createCustomerBo.getAddress());
        customerApproval.setAddress(createCustomerBo.getAddress());
        customer.setNote(createCustomerBo.getNote());
        customerApproval.setNote(createCustomerBo.getNote());
        customer.setEntryUser(currentUser);
        customerApproval.setEntryUser(currentUser);
        customer.setEntryTime(date);
        customerApproval.setEntryTime(date);
        customerApproval.setReviewer(currentUser);
        customerApproval.setReviewTime(date);
        ListBox approve = listBoxRepository.findByCategoryNameAndName(ListBoxCategory.APPROVAL_STATUS.name(), ApprovalStatus.APPROVED.name());
        customerApproval.setReviewStatus(approve);
        ListBox newCustomer = listBoxRepository.findByCategoryNameAndName(ListBoxCategory.CUSTOMER_STATUS.name(), CustomerStatus.NEW_CUSTOMER.name());
        customer.setStatus(newCustomer);
        customerApproval.setStatus(newCustomer);
        customerApproval.setCustomer(customer);
        ListBox addOpt = listBoxRepository.findByCategoryNameAndName(ListBoxCategory.OPERATION_TYPE.name(), OperationType.ADD.name());
        customerApproval.setOptType(addOpt);
        customerApprovalRepository.save(customerApproval);
        customer = customerRepository.save(customer);
        if (StringUtils.isNotBlank(createCustomerBo.getContactName())) {
            CustomerContact customerContact = new CustomerContact();
            customerContact.setCustomer(customer);
            customerContact.setName(createCustomerBo.getContactName());
            customerContact.setPhoneNo(createCustomerBo.getContactPhone());
            customerContact.setEntryUser(currentUser);
            customerContact.setEntryTime(date);
            customerContact.setActive(true);
            customerContactRepository.save(customerContact);
        }
        return customer.getId();
    }

    public void validateCreateNewCustomer(CreateCustomerBo createCustomerBo) throws BizException {
        if (StringUtils.isBlank(createCustomerBo.getName())) {
            throw new BizException("客户名不能为空");
        }
        if ((!StringUtils.isBlank(createCustomerBo.getContactName()) && StringUtils.isBlank(createCustomerBo.getContactPhone()))
            || (StringUtils.isBlank(createCustomerBo.getContactName()) && !StringUtils.isBlank(createCustomerBo.getContactPhone()))) {
            throw new BizException("联系人和电话应该都填或都不填");
        }
        List<Customer> customers = customerRepository.findByNameAndDeleteTimeIsNull(createCustomerBo.getName());
        if (!CollectionUtils.isEmpty(customers)) {
            throw new BizException("客户已存在");
        }
    }

    @Override
    public Integer approveCustomer(ApproveBo approveBo) throws BizException {
        validateApproveBo(approveBo);
        Date date = new Date();
        User user = webContext.getCurrentUser();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        CustomerApproval customerApproval = customerApprovalRepository.findOne(approveBo.getTargetId());
        customerApproval.setReviewer(user);
        customerApproval.setReviewTime(date);
        customerApproval.setReviewNote(approveBo.getNote());
        Integer customerId = null;
        if (approveBo.getAction().equals(ApproveAction.REJECT.name())) {
            ListBox rejectStatus = listBoxRepository.findByCategoryNameAndName(ListBoxCategory.APPROVAL_STATUS.name(), ApprovalStatus.REJECTED.name());
            customerApproval.setReviewStatus(rejectStatus);
            customerApprovalRepository.save(customerApproval);
            notificationService.createNotification(customerApproval.getEntryUser().getId(),
                                                   MessageUtil.getMessage(Constants.Notification.Subject.NEW_CUSTOMER_APPROVAL, "已驳回"),
                                                   MessageUtil.getMessage(Constants.Notification.Content.NEW_CUSTOMER_APPROVAL, "被驳回",
                                                                          customerApproval.getId().toString(), user.getUserName(),
                                                                          dateFormat.format(date), customerApproval.getReviewNote()),
                                                   NotificationType.SYSTEM_NOTIFICATION);
        } else {
            ListBox approve = listBoxRepository.findByCategoryNameAndName(ListBoxCategory.APPROVAL_STATUS.name(), ApprovalStatus.APPROVED.name());
            customerApproval.setReviewStatus(approve);
            customerApprovalRepository.save(customerApproval);
            if (OperationType.REMOVE.name().equals(customerApproval.getOptType().getName())) {
                Customer customer = customerApproval.getCustomer();
                customer.setDeleteUser(customerApproval.getEntryUser());
                customer.setDeleteTime(customerApproval.getEntryTime());
                customerRepository.save(customer);
                customerId = customer.getId();
            } else {
                List<Customer> existCustomer = customerRepository.findByNameAndDeleteTimeIsNull(customerApproval.getName());
                if (!CollectionUtils.isEmpty(existCustomer)) {
                    throw new BizException("客户名称已存在");
                }
                Customer customer = null;
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
                notificationService.createNotification(customerApproval.getEntryUser().getId(),
                                                       MessageUtil.getMessage(Constants.Notification.Subject.NEW_CUSTOMER_APPROVAL, "已通过"),
                                                       MessageUtil.getMessage(Constants.Notification.Content.NEW_CUSTOMER_APPROVAL, "被通过",
                                                                              customerApproval.getId().toString(), user.getUserName(),
                                                                              dateFormat.format(date), customerApproval.getReviewNote()),
                                                       NotificationType.SYSTEM_NOTIFICATION);
            }
        }
        return customerId;
    }

    @Override
    public OptCustomerResultBo deleteCustomer(Integer customerId) throws BizException {
        Customer customer = customerRepository.findOne(customerId);
        if (customer == null) {
            throw new BizException("客户不存在");
        }
        if (customer.getDeleteTime() != null) {
            throw new BizException("客户已删除");
        }
        Set<String> permissions = permissionService.getPermissionValueListByUserId(webContext.getCurrentUserId());
        if (permissions.contains(Constants.Permission.CUSTOMER_APPROVAL)) {
            return new OptCustomerResultBo(OptCustomerResultType.CUSTOMER.name(), deleteCustomerByApproval(customer));
        }
        return new OptCustomerResultBo(OptCustomerResultType.CUSTOMER_APPROVAL.name(), deleteCustomerByNormal(customer));
    }

    private Integer deleteCustomerByApproval(Customer customer) {
        User user = webContext.getCurrentUser();
        Date now = new Date();
        CustomerApproval customerApproval = buildCustomerApproval(customer);
        customerApproval.setReviewer(user);
        customerApproval.setReviewTime(now);
        ListBox approveStatus = listBoxRepository.findByCategoryNameAndName(ListBoxCategory.APPROVAL_STATUS.name(), ApprovalStatus.APPROVED.name());
        customerApproval.setReviewStatus(approveStatus);
        customerApprovalRepository.save(customerApproval);
        customer.setDeleteUser(user);
        customer.setDeleteTime(now);
        customerRepository.save(customer);
        return customer.getId();
    }

    private CustomerApproval buildCustomerApproval(Customer customer) {
        User user = webContext.getCurrentUser();
        Date now = new Date();
        CustomerApproval customerApproval = new CustomerApproval();
        ListBox removeOptType = listBoxRepository.findByCategoryNameAndName(ListBoxCategory.OPERATION_TYPE.name(), OperationType.REMOVE.name());
        customerApproval.setOptType(removeOptType);
        customerApproval.setCustomer(customer);
        customerApproval.setEntryUser(user);
        customerApproval.setEntryTime(now);
        customerApproval.setName(customer.getName());
        customerApproval.setStatus(customer.getStatus());
        return customerApproval;
    }

    private Integer deleteCustomerByNormal(Customer customer) {
        CustomerApproval customerApproval = buildCustomerApproval(customer);
        customerApprovalRepository.save(customerApproval);
        return customerApproval.getId();
    }

    private void validateApproveBo(ApproveBo approveBo) throws BizException {
        if (approveBo.getTargetId() == null) {
            throw new BizException("审批目标为空");
        }
        CustomerApproval customerApproval = customerApprovalRepository.findByIdAndReviewTimeIsNull(approveBo.getTargetId());
        if (customerApproval == null) {
            throw new BizException("审批目标不存在或者已经审批");
        }
        if (Lists.newArrayList(ApprovalStatus.values()).contains(approveBo.getAction())) {
            throw new BizException("不支持的操作");
        }
    }

}
