package top.itcat.mall.common.api;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @className: CommonResult <br/>
 * @description: 统一返回结果 <br/>
 * @author: CatKitty 33641 <br/>
 * @date: 2023/06/04 <br/>
 * @version: 1.0.0 <br/>
 */
@Data
@NoArgsConstructor
public class CommonResult implements Serializable {

    private static final long serialVersionUID = 333L;

    /**
     * 状态码
     */
    private Long code;

    /**
     * 携带信息
     */
    private String msg;

    /**
     * 携带数据
     */
    private Object data;

    protected CommonResult(Long code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    protected CommonResult(IErrorCode errorCode, Object data) {
        this.code = errorCode.code();
        this.msg = errorCode.message();
        this.data = data;
    }

    /**
     * 成功返回结果
     *
     * @param msg
     *         携带消息
     * @param data
     *         携带数据
     * @return 统一返回结果
     */
    public static CommonResult success(String msg, Object data) {
        return new CommonResult(ResultCode.SUCCESS.code(), msg, data);
    }

    /**
     * 成功返回结果
     *
     * @param data
     *         携带数据
     * @return 统一返回结果
     */
    public static CommonResult success(Object data) {
        return new CommonResult(ResultCode.SUCCESS, data);
    }

    /**
     * 失败返回结果
     *
     * @param errorCode
     *         失败码对象
     * @return 统一返回结果
     */
    public static CommonResult fail(IErrorCode errorCode) {
        return new CommonResult(errorCode, null);
    }

    /**
     * 失败返回结果
     *
     * @param msg
     *         携带消息
     * @return 统一返回结果
     */
    public static CommonResult fail(String msg) {
        return new CommonResult(ResultCode.FAILED.code(), msg, null);
    }

}
