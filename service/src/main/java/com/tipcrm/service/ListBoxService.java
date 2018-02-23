package com.tipcrm.service;
import com.tipcrm.dao.entity.ListBox;

public interface ListBoxService {

    ListBox findByCategoryAndName(String category, String name);
}
