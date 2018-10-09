package com.vz.paas.util.wrapper;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * 包装类
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-09 14:27:01
 */
@Data
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Wrapper<T> implements Serializable {

    private static final long serialVersionUID = 2721206135892903278L;

    /**
     * 成功码
     */
    public static final int SUCCESS_CODE = 200;

    /**
     * 成功信息
     */
    public static final String SUCCESS_MESSAGE = "操作成功";

    /**
     * 错误码
     */
    public static final int ERROR_CODE = 500;

    /**
     * 错误信息
     */
    public static final String ERROR_MESSAGE = "内部异常";

    /**
     * 错误码：非法参数
     */
    public static final int ILLEGAL_ARGUMENT_CODE = 100;

    /**
     * 错误信息：非法参数
     */
    public static final String ILLEGAL_ARGUMENT_MESSAGE = "参数非法";

    /**
     * 编码
     */
    private int code;

    /**
     * 信息
     */
    private String message;

    /**
     * 结果数据
     */
    private T result;

    public Wrapper() {
        this(SUCCESS_CODE, SUCCESS_MESSAGE);
    }

    public Wrapper(int code, String message) {
        this(code, message, null);
    }

    public Wrapper(int code, String message, T result) {
        super();
        this.code(code).message(message).result(result);
    }

    private Wrapper<T> code(int code) {
        this.setCode(code);
        return this;
    }

    private Wrapper<T> message(String message) {
        this.setMessage(message);
        return this;
    }

    private Wrapper<T> result(T result) {
        this.setResult(result);
        return this;
    }

    /**
     * 判断是否成功：判断依据编码
     * @return code为200则true，否则false
     */
    @JsonIgnore
    public boolean success() {
        return Wrapper.SUCCESS_CODE == this.code;
    }

    /**
     * 判断是否失败：判断依据编码
     * @return code不为200则true，否则false
     */
    @JsonIgnore
    public boolean error() {
        return !success();
    }
}
