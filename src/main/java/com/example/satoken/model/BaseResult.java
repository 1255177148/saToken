package com.example.satoken.model;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

@Data
public class BaseResult<T> implements Serializable {

    /**
     * 返回数据
     */
    private T data;

    /**
     * 提示信息
     */
    private String message;

    /**
     * 状态码
     */
    private int code;

    /**
     * 状态
     */
    private boolean state = true;

    public static <T> BaseResult<T> result(int code, String message, boolean state) {
        BaseResult<T> result = new BaseResult<>();
        result.setCode(code);
        result.setMessage(message);
        result.setState(state);
        return result;
    }

    /**
     * 返回信息
     *
     * @param code    状态码
     * @param message 信息
     * @param t       数据
     * @param <T>     T
     * @return ResultVo
     */
    public static <T> BaseResult<T> result(int code, String message, T t, boolean state) {
        BaseResult<T> r = new BaseResult<>();
        r.setCode(code);
        r.setMessage(message);
        r.setData(t);
        r.setState(state);
        return r;
    }

    /**
     * 返回成功
     *
     * @param data data
     * @param <T>  T
     * @return ResultVo
     */
    public static <T> BaseResult<T> success(T data, String message) {
        return result(HttpStatus.OK.value(), message, data, true);
    }

    /**
     * 返回失败
     *
     * @param code code
     * @param <T>  T
     * @return ResultVo
     */
    public static <T> BaseResult<T> fail(int code, String message) {
        return result(code, message, null, false);
    }
}
