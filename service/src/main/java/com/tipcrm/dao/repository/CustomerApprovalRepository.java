package com.tipcrm.dao.repository;

import com.tipcrm.dao.entity.CustomerApproval;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerApprovalRepository extends JpaRepository<CustomerApproval, Integer> {

    CustomerApproval findByIdAndReviewStatusId(Integer customerApprovalId, Integer reviewStatusId);
}
