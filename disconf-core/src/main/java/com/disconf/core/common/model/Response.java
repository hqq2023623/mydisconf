package com.disconf.core.common.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lzj
 * @date 2018/1/4
 */
public class Response<T> implements Serializable {
    /**
     * 返回码
     */
    private int code = Status.SUCCESS.getCode();

    /**
     * 返回信息
     */
    private String msg = Status.SUCCESS.getMsg();

    /**
     * 数据总量
     */
    private Long count;

    /**
     * 返回数据
     */
    private T data;

    public Response(int code, String msg, Long count, T data) {
        this.code = code;
        this.msg = msg;
        this.count = count;
        this.data = data;
    }

    /**
     *
     * @return code == 0
     */
    public boolean checkCode() {
        return this.code == 0;
    }

    /**
     * 请求成功
     *
     * @param <T>
     * @return
     */
    public static <T> Response<T> success() {
        return Response.result(Status.SUCCESS, null, null);
    }

    /**
     * 请求成功
     *
     * @param <T>
     * @return
     */
    public static <T> Response<T> success(String msg) {
        return Response.result(0,msg);
    }

    /**
     * 请求成功
     *
     * @param <T>
     * @return
     */
    public static <T> Response<T> fail(String msg) {
        return Response.result(-1,msg);
    }

    /**
     * 请求成功，并返回数据
     *
     * @param data
     * @param <T>
     * @return
     */
    public static <T> Response<T> success(T data) {
        return Response.result(Status.SUCCESS, null, data);
    }

    /**
     * 请求成功并返回数据总量，用于分页
     *
     * @param count
     * @param data
     * @param <T>
     * @return
     */
    public static <T> Response<T> success(long count, T data) {
        return Response.result(Status.SUCCESS, count, data);
    }

    /**
     * 返回结果码及信息
     *
     * @param code
     * @param msg
     * @param <T>
     * @return
     */
    public static <T> Response<T> result(int code, String msg) {
        return Response.result(code, msg, null, null);
    }

    /**
     * 返回结果码及信息
     *
     * @param status
     * @param <T>
     * @return
     */
    public static <T> Response<T> result(Status status) {
        return Response.result(status.getCode(), status.getMsg(), null, null);
    }

    /**
     * 返回结果码，信息，数据
     *
     * @param code
     * @param msg
     * @param data
     * @param <T>
     * @return
     */
    public static <T> Response<T> result(int code, String msg, T data) {
        return Response.result(code, msg, null, data);
    }

    /**
     * 返回结果码，信息，数据
     *
     * @param status
     * @param data
     * @param <T>
     * @return
     */
    public static <T> Response<T> result(Status status, T data) {
        return Response.result(status.getCode(), status.getMsg(), null, data);
    }

    /**
     * 返回结果码，信息，用于分页数据总量，数据
     *
     * @param status
     * @param count
     * @param data
     * @param <T>
     * @return
     */
    public static <T> Response<T> result(Status status, Long count, T data) {
        return Response.result(status.getCode(), status.getMsg(), count, data);
    }

    /**
     * 返回结果码，信息，用于分页数据总量，数据
     *
     * @param code
     * @param msg
     * @param count
     * @param data
     * @param <T>
     * @return
     */
    public static <T> Response<T> result(int code, String msg, Long count, T data) {
        return new Response<>(code, msg, count, data);
    }

    public enum Status {
        FAILURE(-1, "请求失败"),
        SUCCESS(0, "请求成功"),;
        private int code;
        private String msg;
        private static Map<Integer, Status> map;

        Status(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public int getCode() {
            return code;
        }

        public String getMsg() {
            return msg;
        }

        static {
            map = new HashMap<>();
            for (Status status : Status.values()) {
                if (!map.containsKey(status.code)) {
                    map.put(status.code, status);
                }
            }
        }

        public static Status getStatus(int code) {
            return Status.map.get(code);
        }

        public static String getStatusMsg(int code) {
            return Status.map.get(code).getMsg();
        }
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
