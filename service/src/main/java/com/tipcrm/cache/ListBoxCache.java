package com.tipcrm.cache;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.tipcrm.dao.entity.ListBox;
import org.springframework.util.CollectionUtils;

public class ListBoxCache {
    private static Map<Integer, ListBox> listBoxCache = Maps.newHashMap();

    public static void addOrUpdateCache(List<ListBox> listBoxes) {
        if (listBoxes != null) {
            if (listBoxCache == null) {
                listBoxCache = Maps.newHashMap();
            }
            if (!CollectionUtils.isEmpty(listBoxes)) {
                for (ListBox listBox : listBoxes) {
                    listBoxCache.put(listBox.getId(), listBox);
                }
            }
        }
    }

    public static void addOrUpdateCache(ListBox listBox) {
        addOrUpdateCache(Lists.newArrayList(listBox));
    }

    public static ListBox findByCategoryAndName(String categoryName, String name) {
        if (listBoxCache == null) {
            return null;
        }
        for (Map.Entry<Integer, ListBox> entry : listBoxCache.entrySet()) {
            ListBox listBox = entry.getValue();
            if (listBox.getCategoryName().equals(categoryName) && listBox.getName().equals(name)) {
                return listBox;
            }
        }
        return null;
    }

    public static List<ListBox> findByCategory(String category) {
        if (listBoxCache == null) {
            return null;
        }
        return listBoxCache.values().stream().filter(listBox -> listBox.getCategoryName().equals(category)).collect(Collectors.toList());
    }

    public static List<ListBox> findAll() {
        return Lists.newArrayList(listBoxCache.values());
    }
}
