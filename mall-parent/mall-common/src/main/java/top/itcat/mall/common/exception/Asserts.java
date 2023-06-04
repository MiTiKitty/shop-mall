package top.itcat.mall.common.exception;

import top.itcat.mall.common.api.IErrorCode;

/**
 * @className: Asserts <br/>
 * @description: 断言处理类 <br/>
 * @author: CatKitty 33641 <br/>
 * @date: 2023/06/04 <br/>
 * @version: 1.0.0 <br/>
 */
public class Asserts {

    public static void fail(String message) {
        throw new ApiException(message);
    }

    public static void fail(IErrorCode errorCode) {
        throw new ApiException(errorCode);
    }

}
