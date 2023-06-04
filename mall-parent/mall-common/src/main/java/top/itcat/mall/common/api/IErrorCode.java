package top.itcat.mall.common.api;

/**
 * @className: IErrorCode <br/>
 * @description: api返回码结果 <br/>
 * @author: CatKitty 33641 <br/>
 * @date: 2023/06/04 <br/>
 * @version: 1.0.0 <br/>
 */
public interface IErrorCode {

    /**
     * 返回码
     *
     * @return 返回码
     */
    long code();

    /**
     * 返回信息
     *
     * @return 返回信息
     */
    String message();

}
