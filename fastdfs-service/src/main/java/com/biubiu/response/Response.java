package com.biubiu.response;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by Haibiao.Zhang on 2019-03-28 10:45
 */
@Getter
@Setter
@EqualsAndHashCode
public class Response<T> implements Serializable {

    private static final long serialVersionUID = 3683560174028475510L;

    /**
     * 请求成功
     */
    public static final Long OK = 200L;

    /**
     * 已接受
     */
    public static final Long ACCEPT = 202L;

    /**
     * 请求失败（客户端因素）
     */
    public static final Long BAD_REQUEST = 400L;

    /**
     * 未授权
     */
    public static final Long UNAUTHORIZED = 401L;

    /**
     * 请求失败（服务端因素）
     */
    public static final Long FORBIDDEN = 403L;

    /**
     * 服务错误（服务端因素）
     */
    public static final Long INTERNAL_ERROR = 500L;

    private Long code;

    private String message;

    private T payload;

    /**
     * 等价于new Response(OK)
     */
    public Response() {
        this(OK);
    }

    /**
     * 等价于new Response(code, null)
     */
    public Response(Long code) {
        this(code, null);
    }

    /**
     * 等价于new Response(code, message, null)
     */
    public Response(Long code, String message) {
        this(code, message, null);
    }

    /**
     * 使用给定参数构造请求响应
     *
     * @param code    状态码
     * @param message 响应消息
     * @param payload 响应负载
     */
    public Response(Long code, String message, T payload) {
        this.code = code;
        this.message = message;
        this.payload = payload;
    }

    /**
     * 等价于new Response(OK, null, payload)
     */
    public Response(T payload) {
        this(OK, null, payload);
    }

    /**
     * 成功返回
     *
     * @return Response
     */
    public static <T> Response<T> succeed() {
        return new Response<>();
    }

    /**
     * 成功返回
     *
     * @return Response
     */
    public static <T> Response<T> succeed(T payload) {
        return new Response<>(payload);
    }

    /**
     * 失败返回
     *
     * @param code 错误码
     * @return Response
     */
    public static <T> Response<T> fail(Long code) {
        return new Response<>(code);
    }

    /**
     * 失败返回
     *
     * @param code    错误码
     * @param message 错误消息
     * @return Response
     */
    public static <T> Response<T> fail(Long code, String message) {
        return new Response<>(code, message);
    }

}
