package com.zqz.shop.service.business;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.zqz.shop.common.Constant;
import com.zqz.shop.entity.Role;
import com.zqz.shop.mapper.RoleMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.zqz.shop.entity.table.RoleTableDef.ROLE;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: RoleBusService
 * @Date: Created in 14:49 2023-8-29
 */
@Service
public class RoleBusService {
    @Resource
    private RoleMapper roleMapper;


    public Set<String> queryByIds(List<Integer> roleIds) {
        Set<String> roles = new HashSet<>();
        if (CollectionUtil.isEmpty(roleIds)) {
            return roles;
        }
        QueryWrapper wrapper = QueryWrapper.create();
        wrapper.select()
                .and(ROLE.ID.in(roleIds))
                .and(ROLE.ENABLED.eq(true))
                .and(ROLE.DELETED.eq(false));
        List<Role> roleList = roleMapper.selectListByQuery(wrapper);
        if (CollectionUtil.isEmpty(roleList)) {
            return roles;
        }
        roleList.forEach(r -> roles.add(r.getName()));
        return roles;
    }

    public List<Role> queryAll() {
        QueryWrapper wrapper = QueryWrapper.create();
        wrapper.select()
                .and(ROLE.DELETED.eq(false));
        return roleMapper.selectListByQuery(wrapper);
    }

    public Page<Role> queryPage(Integer page, Integer limit, String roleName) {
        QueryWrapper wrapper = QueryWrapper.create();
        wrapper.select().and(Constant.WHERE_ONE_TO_ONE)
                .and(ROLE.DELETED.eq(false));
        if (StrUtil.isNotBlank(roleName)) {
            wrapper.and(ROLE.NAME.like(roleName));
        }
        wrapper.orderBy(ROLE.ADD_TIME.desc());
        return roleMapper.paginateWithRelations(page, limit, wrapper);
    }

    public boolean checkExist(String name) {
        QueryWrapper wrapper = QueryWrapper.create();
        wrapper.select()
                .and(ROLE.NAME.eq(name))
                .and(ROLE.DELETED.eq(false));
        return roleMapper.selectCountByQuery(wrapper) != 0;
    }

    public int add(Role role) {
        role.setAddTime(new Date());
        role.setUpdateTime(new Date());
        return roleMapper.insertSelective(role);
    }

    public int logicalDeleteById(Integer id) {
        return roleMapper.logicalDeleteById(id);
    }

    public int updateById(Role role) {
        role.setUpdateTime(new Date());
        return roleMapper.update(role);
    }
}
