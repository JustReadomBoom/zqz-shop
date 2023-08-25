package com.zqz.shop.service.business;

import com.mybatisflex.core.query.QueryWrapper;
import com.zqz.shop.entity.Admin;
import com.zqz.shop.mapper.AdminMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
}
