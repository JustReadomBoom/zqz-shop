package com.zqz.shop.service.business;

import cn.hutool.core.util.StrUtil;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.zqz.shop.entity.Admin;
import com.zqz.shop.mapper.AdminMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

import static com.zqz.shop.entity.table.AdminTableDef.ADMIN;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: AdminBusService
 * @Date: Created in 11:04 2023-8-25
 */
@Service
public class AdminBusService {
    @Resource
    private AdminMapper adminMapper;


    public List<Admin> queryByUserName(String userName) {
        QueryWrapper wrapper = QueryWrapper.create();
        wrapper.select()
                .and(ADMIN.USERNAME.eq(userName))
                .and(ADMIN.DELETED.eq(false));
        return adminMapper.selectListByQuery(wrapper);
    }

    public Admin queryByUserId(Integer userId) {
        QueryWrapper wrapper = QueryWrapper.create();
        wrapper.select()
                .and(ADMIN.ID.eq(userId))
                .and(ADMIN.DELETED.eq(false));
        return adminMapper.selectOneByQuery(wrapper);
    }

    public Page<Admin> queryPage(Integer page, Integer limit, String username) {
        QueryWrapper wrapper = QueryWrapper.create();
        QueryWrapper select = wrapper.select().and("Constant.WHERE_ONE_TO_ONE");
        if (StrUtil.isNotBlank(username)) {
            select = select.and(ADMIN.USERNAME.like(username));
        }
        select.and(ADMIN.DELETED.eq(false))
                .orderBy(ADMIN.ADD_TIME.desc());
        return adminMapper.paginateWithRelations(page, limit, select);
    }

    public List<Admin> queryAllAdmin() {
        QueryWrapper wrapper = QueryWrapper.create();
        wrapper.select()
                .and(ADMIN.DELETED.eq(false));
        return adminMapper.selectListByQuery(wrapper);
    }

    public int logicalDeleteById(Integer id) {
        return adminMapper.logicalDeleteById(id);
    }

    public int updateById(Admin admin) {
        admin.setUpdateTime(new Date());
        return adminMapper.update(admin);
    }
}
