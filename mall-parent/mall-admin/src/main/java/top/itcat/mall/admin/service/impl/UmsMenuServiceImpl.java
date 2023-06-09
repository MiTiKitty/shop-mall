package top.itcat.mall.admin.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.itcat.mall.admin.service.UmsMenuService;
import top.itcat.mall.admin.service.UmsRoleMenuRelationService;
import top.itcat.mall.admin.vo.UmsMenuNode;
import top.itcat.mall.common.api.CommonPage;
import top.itcat.mall.common.constant.RedisConstant;
import top.itcat.mall.common.service.RedisService;
import top.itcat.mall.entity.UmsMenu;
import top.itcat.mall.mapper.UmsMenuMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @className: UmsMenuServiceImpl <br/>
 * @description: 菜单服务实现类 <br/>
 * @author: CatKitty 33641 <br/>
 * @date: 2023/06/05 <br/>
 * @version: 1.0.0 <br/>
 */
@Slf4j
@Service
public class UmsMenuServiceImpl extends ServiceImpl<UmsMenuMapper, UmsMenu> implements UmsMenuService {

    @Autowired
    private RedisService redisService;

    @Autowired
    private UmsRoleMenuRelationService relationService;

    @Override
    public List<UmsMenu> listByAdminId(Long adminId) {
        return baseMapper.selectListByAdminId(adminId);
    }

    @Override
    public List<UmsMenu> listByRoleId(Long roleId) {
        // 有缓存读缓存
        String key = RedisConstant.QUERY_MENU_BY_ROLE_ID_KEY + roleId;
        String menuJson = redisService.vGet(key);
        if (StrUtil.isNotBlank(menuJson)) {
            return JSONUtil.toList(menuJson, UmsMenu.class);
        }

        // 缓存中没有再去数据库中查找,查找完成再入缓存
        List<UmsMenu> allList = baseMapper.selectListByRoleId(roleId);
        redisService.vSet(key, JSONUtil.toJsonStr(allList), RedisConstant.QUERY_MENU_BY_ROLE_ID_TIME, RedisConstant.QUERY_MENU_BY_ROLE_ID_TIME_UNIT);
        return allList;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean removeMenuById(Long id) {
        // 先删自己
        int delete = baseMapper.deleteById(id);

        // 再删角色菜单联系
        boolean result = relationService.delByMenuId(id);
        delCache();
        return result;
    }

    @Override
    public CommonPage<UmsMenu> listByPage(Long parentId, Integer pageNum, Integer pageSize) {
        QueryWrapper<UmsMenu> wrapper = new QueryWrapper<UmsMenu>();
        wrapper.eq("parent_id", parentId);
        Page<UmsMenu> page = new Page<>(pageNum, pageSize);
        page(page, wrapper);
        List<UmsMenu> vos = page.getRecords();
        if (pageNum * pageSize < page.getTotal()) {
            pageNum++;
        }
        return new CommonPage<>(pageNum, pageSize, (int) page.getPages(), page.getTotal(), vos);
    }

    @Override
    public List<UmsMenuNode> tree() {
        // 先去缓存中查找树形菜单
        String key = RedisConstant.QUERY_MENU_TREE_KEY;
        String treeJson = redisService.vGet(key);
        if (StrUtil.isNotBlank(treeJson)) {
            return JSONUtil.toList(treeJson, UmsMenuNode.class);
        }

        // 缓存中没有再去数据库中查找
        List<UmsMenu> allList = list();
        List<UmsMenuNode> nodeList = allList.stream().map(it -> {
            UmsMenuNode node = new UmsMenuNode();
            BeanUtils.copyProperties(it, node);
            node.setChildren(new ArrayList<>());
            return node;
        }).collect(Collectors.toList());
        Map<Long, UmsMenuNode> map = new HashMap<>();
        for (UmsMenuNode node : nodeList) {
            map.put(node.getId(), node);
        }
        List<UmsMenuNode> result = new ArrayList<>();
        for (UmsMenuNode node : nodeList) {
            UmsMenuNode parent = map.get(node.getParentId());
            if (parent == null) {
                result.add(node);
            } else {
                parent.getChildren().add(node);
            }
        }

        // 存入缓存
        treeJson = JSONUtil.toJsonStr(result);
        redisService.vSet(key, treeJson, RedisConstant.QUERY_MENU_TREE_TIME, RedisConstant.QUERY_MENU_TREE_TIME_UNIT);
        return result;
    }

    @Override
    public void delCache() {
        // 删除树形结构的缓存
        redisService.del(RedisConstant.QUERY_MENU_TREE_KEY, RedisConstant.QUERY_MENU_BY_ROLE_ID_KEY + "*");
    }

    @Override
    public void delCacheByRoleId(Long roleId) {
        redisService.del(RedisConstant.QUERY_MENU_BY_ROLE_ID_KEY + roleId);
    }

    @Override
    public void delCacheByRoleIds(List<Long> roleIds) {
        String[] keys = new String[roleIds.size()];
        for (int i = 0; i < roleIds.size(); i++) {
            keys[i] = RedisConstant.QUERY_MENU_BY_ROLE_ID_KEY + roleIds.get(i);
        }
        redisService.del(keys);
    }

}
