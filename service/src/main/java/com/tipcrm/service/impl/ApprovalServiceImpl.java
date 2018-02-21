package com.tipcrm.service.impl;
import java.util.ArrayList;
import java.util.List;

import com.tipcrm.constant.ApprovalStatus;
import com.tipcrm.constant.ApprovalType;
import com.tipcrm.constant.ListBoxCategory;
import com.tipcrm.dao.entity.ApprovalRequest;
import com.tipcrm.dao.entity.CustomerApproval;
import com.tipcrm.dao.entity.Department;
import com.tipcrm.dao.entity.ListBox;
import com.tipcrm.dao.entity.User;
import com.tipcrm.dao.repository.ApprovalRequestRepository;
import com.tipcrm.dao.repository.CustomerApprovalRepository;
import com.tipcrm.dao.repository.ListBoxRepository;
import com.tipcrm.service.ApprovalService;
import com.tipcrm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ApprovalServiceImpl implements ApprovalService {
    @Autowired
    private CustomerApprovalRepository customerApprovalRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ListBoxRepository listBoxRepository;

    @Autowired
    private ApprovalRequestRepository approvalRequestRepository;

    @Override
    public CustomerApproval saveApprovalRequest(CustomerApproval customerApproval) {
        customerApproval = customerApprovalRepository.save(customerApproval);
        // find manager
        List<User> managers = userService.findGeneralManager();
        // find department
        User currentUser = customerApproval.getEntryUser();
        User departmentManager = null;
        Department department = currentUser.getDepartment();
        if (department != null) {
            departmentManager = department.getManager();
        }
        Integer sequence = 1;
        List<ApprovalRequest> approvalRequests = new ArrayList<ApprovalRequest>();
        if (departmentManager != null) {
            approvalRequests.add(convertToApprovalRequest(customerApproval, departmentManager, sequence));
            sequence++;
        }
        for (User manager : managers) {
            if (departmentManager != null && manager.getId().equals(departmentManager.getId())) {
                continue;
            }
            approvalRequests.add(convertToApprovalRequest(customerApproval, manager, sequence));
            sequence++;
        }
        approvalRequestRepository.save(approvalRequests);
        return customerApproval;
    }

    public ApprovalRequest convertToApprovalRequest(CustomerApproval customerApproval, User reviewer, Integer sequence) {
        ApprovalRequest approvalRequest = new ApprovalRequest();
        ListBox approvalType = listBoxRepository.findByCategoryNameAndName(ListBoxCategory.APPROVAL_TYPE.name(), ApprovalType.CUSTOMER.name());
        ListBox pending = listBoxRepository.findByCategoryNameAndName(ListBoxCategory.APPROVAL_STATUS.name(), ApprovalStatus.PENDING.name());
        approvalRequest.setApprovalType(approvalType);
        approvalRequest.setReviewer(reviewer);
        approvalRequest.setReviewStatus(pending);
        approvalRequest.setApprovalId(customerApproval.getId());
        approvalRequest.setSequence(sequence);
        return approvalRequest;
    }
}
