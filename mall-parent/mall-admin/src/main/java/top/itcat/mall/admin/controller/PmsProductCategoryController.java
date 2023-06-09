package top.itcat.mall.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.itcat.mall.admin.dto.PmsProductCategoryNode;
import top.itcat.mall.admin.dto.PmsProductCategoryParam;
import top.itcat.mall.admin.dto.UpdateProductCategoryShowStatusParam;
import top.itcat.mall.admin.service.PmsProductCategoryService;
import top.itcat.mall.common.api.CommonPage;
import top.itcat.mall.common.api.CommonResult;
import top.itcat.mall.entity.PmsProductCategory;

import java.util.List;

/**
 * @className: PmsProductCategoryController <br/>
 * @description: 商品分类管理 <br/>
 * @author: CatKitty 33641 <br/>
 * @date: 2023/06/08 <br/>
 * @version: 1.0.0 <br/>
 */
@RestController
@RequestMapping("/productCategory")
public class PmsProductCategoryController {

    @Autowired
    private PmsProductCategoryService categoryService;

    /**
     * 添加商品分类
     *
     * @param param
     *         参数对象
     * @return 统一返回结果
     */
    @PostMapping("create")
    public CommonResult create(@RequestBody @Validated PmsProductCategoryParam param) {
        boolean result = categoryService.create(param);
        if (result) {
            categoryService.delCache();
            return CommonResult.success("添加成功");
        }
        return CommonResult.fail("添加失败");
    }

    /**
     * 通过id修改商品分类
     *
     * @param id
     *         id
     * @param param
     *         参数对象
     * @return 统一返回结果
     */
    @PutMapping("{id}")
    public CommonResult updateById(@PathVariable("id") Long id,
                                   @RequestBody @Validated PmsProductCategoryParam param) {
        boolean result = categoryService.updateCategoryById(id, param);
        if (result) {
            categoryService.delCache();
            return CommonResult.success("修改成功");
        }
        return CommonResult.fail("修改失败");
    }

    /**
     * 根据id修改商品分类导航栏状态
     *
     * @param id
     *         id
     * @param navStatus
     *         导航栏状态
     * @return 统一返回结果
     */
    @PutMapping("/navStatus/{id}")
    public CommonResult updateNavStatusById(@PathVariable("id") Long id,
                                            @RequestParam("navStatus") Integer navStatus) {
        PmsProductCategory pmsProductCategory = new PmsProductCategory();
        pmsProductCategory.setId(id);
        pmsProductCategory.setNavStatus(navStatus);
        boolean result = categoryService.updateById(pmsProductCategory);
        if (result) {
            categoryService.delCache();
            return CommonResult.success("修改成功");
        }
        return CommonResult.fail("修改失败");
    }

    /**
     * 修改分类显示状态
     *
     * @param param
     *         参数对象
     * @return 统一返回结果
     */
    @PutMapping("showStatus")
    public CommonResult updateShowStatus(@RequestBody @Validated UpdateProductCategoryShowStatusParam param) {
        Boolean result = categoryService.updateShowStatus(param.getIds(), param.getShowStatus());
        if (result) {
            categoryService.delCache();
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
    public CommonResult delById(@PathVariable("id") Long id) {
        boolean result = categoryService.removeCategoryById(id);
        if (result) {
            categoryService.delCache();
            return CommonResult.success("删除成功");
        }
        return CommonResult.fail("删除失败");
    }

    /**
     * 分页查询商品分类
     *
     * @param parentId
     *         分类父id
     * @param pageSize
     *         每页查询数量
     * @param pageNum
     *         当前页
     * @return 分页结果
     */
    @GetMapping("/list/{parentId}")
    public CommonResult list(@PathVariable("parentId") Long parentId,
                             @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                             @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        CommonPage<PmsProductCategory> result = categoryService.listByPage(parentId, pageSize, pageNum);
        return CommonResult.success(result);
    }

    /**
     * 根据id拿到商品分类对象
     *
     * @param id
     *         id
     * @return 分类信息
     */
    @GetMapping("{id}")
    public CommonResult info(@PathVariable("id") Long id) {
        PmsProductCategory productCategory = categoryService.getById(id);
        return CommonResult.success(productCategory);
    }

    /**
     * 以树状形式查询所有商品分类
     *
     * @return 分类树
     */
    @GetMapping("tree")
    public CommonResult tree() {
        List<PmsProductCategoryNode> tree = categoryService.getTree();
        return CommonResult.success(tree);
    }

}
