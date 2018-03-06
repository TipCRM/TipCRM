package com.tipcrm.service.impl;
import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;
import com.tipcrm.bo.ListBoxBo;
import com.tipcrm.cache.ListBoxCache;
import com.tipcrm.constant.ListBoxCategory;
import com.tipcrm.dao.entity.ListBox;
import com.tipcrm.dao.repository.ListBoxRepository;
import com.tipcrm.service.ListBoxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Service
@Transactional
public class ListBoxServiceImpl implements ListBoxService {

    @Autowired
    private ListBoxRepository listBoxRepository;

    @Override
    public ListBox findByCategoryAndName(String category, String name) {
        ListBox listBox = ListBoxCache.findByCategoryAndName(category, name);
        if (listBox == null) {
            listBox = listBoxRepository.findByCategoryNameAndName(category, name);
        }
        return listBox;
    }

    private List<ListBox> findByCategory(String category) {
        List<ListBox> listBoxes = ListBoxCache.findByCategory(category);
        if (CollectionUtils.isEmpty(listBoxes)) {
            listBoxes = listBoxRepository.findByCategoryName(category);
        }
        return listBoxes;
    }

    @Override
    public List<ListBoxBo> findAllCustomerStatuses() {
        return convertToListBoxBos(findByCategory(ListBoxCategory.CUSTOMER_STATUS.name()));
    }

    @Override
    public List<ListBoxBo> findAllGoalTypes() {
        return convertToListBoxBos(findByCategory(ListBoxCategory.GOAL_TYPE.name()));
    }

    @Override
    public List<ListBoxBo> findAllUserStatuses() {
        return convertToListBoxBos(findByCategory(ListBoxCategory.USER_STATUS.name()));
    }

    @Override
    public List<ListBoxBo> findAllNotificationReadStatuses() {
        return convertToListBoxBos(findByCategory(ListBoxCategory.NOTIFICATION_READ_STATUS.name()));
    }

    private ListBoxBo convertToListBoxBo(ListBox listBox) {
        if (listBox != null) {
            return new ListBoxBo(listBox.getId(), listBox.getDisplayName());
        }
        return null;
    }

    private List<ListBoxBo> convertToListBoxBos(List<ListBox> listBoxes) {
        if (!CollectionUtils.isEmpty(listBoxes)) {
            return listBoxes.stream().map(listBox -> convertToListBoxBo(listBox)).sorted((listBox1, listBox2) -> listBox1.getId().compareTo(listBox2.getId()))
                            .collect(Collectors.toList());
        }
        return Lists.newArrayList();
    }
}
