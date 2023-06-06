package top.itcat.mall.admin.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.itcat.mall.admin.dto.CreateMenuParam;
import top.itcat.mall.admin.service.UmsMenuService;
import top.itcat.mall.admin.vo.UmsMenuNode;
import top.itcat.mall.common.api.CommonPage;
import top.itcat.mall.common.api.CommonResult;
import top.itcat.mall.entity.UmsMenu;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @className: UmsMenuController <br/>
 * @description: 后台菜单管理接口 <br/>
 * @author: CatKitty 33641 <br/>
 * @date: 2023/06/06 <br/>
 * @version: 1.0.0 <br/>
 */
@RestController
@RequestMapping("menu")
public class UmsMenuController {

    @Autowired
    private UmsMenuService menuService;

    /**
     * 添加新菜单
     *
     * @param param
     *         添加参数
     * @return 统一返回结果
     */
    @PostMapping("create")
    public CommonResult create(@RequestBody @Validated CreateMenuParam param) {
        UmsMenu menu = new UmsMenu();
        BeanUtils.copyProperties(param, menu);
        menu.setCreateTime(LocalDateTime.now());
        boolean result = menuService.save(menu);
        if (result) {
            menuService.delCache();
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
    public CommonResult updateById(@PathVariable("id") Long id, @RequestBody CreateMenuParam param) {
        UmsMenu menu = new UmsMenu();
        BeanUtils.copyProperties(param, menu);
        menu.setId(id);
        boolean result = menuService.updateById(menu);
        if (result) {
            menuService.delCache();
            return CommonResult.success("修改成功");
        }
        return CommonResult.fail("修改失败");
    }

    /**
     * 根据菜单id删除菜单等信息
     *
     * @param id
     *         菜单id
     * @return 统一返回结果
     */
    @DeleteMapping("{id}")
    public CommonResult removeById(@PathVariable("id") Long id) {
        boolean result = menuService.removeMenuById(id);
        if (result) {
            menuService.delCache();
            return CommonResult.success("修改成功");
        }
        return CommonResult.fail("修改失败");
    }

    /**
     * 根据菜单id拿到菜单详情
     *
     * @param id
     *         菜单id
     * @return 菜单详情
     */
    @GetMapping("{id}")
    public CommonResult infoById(@PathVariable("id") Long id) {
        UmsMenu menu = menuService.getById(id);
        return CommonResult.success(menu);
    }

    /**
     * 分页获取菜单列表
     *
     * @param parentId
     *         父id
     * @param pageNum
     *         当前页
     * @param pageSize
     *         每页查询数量
     * @return 菜单列表
     */
    @GetMapping("/list/{parentId}")
    public CommonResult listByPage(@PathVariable("parentId") Long parentId,
                                   @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                   @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        CommonPage<UmsMenu> menus = menuService.listByPage(parentId, pageNum, pageSize);
        return CommonResult.success(menus);
    }

    /**
     * 以树形结构返回菜单列表
     *
     * @return 树形菜单列表
     */
    @GetMapping("/tree")
    public CommonResult tree() {
        List<UmsMenuNode> tree = menuService.tree();
        return CommonResult.success(tree);
    }

    /**
     * 修改菜单显示状态
     *
     * @param id
     *         菜单id
     * @param hidden
     *         显示状态
     * @return 统一返回结果
     */
    @PutMapping("/hidden/{id}")
    public CommonResult updateHiddenById(@PathVariable("id") Long id, @RequestParam("hidden") Integer hidden) {
        UmsMenu menu = new UmsMenu();
        menu.setHidden(hidden);
        menu.setId(id);
        boolean result = menuService.updateById(menu);
        if (result) {
            menuService.delCache();
            return CommonResult.success("修改成功");
        }
        return CommonResult.fail("修改失败");
    }

}
