package com.tipcrm.web.publicapi;

import java.util.List;

import com.tipcrm.bo.ListBoxBo;
import com.tipcrm.service.ListBoxService;
import com.tipcrm.web.util.JsonEntity;
import com.tipcrm.web.util.ResponseHelper;
import io.swagger.annotations.Api;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public/api/type")
@Api
@RequiresAuthentication
public class ListBoxApi {

    @Autowired
    private ListBoxService listBoxService;

    @RequestMapping(value = "customerStatus", method = RequestMethod.GET)
    public JsonEntity<List<ListBoxBo>> getCustomerStatuses() {
        return ResponseHelper.createInstance(listBoxService.findAllCustomerStatuses());
    }

    @RequestMapping(value = "goalType", method = RequestMethod.GET)
    public JsonEntity<List<ListBoxBo>> getGoalTypes() {
        return ResponseHelper.createInstance(listBoxService.findAllGoalTypes());
    }

    @RequestMapping(value = "userStatus", method = RequestMethod.GET)
    public JsonEntity<List<ListBoxBo>> getUserStatuses() {
        return ResponseHelper.createInstance(listBoxService.findAllUserStatuses());
    }

    @RequestMapping(value = "notificationReadStatus", method = RequestMethod.GET)
    public JsonEntity<List<ListBoxBo>> getNotificationReadStatuses() {
        return ResponseHelper.createInstance(listBoxService.findAllNotificationReadStatuses());
    }


}
