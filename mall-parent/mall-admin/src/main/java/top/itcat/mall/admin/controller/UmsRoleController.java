package top.itcat.mall.admin.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.itcat.mall.admin.dto.CreateRoleParam;
import top.itcat.mall.admin.dto.DeleteRoleParam;
import top.itcat.mall.admin.dto.UpdateRoleMenuParam;
import top.itcat.mall.admin.dto.UpdateRoleResourceParam;
import top.itcat.mall.admin.service.UmsRoleService;
import top.itcat.mall.common.api.CommonPage;
import top.itcat.mall.common.api.CommonResult;
import top.itcat.mall.entity.UmsMenu;
import top.itcat.mall.entity.UmsResource;
import top.itcat.mall.entity.UmsRole;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @className: UmsRoleController <br/>
 * @description: 后台角色管理 <br/>
 * @author: CatKitty 33641 <br/>
 * @date: 2023/06/05 <br/>
 * @version: 1.0.0 <br/>
 */
@RestController
@RequestMapping("role")
public class UmsRoleController {

    @Autowired
    private UmsRoleService roleService;

    /**
     * 添加新角色
     *
     * @param param
     *         添加参数
     * @return 统一返回结果
     */
    @PostMapping("create")
    public CommonResult createRole(@RequestBody @Validated CreateRoleParam param) {
        UmsRole role = new UmsRole();
        BeanUtils.copyProperties(param, role);
        role.setCreateTime(LocalDateTime.now());
        role.setAdminCount(0);
        boolean result = roleService.save(role);
        if (result) {
            return CommonResult.success("添加成功");
        }
        return CommonResult.fail("添加失败");
    }

    /**
     * 根据角色id修改角色信息
     *
     * @param id
     *         角色id
     * @param param
     *         修改参数
     * @return 统一返回结果
     */
    @PutMapping("{id}")
    public CommonResult updateById(@PathVariable("id") Long id, @RequestBody CreateRoleParam param) {
        UmsRole role = new UmsRole();
        BeanUtils.copyProperties(param, role);
        role.setId(id);
        boolean result = roleService.updateById(role);
        if (result) {
            return CommonResult.success("修改成功");
        }
        return CommonResult.fail("修改失败");
    }

    /**
     * 批量删除角色
     *
     * @param param
     *         删除角色参数对象
     * @return 统一返回结果
     */
    @DeleteMapping("del")
    public CommonResult deleteByIds(@RequestBody @Validated DeleteRoleParam param) {
        Boolean result = roleService.removeRolesByIds(param.getIds());
        if (result) {
            return CommonResult.success("删除成功");
        }
        return CommonResult.fail("删除失败");
    }

    /**
     * 获取所有角色
     *
     * @return 所有角色
     */
    @GetMapping("all")
    public CommonResult getAll() {
        List<UmsRole> list = roleService.list();
        return CommonResult.success(list);
    }

    /**
     * 分页查询角色列表
     *
     * @param keyword
     *         搜索关键词：角色名称
     * @param pageSize
     *         每页查询数量
     * @param pageNum
     *         当前查询页
     * @return 查询角色列表
     */
    @GetMapping("list")
    public CommonResult listByPage(@RequestParam(value = "keyword", required = false) String keyword,
                                   @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                   @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        CommonPage<UmsRole> roles = roleService.listByPage(pageNum, pageSize, keyword);
        return CommonResult.success(roles);
    }

    /**
     * 修改角色状态
     *
     * @param id
     *         角色id
     * @param status
     *         状态值
     * @return 统一返回结果
     */
    @PutMapping("/updateStatus/{id}")
    public CommonResult updateRoleStatus(@PathVariable("id") Long id,
                                         @RequestParam("status") @Min(0) @Max(1) Integer status) {
        UmsRole umsRole = new UmsRole();
        umsRole.setId(id);
        umsRole.setStatus(status);
        boolean result = roleService.updateById(umsRole);
        if (result) {
            return CommonResult.success("修改成功");
        }
        return CommonResult.fail("修改失败");
    }

    /**
     * 根据角色id获取相关菜单列表
     *
     * @param id
     *         角色id
     * @return 菜单列表
     */
    @GetMapping("/listMenu/{id}")
    public CommonResult listMenuById(@PathVariable("id") Long id) {
        List<UmsMenu> list = roleService.listMenuById(id);
        return CommonResult.success(list);
    }

    /**
     * 根据角色id获取相关资源列表
     *
     * @param id
     *         角色id
     * @return 菜单列表
     */
    @GetMapping("/listResource/{id}")
    public CommonResult listResourceById(@PathVariable("id") Long id) {
        List<UmsResource> list = roleService.listResourceById(id);
        return CommonResult.success(list);
    }

    /**
     * 分配角色菜单关联关系
     *
     * @param param
     *         参数对象
     * @return 统一返回结果
     */
    @PutMapping("allocMenus")
    public CommonResult allocMenus(@RequestBody @Validated UpdateRoleMenuParam param) {
        Boolean result = roleService.allocMenus(param.getRoleId(), param.getMenuIds());
        if (result) {
            return CommonResult.success("分配成功");
        }
        return CommonResult.fail("分配失败");
    }

    /**
     * 分配角色资源关联关系
     *
     * @param param
     *         参数对象
     * @return 统一返回结果
     */
    @PutMapping("allocResources")
    public CommonResult allocResources(@RequestBody @Validated UpdateRoleResourceParam param) {
        Boolean result = roleService.allocResources(param.getRoleId(), param.getResourceIds());
        if (result) {
            return CommonResult.success("分配成功");
        }
        return CommonResult.fail("分配失败");
    }

}
