package com.tipcrm.web.publicapi;
import java.util.List;

import com.tipcrm.bo.LevelBo;
import com.tipcrm.bo.SaveLevelBo;
import com.tipcrm.constant.Constants;
import com.tipcrm.service.LevelService;
import com.tipcrm.web.util.JsonEntity;
import com.tipcrm.web.util.ResponseHelper;
import io.swagger.annotations.Api;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api
@RequiresAuthentication
@RequestMapping(value = "/public/api")
public class LevelApi {

    @Autowired
    private LevelService levelService;

    @RequestMapping(value = "level", method = RequestMethod.POST)
    @RequiresPermissions(Constants.Permission.LEVEL_ADD)
    public JsonEntity<Integer> createNewLevel(@RequestBody SaveLevelBo saveLevelBo) {
        return ResponseHelper.createInstance(levelService.createNewLevel(saveLevelBo));
    }

    @RequestMapping(value = "levels", method = RequestMethod.GET)
    @RequiresPermissions(Constants.Permission.LEVEL_VIEW)
    public JsonEntity<List<LevelBo>> getLevels() {
        return ResponseHelper.createInstance(levelService.getAllLevel());
    }

    @RequestMapping(value = "level", method = RequestMethod.PUT)
    @RequiresPermissions(Constants.Permission.LEVEL_UPDATE)
    public JsonEntity<String> updateLevel(@RequestBody SaveLevelBo saveLevelBo) {
        levelService.updateLevel(saveLevelBo);
        return ResponseHelper.createInstance(Constants.RequestResult.SUCCESS);
    }

    @RequestMapping(value = "level/{levelId}", method = RequestMethod.DELETE)
    @RequiresPermissions(Constants.Permission.LEVEL_DELETE)
    public JsonEntity<String> deleteLevel() {
        return ResponseHelper.ofNothing();
    }
}
