package top.itcat.mall.common.exception;

import top.itcat.mall.common.api.IErrorCode;

/**
 * @className: ApiException <br/>
 * @description: 自定义异常 <br/>
 * @author: CatKitty 33641 <br/>
 * @date: 2023/06/04 <br/>
 * @version: 1.0.0 <br/>
 */
public class ApiException extends RuntimeException {

    private IErrorCode errorCode;

    public ApiException(IErrorCode errorCode){
        super(errorCode.message());
        this.errorCode = errorCode;
    }

    public ApiException(String message) {
        super(message);
    }

    public ApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public IErrorCode getErrorCode() {
        return errorCode;
    }
}
