package com.tipcrm.bo;
import java.util.ArrayList;
import java.util.List;

import com.tipcrm.dao.entity.User;
import org.springframework.util.CollectionUtils;

public class UserBasicBo {
    private Integer id;
    private String name;

    public UserBasicBo(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public static UserBasicBo convertToUserBasicBo(User user) {
        return new UserBasicBo(user.getId(), user.getUserName());
    }

    public static List<UserBasicBo> convertToUserBasicBos(List<User> users) {
        List<UserBasicBo> userBasicBos = new ArrayList<>();
        if (!CollectionUtils.isEmpty(users)) {
            for (User user : users) {
                userBasicBos.add(convertToUserBasicBo(user));
            }
        }
        return userBasicBos;

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
