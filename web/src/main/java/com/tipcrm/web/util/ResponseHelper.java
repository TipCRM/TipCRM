//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.tipcrm.web.util;


public class ResponseHelper {
    public ResponseHelper() {
    }

    public static <T> JsonEntity<T> createInstance(T object) {
        JsonEntity<T> response = new JsonEntity(object);
        //        response.setRequestId(WebUtil.getRequestId());
        return response;
    }

    public static <T> JsonEntity<T> of(T object) {
        return createInstance(object);
    }

    public static <T> JsonEntity<T> ofNothing() {
        return createInstance(null);
    }
}
