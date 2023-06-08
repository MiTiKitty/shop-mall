package top.itcat.mall.admin.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.itcat.mall.admin.dto.CreateBrandParam;
import top.itcat.mall.admin.dto.DeleteBrandParam;
import top.itcat.mall.admin.service.PmsBrandService;
import top.itcat.mall.common.api.CommonPage;
import top.itcat.mall.common.api.CommonResult;
import top.itcat.mall.entity.PmsBrand;

import java.util.List;

/**
 * @className: PmsBrandController <br/>
 * @description: 商品品牌管理 <br/>
 * @author: CatKitty 33641 <br/>
 * @date: 2023/06/08 <br/>
 * @version: 1.0.0 <br/>
 */
@RestController
@RequestMapping("brand")
public class PmsBrandController {

    @Autowired
    private PmsBrandService brandService;

    /**
     * 获取所有品牌列表
     *
     * @return 品牌列表
     */
    @GetMapping("listAll")
    public CommonResult listAll() {
        List<PmsBrand> list = brandService.listAll();
        return CommonResult.success(list);
    }

    /**
     * 根据品牌名称分页获取品牌列表
     *
     * @param keyword
     *         关键词：品牌名称
     * @param showStatus
     *         显示状态
     * @param pageNum
     *         当前页
     * @param pageSize
     *         每页查询数量
     * @return 分页结果
     */
    @GetMapping("list")
    public CommonResult list(@RequestParam(value = "keyword", required = false) String keyword,
                             @RequestParam(value = "showStatus", required = false) Integer showStatus,
                             @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                             @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize) {
        CommonPage<PmsBrand> result = brandService.listByPage(keyword, showStatus, pageNum, pageSize);
        return CommonResult.success(result);
    }

    /**
     * 根据id查询品牌信息
     *
     * @param id
     *         id
     * @return 品牌信息
     */
    @GetMapping("{id}")
    public CommonResult info(@PathVariable("id") Long id) {
        PmsBrand brand = brandService.getById(id);
        return CommonResult.success(brand);
    }

    /**
     * 创建新的品牌
     *
     * @param param
     *         参数对象
     * @return 统一返回结果
     */
    @PostMapping("create")
    public CommonResult create(@RequestBody @Validated CreateBrandParam param) {
        PmsBrand pmsBrand = new PmsBrand();
        BeanUtils.copyProperties(param, pmsBrand);
        pmsBrand.setProductCount(0);
        pmsBrand.setProductCommentCount(0);
        boolean result = brandService.save(pmsBrand);
        if (result) {
            brandService.delCache();
            return CommonResult.success("添加成功");
        }
        return CommonResult.fail("添加失败");
    }

    /**
     * 根据id修改品牌
     *
     * @param id
     *         id
     * @param param
     *         参数对象
     * @return 统一返回结果
     */
    @PutMapping("{id}")
    public CommonResult updateById(@PathVariable("id") Long id, @RequestBody @Validated CreateBrandParam param) {
        PmsBrand pmsBrand = new PmsBrand();
        BeanUtils.copyProperties(param, pmsBrand);
        pmsBrand.setId(id);
        boolean result = brandService.updateById(pmsBrand);
        if (result) {
            brandService.delCache();
            return CommonResult.success("修改成功");
        }
        return CommonResult.fail("修改失败");
    }

    /**
     * 批量修改品牌显示状态
     *
     * @param showStatus
     *         显示状态
     * @param param
     *         参数对象
     * @return 统一返回结果
     */
    @PutMapping("showStatus")
    public CommonResult updateShowStatus(@RequestParam("showStatus") Integer showStatus,
                                         @RequestBody @Validated DeleteBrandParam param) {
        List<Long> ids = param.getIds();
        boolean result = brandService.updateShowStatus(ids, showStatus);
        if (result) {
            brandService.delCache();
            return CommonResult.success("修改成功");
        }
        return CommonResult.fail("修改失败");
    }

    /**
     * 批量更新厂家制造商状态
     *
     * @param factoryStatus
     *         厂家制造商状态
     * @param param
     *         参数对象
     * @return 统一返回结果
     */
    @PutMapping("factoryStatus")
    public CommonResult updateFactoryStatus(@RequestParam("factoryStatus") Integer factoryStatus,
                                         @RequestBody @Validated DeleteBrandParam param) {
        List<Long> ids = param.getIds();
        boolean result = brandService.updateFactoryStatus(ids, factoryStatus);
        if (result) {
            brandService.delCache();
            return CommonResult.success("修改成功");
        }
        return CommonResult.fail("修改失败");
    }

    /**
     * 根据id删除品牌
     *
     * @param id
     *         id
     * @return 统一返回结果
     */
    @DeleteMapping("{id}")
    public CommonResult removeBrandById(@PathVariable("id") Long id) {
        boolean result = brandService.removeBrandById(id);
        if (result) {
            brandService.delCache();
            return CommonResult.success("删除成功");
        }
        return CommonResult.fail("删除失败");
    }

    /**
     * 批量删除品牌
     *
     * @param param
     *         参数对象
     * @return 统一返回结果
     */
    @DeleteMapping("del")
    public CommonResult removeBrandByIds(@RequestBody @Validated DeleteBrandParam param) {
        List<Long> ids = param.getIds();
        boolean result = brandService.removeBrandByIds(ids);
        if (result) {
            brandService.delCache();
            return CommonResult.success("删除成功");
        }
        return CommonResult.fail("删除失败");
    }

}
