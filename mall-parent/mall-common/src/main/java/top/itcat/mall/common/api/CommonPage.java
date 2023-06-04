package top.itcat.mall.common.api;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @className: CommonPage <br/>
 * @description: 通用分页结果类 <br/>
 * @author: CatKitty 33641 <br/>
 * @date: 2023/06/04 <br/>
 * @version: 1.0.0 <br/>
 */
@Data
@AllArgsConstructor
public class CommonPage<T> {

    /**
     * 当前页
     */
    private Integer pageNo;

    /**
     * 每页数量
     */
    private Integer pageSize;

    /**
     * 总页数
     */
    private Integer totalPage;

    /**
     * 总条数
     */
    private Long total;

    /**
     * 携带数据列表
     */
    private List<T> array;

}
