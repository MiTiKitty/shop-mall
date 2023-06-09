package top.itcat.mall.admin.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.itcat.mall.admin.dto.DeleteProductAttributeParam;
import top.itcat.mall.admin.dto.PmsProductAttributeParam;
import top.itcat.mall.admin.service.PmsProductAttributeService;
import top.itcat.mall.admin.vo.ProductAttrInfo;
import top.itcat.mall.common.api.CommonPage;
import top.itcat.mall.common.api.CommonResult;
import top.itcat.mall.entity.PmsProductAttribute;

import java.util.List;

/**
 * @className: PmsProductAttributeController <br/>
 * @description: 商品属性管理 <br/>
 * @author: CatKitty 33641 <br/>
 * @date: 2023/06/08 <br/>
 * @version: 1.0.0 <br/>
 */
@RestController
@RequestMapping("/productAttribute")
public class PmsProductAttributeController {

    @Autowired
    private PmsProductAttributeService productAttributeService;

    /**
     * 添加商品属性信息
     *
     * @param param
     *         参数对象
     * @return 统一返回结果
     */
    @PostMapping("/create")
    public CommonResult create(@RequestBody @Validated PmsProductAttributeParam param) {
        PmsProductAttribute pmsProductAttribute = new PmsProductAttribute();
        BeanUtils.copyProperties(param, pmsProductAttribute);
        boolean result = productAttributeService.create(pmsProductAttribute);
        if (result) {
            return CommonResult.success("添加成功");
        }
        return CommonResult.fail("添加失败");
    }

    /**
     * 根据id修改商品属性
     *
     * @param id
     *         id
     * @param param
     *         参数对象
     * @return 统一返回结果
     */
    @PutMapping("{id}")
    public CommonResult updateById(@PathVariable("id") Long id,
                                   @RequestBody @Validated PmsProductAttributeParam param) {
        PmsProductAttribute pmsProductAttribute = new PmsProductAttribute();
        BeanUtils.copyProperties(param, pmsProductAttribute);
        pmsProductAttribute.setId(id);
        boolean result = productAttributeService.updateAttributeById(pmsProductAttribute);
        if (result) {
            return CommonResult.success("修改成功");
        }
        return CommonResult.fail("修改失败");
    }

    /**
     * 批量删除商品属性
     *
     * @param param
     *         参数对象
     * @return 统一返回结果
     */
    @DeleteMapping("del")
    public CommonResult deleteByIds(@RequestBody @Validated DeleteProductAttributeParam param) {
        List<Long> ids = param.getIds();
        boolean result = productAttributeService.removeAttributeByIds(ids);
        if (result) {
            return CommonResult.success("删除成功");
        }
        return CommonResult.fail("删除失败");
    }

    /**
     * 根据分类查询属性列表或参数列表
     *
     * @param cid
     *         所属分类id
     * @param type
     *         0表示属性，1表示参数
     * @param pageSize
     *         每页查询数量
     * @param pageNum
     *         当前页
     * @return 分页结果
     */
    @GetMapping("/list/{cid}")
    public CommonResult list(@PathVariable("cid") Long cid,
                             @RequestParam(value = "type") Integer type,
                             @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                             @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        CommonPage<PmsProductAttribute> result = productAttributeService.listByPage(cid, type, pageSize, pageNum);
        return CommonResult.success(result);
    }

    /**
     * 根据id获取商品属性
     *
     * @param id
     *         id
     * @return 商品属性对象
     */
    @GetMapping("{id}")
    public CommonResult info(@PathVariable("id") Long id) {
        PmsProductAttribute productAttribute = productAttributeService.getById(id);
        return CommonResult.success(productAttribute);
    }

    /**
     * 根据商品分类的id获取商品属性及属性分类
     *
     * @param pid
     *         商品分类id
     * @return 结果集
     */
    @GetMapping("/attrInfo/{pid}")
    public CommonResult getAttrInfo(@PathVariable("pid") Long pid) {
        List<ProductAttrInfo> result = productAttributeService.getAttrInfo(pid);
        return CommonResult.success(result);
    }

}
