package top.itcat.mall.admin;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.itcat.mall.admin.dto.UmsAdminRegisterParam;
import top.itcat.mall.admin.service.UmsAdminService;
import top.itcat.mall.admin.service.UmsMenuService;
import top.itcat.mall.admin.service.UmsResourceService;
import top.itcat.mall.admin.service.UmsRoleService;
import top.itcat.mall.admin.vo.UmsAdminRegisterSuccessVO;

import java.util.Collections;

/**
 * @className: CatAdminTest <br/>
 * @description: 测试类 <br/>
 * @author: CatKitty 33641 <br/>
 * @date: 2023/06/05 <br/>
 * @version: 1.0.0 <br/>
 */
@SpringBootTest
public class CatAdminTest {

    @Autowired
    private UmsAdminService adminService;

    public void addSuperAdmin() {
        UmsAdminRegisterParam param = new UmsAdminRegisterParam();
        param.setNickName("CatKitty");
        param.setUsername("CatKitty");
        param.setPassword("123456");
        param.setEmail("3364187323@qq.com");
        param.setNote("猫猫专用");
        param.setIcon("none");
        UmsAdminRegisterSuccessVO vo = adminService.register(param);
        // 注册完账号，进行权限分配
        if (vo != null) {
            adminService.updateRole(vo.getId(), Collections.singletonList(5L));
        }
    }

}
