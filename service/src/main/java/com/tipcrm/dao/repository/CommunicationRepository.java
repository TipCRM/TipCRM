package com.tipcrm.dao.repository;

import java.util.List;

import com.tipcrm.dao.entity.Communication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommunicationRepository extends JpaRepository<Communication, Integer> {
    List<Communication> findByCustomerIdOrderByEntryTimeDesc(Integer customerId);
}
