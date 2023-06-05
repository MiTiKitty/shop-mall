package top.itcat.mall.common.config;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @className: MyBatisConfig <br/>
 * @description: mybatis配置类 <br/>
 * @author: CatKitty 33641 <br/>
 * @date: 2023/06/04 <br/>
 * @version: 1.0.0 <br/>
 */
@Configuration
@MapperScan("top.itcat.mall.mapper")
public class MyBatisConfig {

}
