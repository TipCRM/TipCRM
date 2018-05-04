package com.tipcrm.service;

import com.tipcrm.dao.entity.CustomerApproval;

public interface ApprovalService {
    CustomerApproval saveApprovalRequest(CustomerApproval customerApproval);
}
