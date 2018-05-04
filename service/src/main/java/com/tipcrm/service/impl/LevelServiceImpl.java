package com.tipcrm.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.tipcrm.bo.LevelBo;
import com.tipcrm.bo.SaveLevelBo;
import com.tipcrm.dao.entity.Level;
import com.tipcrm.dao.entity.User;
import com.tipcrm.dao.repository.LevelRepository;
import com.tipcrm.dao.repository.UserRepository;
import com.tipcrm.exception.BizException;
import com.tipcrm.service.LevelService;
import com.tipcrm.service.WebContext;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Transactional
@Service
public class LevelServiceImpl implements LevelService {

    @Autowired
    private LevelRepository levelRepository;

    @Autowired
    private WebContext webContext;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Integer createNewLevel(SaveLevelBo saveLevelBo) {
        validateSaveLevelBo(saveLevelBo, true);
        User user = webContext.getCurrentUser();
        Date date = new Date();
        Level level = new Level();
        level.setDefaultPaymentPercent(saveLevelBo.getDefaultPayment().setScale(2, BigDecimal.ROUND_HALF_DOWN));
        level.setName(saveLevelBo.getName());
        level.setEntryUser(user);
        level.setEntryTime(date);
        level = levelRepository.save(level);
        return level.getId();
    }

    @Override
    public void updateLevel(SaveLevelBo saveLevelBo) {
        validateSaveLevelBo(saveLevelBo, false);
        User user = webContext.getCurrentUser();
        Date date = new Date();
        Level level = levelRepository.findOne(saveLevelBo.getId());
        level.setName(saveLevelBo.getName());
        level.setDefaultPaymentPercent(saveLevelBo.getDefaultPayment().setScale(2, BigDecimal.ROUND_HALF_DOWN));
        level.setUpdateUser(user);
        level.setUpdateTime(date);
        levelRepository.save(level);
    }

    @Override
    public List<LevelBo> getAllLevel() {
        return LevelBo.toLevelBos(levelRepository.findAllByDeleteTimeIsNull());

    }

    @Override
    public void deleteLevel(Integer levelId) {
        Level level = levelRepository.findByIdAndDeleteTimeIsNull(levelId);
        if (level == null) {
            throw new BizException("该等级不存在");
        }
        List<User> users = userRepository.findAllByLevelId(levelId);
        if (!CollectionUtils.isEmpty(users)) {
            users.stream().forEach(user -> user.setLevel(null));
            userRepository.save(users);
        }
        User user = webContext.getCurrentUser();
        Date date = new Date();
        level.setDeleteUser(user);
        level.setDeleteTime(date);
        levelRepository.save(level);
    }

    private void validateSaveLevelBo(SaveLevelBo saveLevelBo, Boolean isSaveMethod) {
        if (!isSaveMethod && saveLevelBo.getId() == null) {
            throw new BizException("没有指定需要修改的等级ID");
        }
        if (!isSaveMethod) {
            Level level = levelRepository.findOne(saveLevelBo.getId());
            if (level == null) {
                throw new BizException("等级不存在");
            }
        }
        if (StringUtils.isBlank(saveLevelBo.getName())) {
            throw new BizException("等级名不能为空");
        }
        Level level = levelRepository.findByName(saveLevelBo.getName());
        if (level != null && (isSaveMethod || !isSaveMethod && !level.getId().equals(saveLevelBo.getId()))) {
            throw new BizException("等级名已存在");
        }
        if (saveLevelBo.getDefaultPayment() == null) {
            throw new BizException("提成比不能为空");
        }
        int compare0 = saveLevelBo.getDefaultPayment().compareTo(BigDecimal.ZERO);
        int compare100 = saveLevelBo.getDefaultPayment().compareTo(new BigDecimal(100));
        if (compare0 < 0 || compare100 >= 0) {
            throw new BizException("提成比介于0-100之间");
        }
    }
}