package com.tipcrm.service;
import java.util.List;

import com.tipcrm.bo.MenuBo;

public interface MenuService {

    List<MenuBo> findMenuByUserId(Integer userId);
}
