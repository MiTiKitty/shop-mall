package top.itcat.mall.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 会员任务表
 * </p>
 *
 * @author CatKitty 33641
 * @since 2023-06-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UmsMemberTask implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String name;

    /**
     * 赠送成长值
     */
    private Integer growth;

    /**
     * 赠送积分
     */
    private Integer intergration;

    /**
     * 任务类型：0->新手任务；1->日常任务
     */
    private Integer type;


}
