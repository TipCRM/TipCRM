package com.tipcrm.service;

import java.util.List;

import com.tipcrm.bo.ListBoxBo;
import com.tipcrm.dao.entity.ListBox;

public interface ListBoxService {

    ListBox findByCategoryAndName(String category, String name);

    List<ListBoxBo> findAllCustomerStatuses();

    List<ListBoxBo> findAllGoalTypes();

    List<ListBoxBo> findAllUserStatuses();

    List<ListBoxBo> findAllNotificationReadStatuses();
}
