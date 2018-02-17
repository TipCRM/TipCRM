package com.tipcrm.util;
public class MessageUtil {

    public static String getMessage(String message, String... args) {
        for (int i = 0; i < args.length; i++) {
            message = message.replace("{" + i + "}", args[i]);
        }
        return message;
    }
}
