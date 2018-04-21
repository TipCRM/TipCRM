package com.tipcrm.dao.repository;

import com.tipcrm.dao.entity.CustomerContact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerContactRepository extends JpaRepository<CustomerContact, Integer> {

    List<CustomerContact> findByCustomerApprovalId(Integer approvalId);

    List<CustomerContact> findByCustomerIdOrderByEntryTimeDesc(Integer customerId);
}
