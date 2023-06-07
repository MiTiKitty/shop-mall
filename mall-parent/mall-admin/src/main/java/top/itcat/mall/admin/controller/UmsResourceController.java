package top.itcat.mall.admin.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.itcat.mall.admin.dto.CreateResourceParam;
import top.itcat.mall.admin.service.UmsResourceService;
import top.itcat.mall.common.api.CommonPage;
import top.itcat.mall.common.api.CommonResult;
import top.itcat.mall.entity.UmsResource;
import top.itcat.mall.security.component.DynamicSecurityMetadataSource;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @className: UmsResourceController <br/>
 * @description: 后台资源管理 <br/>
 * @author: CatKitty 33641 <br/>
 * @date: 2023/06/06 <br/>
 * @version: 1.0.0 <br/>
 */
@RestController
@RequestMapping("resource")
public class UmsResourceController {

    @Autowired
    private UmsResourceService resourceService;

    @Autowired
    private DynamicSecurityMetadataSource dynamicSecurityMetadataSource;

    /**
     * 获取所有资源列表
     *
     * @return 资源列表
     */
    @GetMapping("/list/all")
    public CommonResult listAll() {
        List<UmsResource> all = resourceService.listAll();
        return CommonResult.success(all);
    }

    /**
     * 分页查询资源列表
     *
     * @param categoryId
     *         分类id
     * @param nameKeyword
     *         关键词：资源名称
     * @param urlKeyword
     *         关键词：url关键词
     * @param pageSize
     *         每页查询数量
     * @param pageNum
     *         当前页
     * @return 分页查询结果
     */
    @GetMapping("list")
    public CommonResult listByPage(@RequestParam(value = "categoryId", required = false) Long categoryId,
                                   @RequestParam(value = "nameKeyword", required = false) String nameKeyword,
                                   @RequestParam(value = "urlKeyword", required = false) String urlKeyword,
                                   @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                   @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        CommonPage<UmsResource> result = resourceService.listByPage(categoryId, nameKeyword, urlKeyword, pageSize, pageNum);
        return CommonResult.success(result);
    }

    /**
     * 根据资源id查询你资源详情
     *
     * @param id
     *         资源id
     * @return 资源详情
     */
    @GetMapping("{id}")
    public CommonResult infoById(@PathVariable("id") Long id) {
        UmsResource resource = resourceService.getById(id);
        return CommonResult.success(resource);
    }

    /**
     * 添加新的资源
     *
     * @param param
     *         参数对象
     * @return 统一返回结果
     */
    @PostMapping("create")
    public CommonResult create(@RequestBody @Validated CreateResourceParam param) {
        UmsResource resource = new UmsResource();
        BeanUtils.copyProperties(param, resource);
        resource.setCreateTime(LocalDateTime.now());
        boolean result = resourceService.save(resource);
        if (result) {
            dynamicSecurityMetadataSource.clearDataSource();
            resourceService.delCache();
            return CommonResult.success("添加成功");
        }
        return CommonResult.fail("添加失败");
    }

    /**
     * 根据资源id修改资源
     *
     * @param id
     *         资源id
     * @param param
     *         参数对象
     * @return 统一返回结果
     */
    @PutMapping("{id}")
    public CommonResult updateById(@PathVariable("id") Long id, @RequestBody @Validated CreateResourceParam param) {
        UmsResource resource = new UmsResource();
        BeanUtils.copyProperties(param, resource);
        resource.setId(id);
        boolean result = resourceService.updateById(resource);
        if (result) {
            dynamicSecurityMetadataSource.clearDataSource();
            resourceService.delCache();
            return CommonResult.success("修改成功");
        }
        return CommonResult.fail("修改失败");
    }

    /**
     * 根据资源id删除资源
     *
     * @param id
     *         资源id
     * @return 统一返回结果
     */
    @DeleteMapping("{id}")
    public CommonResult removeById(@PathVariable("id") Long id) {
        Boolean result = resourceService.removeResourceById(id);
        if (result) {
            dynamicSecurityMetadataSource.clearDataSource();
            resourceService.delCache();
            return CommonResult.success("删除成功");
        }
        return CommonResult.fail("删除失败");
    }

}
