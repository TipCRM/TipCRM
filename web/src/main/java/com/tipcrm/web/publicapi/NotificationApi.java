package com.tipcrm.web.publicapi;
import java.util.List;

import com.tipcrm.bo.NotificationBo;
import com.tipcrm.bo.QueryRequestBo;
import com.tipcrm.bo.QueryResultBo;
import com.tipcrm.bo.SimpleNotificationBo;
import com.tipcrm.exception.BizException;
import com.tipcrm.exception.QueryException;
import com.tipcrm.service.NotificationService;
import com.tipcrm.web.util.JsonEntity;
import com.tipcrm.web.util.ResponseHelper;
import io.swagger.annotations.Api;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api
@RequestMapping(value = "/public/api")
@RequiresAuthentication
public class NotificationApi {

    @Autowired
    private NotificationService notificationService;

    @RequestMapping(value = "notification/realTime", method = RequestMethod.GET)
    public JsonEntity<List<SimpleNotificationBo>> getMyRealTimeNotifications() {
        return ResponseHelper.createInstance(notificationService.getMyRealTimeNotifications());
    }

    @RequestMapping(value = "notification/my", method = RequestMethod.POST)
    public JsonEntity<QueryResultBo<NotificationBo>> getMyNotifications(@RequestBody QueryRequestBo queryRequestBo) throws QueryException, BizException {
        return ResponseHelper.createInstance(notificationService.getMyNotifications(queryRequestBo));
    }

    @RequestMapping(value = "notification/{notificationId}", method = RequestMethod.GET)
    public JsonEntity<NotificationBo> getNotification(@PathVariable("notificationId") Integer notificationId) throws BizException {
        return ResponseHelper.createInstance(notificationService.getNotificationById(notificationId));
    }
}
