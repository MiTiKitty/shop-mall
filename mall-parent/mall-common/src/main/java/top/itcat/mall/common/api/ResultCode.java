package top.itcat.mall.common.api;

/**
 * @className: ResultCode <br/>
 * @description: API返回码封装类 <br/>
 * @author: CatKitty 33641 <br/>
 * @date: 2023/06/04 <br/>
 * @version: 1.0.0 <br/>
 */
public enum ResultCode implements IErrorCode {
    /**
     * 操作成功
     */
    SUCCESS(200, "操作成功"),

    /**
     * 操作失败
     */
    FAILED(500, "操作失败"),

    /**
     * 参数检验失败
     */
    VALIDATE_FAILED(404, "参数检验失败"),

    /**
     * 暂未登录或token已经过期
     */
    UNAUTHORIZED(401, "暂未登录或token已经过期"),

    /**
     * 没有相关权限
     */
    FORBIDDEN(403, "没有相关权限");

    /**
     * 响应码
     */
    private final long code;

    /**
     * 响应消息
     */
    private final String message;

    ResultCode(long code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public long code() {
        return code;
    }

    @Override
    public String message() {
        return message;
    }
}
