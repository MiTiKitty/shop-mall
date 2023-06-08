package top.itcat.mall.admin.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.itcat.mall.admin.service.PmsBrandService;
import top.itcat.mall.common.api.CommonPage;
import top.itcat.mall.common.constant.RedisConstant;
import top.itcat.mall.common.service.RedisService;
import top.itcat.mall.entity.PmsBrand;
import top.itcat.mall.mapper.PmsBrandMapper;

import java.util.ArrayList;
import java.util.List;

/**
 * @className: PmsBrandServiceImpl <br/>
 * @description: 商品品牌服务实现类 <br/>
 * @author: CatKitty 33641 <br/>
 * @date: 2023/06/08 <br/>
 * @version: 1.0.0 <br/>
 */
@Slf4j
@Service
public class PmsBrandServiceImpl extends ServiceImpl<PmsBrandMapper, PmsBrand> implements PmsBrandService {

    @Autowired
    private RedisService redisService;

    @Override
    public List<PmsBrand> listAll() {
        // 有缓存读缓存
        String key = RedisConstant.QUERY_BRAND_ALL_KEY;
        String json = redisService.vGet(key);
        if (StrUtil.isNotBlank(json)) {
            return JSONUtil.toList(json, PmsBrand.class);
        }

        // 查库并入缓存
        List<PmsBrand> list = list();
        redisService.vSet(key, JSONUtil.toJsonStr(list), RedisConstant.QUERY_BRAND_ALL_TIME, RedisConstant.QUERY_BRAND_ALL_TIME_UNIT);
        return list;
    }

    @Override
    public boolean removeBrandById(Long id) {
        // TODO 删除逻辑
        return false;
    }

    @Override
    public boolean removeBrandByIds(List<Long> ids) {
        // TODO 删除逻辑
        return false;
    }

    @Override
    public void delCache() {
        redisService.del(RedisConstant.QUERY_BRAND_ALL_KEY);
    }

    @Override
    public CommonPage<PmsBrand> listByPage(String keyword, Integer showStatus, Integer pageNum, Integer pageSize) {
        QueryWrapper<PmsBrand> wrapper = new QueryWrapper<>();
        if (StrUtil.isNotBlank(keyword)) {
            wrapper.like("name", keyword);
        }
        if (showStatus!= null) {
            wrapper.and(e -> e.eq("show_status", showStatus));
        }
        Page<PmsBrand> page = new Page<>(pageNum, pageSize);
        page(page, wrapper);
        List<PmsBrand> vos = page.getRecords();
        if (pageNum * pageSize < page.getTotal()) {
            pageNum++;
        }
        return new CommonPage<>(pageNum, pageSize, (int) page.getPages(), page.getTotal(), vos);
    }

    @Override
    public boolean updateShowStatus(List<Long> ids, Integer showStatus) {
        List<PmsBrand> pmsBrands = new ArrayList<>(ids.size());
        for (Long id : ids) {
            PmsBrand pmsBrand = new PmsBrand();
            pmsBrand.setId(id);
            pmsBrand.setShowStatus(showStatus);
            pmsBrands.add(pmsBrand);
        }
        return updateBatchById(pmsBrands);
    }

    @Override
    public boolean updateFactoryStatus(List<Long> ids, Integer factoryStatus) {
        List<PmsBrand> pmsBrands = new ArrayList<>(ids.size());
        for (Long id : ids) {
            PmsBrand pmsBrand = new PmsBrand();
            pmsBrand.setId(id);
            pmsBrand.setFactoryStatus(factoryStatus);
            pmsBrands.add(pmsBrand);
        }
        return updateBatchById(pmsBrands);
    }

}
