package com.tipcrm.dao.repository;

import com.tipcrm.dao.entity.Communication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommunicationRepository extends JpaRepository<Communication, Integer> {
    List<Communication> findByCustomerIdOrderByEntryTimeDesc(Integer customerId);
}
