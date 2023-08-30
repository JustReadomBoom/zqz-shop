package com.zqz.shop.service.business;

import cn.hutool.core.collection.CollectionUtil;
import com.mybatisflex.core.query.QueryWrapper;
import com.zqz.shop.entity.SysPermission;
import com.zqz.shop.mapper.SysPermissionMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.zqz.shop.entity.table.SysPermissionTableDef.SYS_PERMISSION;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: SysPermissionBusService
 * @Date: Created in 15:01 2023-8-29
 */
@Service
public class SysPermissionBusService {
    @Resource
    private SysPermissionMapper permissionMapper;

    public Set<String> queryByRoles(List<Integer> roleIds) {
        Set<String> permissions = new HashSet<>();
        if (CollectionUtil.isEmpty(roleIds)) {
            return permissions;
        }
        QueryWrapper wrapper = QueryWrapper.create();
        wrapper.select()
                .and(SYS_PERMISSION.ROLE_ID.in(roleIds))
                .and(SYS_PERMISSION.DELETED.eq(false));
        List<SysPermission> permissionList = permissionMapper.selectListByQuery(wrapper);
        if (CollectionUtil.isEmpty(permissionList)) {
            return permissions;
        }
        permissionList.forEach(p -> {
            permissions.add(p.getPermission());
        });
        return permissions;
    }
}
