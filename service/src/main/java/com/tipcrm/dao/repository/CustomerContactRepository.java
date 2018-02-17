package com.tipcrm.dao.repository;

import java.util.List;

import com.tipcrm.dao.entity.CustomerContact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerContactRepository extends JpaRepository<CustomerContact, Integer> {

    List<CustomerContact> findByCustomerApprovalId(Integer approvalId);
}
