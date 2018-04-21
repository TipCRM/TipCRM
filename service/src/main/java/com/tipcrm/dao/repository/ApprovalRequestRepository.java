package com.tipcrm.dao.repository;

import com.tipcrm.dao.entity.ApprovalRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApprovalRequestRepository extends JpaRepository<ApprovalRequest, Integer> {
    List<ApprovalRequest> findByApprovalTypeIdAndApprovalId(Integer approvalTypeId, Integer approvalId);

    ApprovalRequest findByApprovalTypeIdAndApprovalIdAndReviewerId(Integer approveTypeId, Integer approvalId, Integer reivewerId);
}
