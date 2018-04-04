package com.tipcrm.service;
import java.util.List;

import com.tipcrm.bo.LevelBo;
import com.tipcrm.bo.SaveLevelBo;

public interface LevelService {
    Integer createNewLevel(SaveLevelBo saveLevelBo);

    void updateLevel(SaveLevelBo saveLevelBo);

    List<LevelBo> getAllLevel();
}
