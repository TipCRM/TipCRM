package com.tipcrm.bo;

import com.tipcrm.dao.entity.User;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

public class UserBasicBo {
    private Integer id;
    private Integer workNo;
    private String name;

    public static UserBasicBo convertToUserBasicBo(User user) {
        UserBasicBo userBasicBo = new UserBasicBo();
        userBasicBo.setId(user.getId());
        userBasicBo.setName(user.getUserName());
        return userBasicBo;
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

    public Integer getWorkNo() {
        return workNo;
    }

    public void setWorkNo(Integer workNo) {
        this.workNo = workNo;
    }
}
