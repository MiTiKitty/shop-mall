package top.itcat.mall;



import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * @className: Generator <br/>
 * @description: 代码生成工具 <br/>
 * @author: CatKitty 33641 <br/>
 * @date: 2023/06/04 <br/>
 * @version: 1.0.0 <br/>
 */
public class Generator {

    private static final String dbName = "cat_shop_mall";
    private static final String driverClassName = "com.mysql.cj.jdbc.Driver";

    private static final String dbUrl = "jdbc:mysql://116.62.191.135:3306/" + dbName + "?useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai";

    private static final String dbUsername = "root";
    private static final String dbPassword = "123123asd";

    private static final String basePackageName = "top.itcat.mall";

    private static final String author = "CatKitty 33641";

    // 是否开启swagger日志
    private static final boolean useSwagger = false;
    // 是否覆盖
    private static final boolean isOverride = true;

    /**
     * 获取某个数据库中的所有表名
     * @param dbName
     * @return
     * @throws Exception
     */
    private static String[] getTables(String dbName) throws Exception {
        List<String> tables = new ArrayList<>();

        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        try {
            Class.forName(driverClassName);
            connection = DriverManager.getConnection(dbUrl,dbUsername,dbPassword);
            String sql = "select table_name from information_schema.tables where table_schema=?";
            ps = connection.prepareStatement(sql);
            ps.setString(1,dbName);
            resultSet = ps.executeQuery();
            while (resultSet.next()) {
                tables.add(resultSet.getString("table_name"));
            }
            String[] result = tables.toArray(new String[tables.size()]);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        throw new Exception("数据库连接异常！");
    }

    /**
     * 直接获取数据库中的所有表名然后自动生成
     * 自动生成entity、mapper、service、controller的代码
     * @throws Exception
     */
    private static void generateAllInfo() throws Exception {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir") + "\\mall-parent\\mall-mbg";
        gc.setOutputDir(projectPath + "/src/main/java");
        gc.setAuthor(author);
        gc.setOpen(false);
        gc.setSwagger2(useSwagger);      //实体属性 Swagger2 注解
        gc.setFileOverride(isOverride);  // 是否覆盖
        gc.setServiceName("%sService"); //去Service的I前缀
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl(dbUrl);
        dsc.setDriverName(driverClassName);
        dsc.setUsername(dbUsername);
        dsc.setPassword(dbPassword);
        dsc.setDbType(DbType.MYSQL);
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent(basePackageName);
        mpg.setPackageInfo(pc);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {}
        };

        // 如果模板引擎是 freemarker
        String templatePath = "/templates/mapper.xml.ftl";
        // 如果模板引擎是 velocity
        // String templatePath = "/templates/mapper.xml.vm";

        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return projectPath + "/src/main/resources/mapper/" +
                        "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });

        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();

        templateConfig.setXml(null);
        templateConfig.setController(null);
        mpg.setTemplate(templateConfig);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
        strategy.setInclude(getTables(dbName));
        strategy.setControllerMappingHyphenStyle(true);
        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());

        mpg.execute();
    }

    public static void main(String[] args) {
        try {
            generateAllInfo();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
