package top.itcat.mall.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.itcat.mall.admin.service.PmsProductAttributeCategoryService;
import top.itcat.mall.admin.vo.PmsProductAttributeCategoryNode;
import top.itcat.mall.common.api.CommonPage;
import top.itcat.mall.common.api.CommonResult;
import top.itcat.mall.entity.PmsProductAttributeCategory;

import java.util.List;

/**
 * @className: PmsProductAttributeCategoryController <br/>
 * @description: 商品属性扽类管理 <br/>
 * @author: CatKitty 33641 <br/>
 * @date: 2023/06/08 <br/>
 * @version: 1.0.0 <br/>
 */
@RestController
@RequestMapping("/productAttribute/category")
public class PmsProductAttributeCategoryController {

    @Autowired
    private PmsProductAttributeCategoryService productAttributeCategoryService;

    /**
     * 添加一个属性分类
     *
     * @param name
     *         实行分类名称
     * @return 统一返回结果
     */
    @PostMapping("create")
    public CommonResult create(@RequestParam("name") String name) {
        PmsProductAttributeCategory productAttributeCategory = new PmsProductAttributeCategory();
        productAttributeCategory.setName(name);
        productAttributeCategory.setParamCount(0);
        productAttributeCategory.setAttributeCount(0);
        boolean result = productAttributeCategoryService.save(productAttributeCategory);
        if (result) {
            productAttributeCategoryService.delCache();
            return CommonResult.success("添加成功");
        }
        return CommonResult.fail("添加失败");
    }

    /**
     * 根据id修改属性分类
     *
     * @param id
     *         id
     * @param name
     *         分类名称
     * @return 统一返回结果
     */
    @PutMapping("{id}")
    public CommonResult updateById(@PathVariable("id") Long id, @RequestParam("name") String name) {
        PmsProductAttributeCategory productAttributeCategory = new PmsProductAttributeCategory();
        productAttributeCategory.setId(id);
        productAttributeCategory.setName(name);
        boolean result = productAttributeCategoryService.updateById(productAttributeCategory);
        if (result) {
            productAttributeCategoryService.delCache();
            return CommonResult.success("修改成功");
        }
        return CommonResult.fail("修改失败");
    }

    /**
     * 根据id删除商品分类
     *
     * @param id
     *         id
     * @return 统一返回结果
     */
    @DeleteMapping("{id}")
    public CommonResult deleteById(@PathVariable("id") Long id) {
        boolean result = productAttributeCategoryService.removeCategoryById(id);
        if (result) {
            productAttributeCategoryService.delCache();
            return CommonResult.success("删除成功");
        }
        return CommonResult.fail("删除失败");
    }

    /**
     * 分页获取商品属性分类列表
     *
     * @param pageSize
     *         每页查询数量
     * @param pageNum
     *         当前页
     * @return 分页结果
     */
    @GetMapping("/list")
    public CommonResult list(@RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                             @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        CommonPage<PmsProductAttributeCategory> result = productAttributeCategoryService.listByPage(pageSize, pageNum);
        return CommonResult.success(result);
    }

    /**
     * 根据id获取属性分类
     *
     * @param id
     *         id
     * @return 实行分类对象
     */
    @GetMapping("{id}")
    public CommonResult info(@PathVariable("id") Long id) {
        PmsProductAttributeCategory productAttributeCategory = productAttributeCategoryService.getById(id);
        return CommonResult.success(productAttributeCategory);
    }

    /**
     * 获取所有商品属性分类及其下属性
     *
     * @return 分类列表
     */
    @GetMapping("/list/withAttr")
    public CommonResult getListWithAttr() {
        List<PmsProductAttributeCategoryNode> result = productAttributeCategoryService.getListWithAttr();
        return CommonResult.success(result);
    }

}
