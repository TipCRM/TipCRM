package com.tipcrm.service;

import com.tipcrm.bo.ListBoxBo;
import com.tipcrm.dao.entity.ListBox;

import java.util.List;

public interface ListBoxService {

    ListBox findByCategoryAndName(String category, String name);

    List<ListBoxBo> findAllCustomerStatuses();

    List<ListBoxBo> findAllGoalTypes();

    List<ListBoxBo> findAllUserStatuses();

    List<ListBoxBo> findAllNotificationReadStatuses();
}
