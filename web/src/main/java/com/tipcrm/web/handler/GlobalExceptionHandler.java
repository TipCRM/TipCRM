package com.tipcrm.web.handler;


import javax.servlet.http.HttpServletRequest;

import com.tipcrm.exception.AccountException;
import com.tipcrm.web.util.JsonEntity;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // 捕捉shiro的异常
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(ShiroException.class)
    public JsonEntity handle401(ShiroException e) {
        logger.error("ERROR: " + e.getMessage(), e);
        JsonEntity jsonEntity = new JsonEntity();
        jsonEntity.setStatus(401);
        jsonEntity.setMessage(e.getMessage());
        return jsonEntity;
    }

    // 捕捉UnauthorizedException
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(UnauthorizedException.class)
    public JsonEntity handle401() {
        JsonEntity jsonEntity = new JsonEntity();
        jsonEntity.setStatus(403);
        jsonEntity.setMessage("未授权");
        return jsonEntity;
    }

    // 捕捉AccountException
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(AccountException.class)
    public JsonEntity handleAccountException(AccountException e) {
        JsonEntity jsonEntity = new JsonEntity();
        jsonEntity.setStatus(401);
        jsonEntity.setMessage(e.getMessage());
        return jsonEntity;
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthenticatedException.class)
    public JsonEntity handleUnauthenticatedException() {
        JsonEntity jsonEntity = new JsonEntity();
        jsonEntity.setStatus(401);
        jsonEntity.setMessage("未登陆");
        return jsonEntity;
    }

    // 捕捉其他所有异常
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public JsonEntity globalException(HttpServletRequest request, Throwable e) {
        logger.error("ERROR: " + e.getMessage(), e);
        JsonEntity jsonEntity = new JsonEntity();
        jsonEntity.setStatus(getStatus(request).value());
        jsonEntity.setMessage(e.getMessage());
        return jsonEntity;
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }
}
