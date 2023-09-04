package com.zqz.shop.utils;

import com.zqz.shop.enums.AdminResponseCode;
import com.zqz.shop.enums.ResponseCode;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: ZQZ
 * @Description: 响应工具
 * @ClassName: ResponseUtil
 * @Date: Created in 11:50 2023-8-11
 */
public class ResponseUtil {
    public static Object ok() {
        Map<String, Object> obj = new HashMap<>(2);
        obj.put("errno", 0);
        obj.put("errmsg", "成功");
        return obj;
    }

    public static Object ok(Object data) {
        Map<String, Object> obj = new HashMap<>(3);
        obj.put("errno", 0);
        obj.put("errmsg", "成功");
        obj.put("data", data);
        return obj;
    }

    public static Object ok(String errmsg, Object data) {
        Map<String, Object> obj = new HashMap<>(3);
        obj.put("errno", 0);
        obj.put("errmsg", errmsg);
        obj.put("data", data);
        return obj;
    }

    public static Object fail() {
        Map<String, Object> obj = new HashMap<>(2);
        obj.put("errno", -1);
        obj.put("errmsg", "错误");
        return obj;
    }

    public static Object fail(String msg) {
        Map<String, Object> obj = new HashMap<>(2);
        obj.put("errno", -1);
        obj.put("errmsg", msg);
        return obj;
    }

    public static Object fail(int errno, String errmsg) {
        Map<String, Object> obj = new HashMap<>(2);
        obj.put("errno", errno);
        obj.put("errmsg", errmsg);
        return obj;
    }

    public static Object fail(ResponseCode responseCode) {
        Map<String, Object> obj = new HashMap<>(2);
        obj.put("errno", responseCode.getCode());
        obj.put("errmsg", responseCode.getDesc());
        return obj;
    }


    public static Object fail(AdminResponseCode responseCode) {
        Map<String, Object> obj = new HashMap<>(2);
        obj.put("errno", responseCode.getCode());
        obj.put("errmsg", responseCode.getDesc());
        return obj;
    }


    public static Object badArgument() {
        return fail(401, "参数不对");
    }

    public static Object badArgumentValue() {
        return fail(402, "参数值不对");
    }

    public static Object dataEmpty() {
        return fail(403, "数据为空");
    }

    public static Object unlogin() {
        return fail(501, "请登录");
    }

    public static Object serious() {
        return fail(502, "系统内部错误");
    }

    public static Object unsupport() {
        return fail(503, "业务不支持");
    }

    public static Object updatedDateExpired() {
        return fail(504, "更新数据已经失效");
    }

    public static Object updatedDataFailed() {
        return fail(505, "更新数据失败");
    }
    public static Object fileUploadError() {
        return fail(507, "文件上传失败");
    }

    public static Object unauthz() {
        return fail(506, "无操作权限");
    }

}
