package top.itcat.mall.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.security.core.userdetails.UserDetails;
import top.itcat.mall.admin.dto.UmsAdminRegisterParam;
import top.itcat.mall.admin.dto.UpdateAdminPasswordParam;
import top.itcat.mall.admin.vo.AdminInfoVO;
import top.itcat.mall.admin.vo.UmsAdminRegisterSuccessVO;
import top.itcat.mall.common.api.CommonPage;
import top.itcat.mall.entity.UmsAdmin;
import top.itcat.mall.entity.UmsResource;

import java.util.List;

/**
 * <p>
 * 后台用户表 服务类
 * </p>
 *
 * @author CatKitty 33641
 * @since 2023-06-04
 */
public interface UmsAdminService extends IService<UmsAdmin> {

    /**
     * 根据用户名获取用户
     *
     * @param username
     *         用户名
     * @return 用户信息
     */
    UmsAdmin getUmsAdminByUsername(String username);

    /**
     * 根据用户id获取资源列表
     *
     * @param adminId
     *         用户id
     * @return 资源列表
     */
    List<UmsResource> getResourceListByAdminId(Long adminId);

    /**
     * 获取用户信息
     *
     * @param username
     *         用户名
     * @return 用户信息
     */
    UserDetails loadUserByUsername(String username);

    /**
     * 用户注册
     *
     * @param param
     *         注册参数
     * @return 注册成功返回对象
     */
    UmsAdminRegisterSuccessVO register(UmsAdminRegisterParam param);

    /**
     * 使用用户名和密码进行登录
     *
     * @param username
     *         用户名
     * @param password
     *         密码
     * @return 登录成功生成的token，若登陆失败，则返回null
     */
    String login(String username, String password);

    /**
     * 刷新token
     *
     * @param token
     *         旧token
     * @return 新token
     */
    String refreshToken(String token);

    /**
     * 根据用户名获取用户详细列表
     *
     * @param username
     *         用户名
     * @return 用户详情
     */
    AdminInfoVO getInfoByUsername(String username);

    /**
     * 更新用户角色联系
     *
     * @param adminId
     *         用户id
     * @param roleIds
     *         角色id集合
     * @return 成功与否
     */
    Boolean updateRole(Long adminId, List<Long> roleIds);

    /**
     * 分页查询用户列表
     *
     * @param keyword
     *         关键词：用户名或者用户昵称
     * @param pageSize
     *         每页查询数量
     * @param pageNum
     *         当前查询页
     * @return 查询用户列表
     */
    CommonPage<UmsAdminRegisterSuccessVO> listByPage(Integer pageNum, Integer pageSize, String keyword);

    /**
     * 根据用户id删除用户信息，包括该用户的所有联系信息
     *
     * @param id
     *         用户id
     * @return 成功与否
     */
    Boolean removeAdminById(Long id);

    /**
     * 修改用户密码
     *
     * @param param
     *         修改参数对象
     * @return 状态值
     */
    int updateAdminPassword(UpdateAdminPasswordParam param);
}
