package com.zqz.shop.service.business;

import cn.hutool.core.util.StrUtil;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.zqz.shop.common.Constant;
import com.zqz.shop.entity.Brand;
import com.zqz.shop.mapper.BrandMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import static com.zqz.shop.entity.table.BrandTableDef.BRAND;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: BrandBusService
 * @Date: Created in 16:08 2023-8-21
 */
@Service
public class BrandBusService {
    @Resource
    private BrandMapper brandMapper;

    public Brand queryById(Integer id) {
        return brandMapper.selectOneById(id);
    }

    public List<Brand> getAdminBrands(Integer userId) {
        QueryWrapper wrapper = QueryWrapper.create();
        wrapper.select()
                .and(BRAND.ADMIN_ID.eq(userId))
                .and(BRAND.DELETED.eq(false));
        return brandMapper.selectListByQuery(wrapper);
    }

    public List<Brand> queryAll() {
        QueryWrapper wrapper = QueryWrapper.create();
        wrapper.select()
                .and(BRAND.DELETED.eq(false));
        return brandMapper.selectListByQuery(wrapper);

    }

    public Page<Brand> queryPage(Integer page, Integer limit, String id, String name) {
        QueryWrapper wrapper = QueryWrapper.create();
        wrapper.select().and(Constant.WHERE_ONE_TO_ONE);
        if (StrUtil.isNotBlank(id)) {
            wrapper.and(BRAND.ID.eq(id));
        }
        if (StrUtil.isNotBlank(name)) {
            wrapper.and(BRAND.NAME.like(name));
        }
        wrapper.and(BRAND.DELETED.eq(false))
                .orderBy(BRAND.ADD_TIME.desc());
        return brandMapper.paginateWithRelations(page, limit, wrapper);
    }
}
