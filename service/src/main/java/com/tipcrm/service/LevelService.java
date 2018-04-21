package com.tipcrm.service;

import com.tipcrm.bo.LevelBo;
import com.tipcrm.bo.SaveLevelBo;

import java.util.List;

public interface LevelService {
    Integer createNewLevel(SaveLevelBo saveLevelBo);

    void updateLevel(SaveLevelBo saveLevelBo);

    List<LevelBo> getAllLevel();

    void deleteLevel(Integer levelId);
}
