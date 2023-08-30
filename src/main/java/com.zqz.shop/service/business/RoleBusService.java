package com.zqz.shop.service.business;

import cn.hutool.core.collection.CollectionUtil;
import com.mybatisflex.core.query.QueryWrapper;
import com.zqz.shop.entity.Role;
import com.zqz.shop.mapper.RoleMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
}
