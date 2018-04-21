package com.tipcrm.dao.repository;

import com.tipcrm.dao.entity.ListBox;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ListBoxRepository extends JpaRepository<ListBox, Integer> {

    ListBox findByCategoryNameAndName(String categoryName, String name);

    List<ListBox> findByCategoryName(String categoryName);
}
