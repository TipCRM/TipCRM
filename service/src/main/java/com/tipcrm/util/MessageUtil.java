package com.tipcrm.util;
public class MessageUtil {

    public static String getMessage(String message, Object... args) {
        for (int i = 0; i < args.length; i++) {
            message = message.replace("{" + i + "}", args[i].toString());
        }
        return message;
    }
}
