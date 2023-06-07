package top.itcat.mall.admin.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.itcat.mall.admin.dto.CreateResourceCategoryParam;
import top.itcat.mall.admin.service.UmsResourceCategoryService;
import top.itcat.mall.common.api.CommonResult;
import top.itcat.mall.entity.UmsResourceCategory;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @className: UmsResourceCategoryController <br/>
 * @description: 后台资源分类管理 <br/>
 * @author: CatKitty 33641 <br/>
 * @date: 2023/06/06 <br/>
 * @version: 1.0.0 <br/>
 */
@RestController
@RequestMapping("/resource/category")
public class UmsResourceCategoryController {

    @Autowired
    private UmsResourceCategoryService resourceCategoryService;

    /**
     * 添加资源分类
     *
     * @param param
     *         参数对象
     * @return 统一返回结果
     */
    @PostMapping("create")
    public CommonResult create(@RequestBody @Validated CreateResourceCategoryParam param) {
        UmsResourceCategory category = new UmsResourceCategory();
        BeanUtils.copyProperties(param, category);
        category.setCreateTime(LocalDateTime.now());
        boolean result = resourceCategoryService.save(category);
        if (result) {
            return CommonResult.success("添加成功");
        }
        return CommonResult.fail("添加失败");
    }

    /**
     * 修改资源分类
     *
     * @param id
     *         分类id
     * @param param
     *         参数对象
     * @return 统一返回结果
     */
    @PutMapping("{id}")
    public CommonResult updateById(@PathVariable("id") Long id,
                                   @RequestBody @Validated CreateResourceCategoryParam param) {
        UmsResourceCategory category = new UmsResourceCategory();
        BeanUtils.copyProperties(param, category);
        category.setId(id);
        boolean result = resourceCategoryService.updateById(category);
        if (result) {
            return CommonResult.success("修改成功");
        }
        return CommonResult.fail("修改失败");
    }

    /**
     * 根据id删除资源分类
     *
     * @param id
     *         分类id
     * @return 统一返回结果
     */
    @DeleteMapping("{id}")
    public CommonResult removeById(@PathVariable("id") Long id) {
        Boolean result = resourceCategoryService.removeCategoryById(id);
        if (result) {
            return CommonResult.success("删除成功");
        }
        return CommonResult.fail("删除失败");
    }

    /**
     * 获取所有资源分类列表
     *
     * @return 资源分类列表
     */
    @GetMapping("list")
    public CommonResult listAll() {
        List<UmsResourceCategory> list = resourceCategoryService.list();
        return CommonResult.success(list);
    }

}
