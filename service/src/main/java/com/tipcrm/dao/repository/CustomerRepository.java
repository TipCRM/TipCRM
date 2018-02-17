package com.tipcrm.dao.repository;
import java.util.List;

import com.tipcrm.dao.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    List<Customer> findByNameAndDeleteTimeIsNull(String name);

}
