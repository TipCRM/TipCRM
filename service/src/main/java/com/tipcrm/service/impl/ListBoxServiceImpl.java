package com.tipcrm.service.impl;
import com.tipcrm.dao.entity.ListBox;
import com.tipcrm.dao.repository.ListBoxRepository;
import com.tipcrm.service.ListBoxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ListBoxServiceImpl implements ListBoxService {

    @Autowired
    private ListBoxRepository listBoxRepository;

    @Override
    public ListBox findByCategoryAndName(String category, String name) {
        return listBoxRepository.findByCategoryNameAndName(category, name);
    }
}
