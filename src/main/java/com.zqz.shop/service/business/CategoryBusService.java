package com.zqz.shop.service.business;

import cn.hutool.core.util.StrUtil;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.zqz.shop.entity.Category;
import com.zqz.shop.mapper.CategoryMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import static com.zqz.shop.entity.table.CategoryTableDef.CATEGORY;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: CategoryBusService
 * @Date: Created in 17:13 2023-8-17
 */
@Service
public class CategoryBusService {
    @Resource
    private CategoryMapper categoryMapper;


    public List<Category> queryListPage(Integer pageNumber, Integer pageSize) {
        QueryWrapper wrapper = QueryWrapper.create();
        wrapper.select()
                .where(CATEGORY.LEVEL.eq("L1"))
                .and(CATEGORY.DELETED.eq(false));
        return categoryMapper.paginateWithRelations(pageNumber, pageSize, wrapper).getRecords();
    }

    public Category queryById(Integer id) {
        return categoryMapper.selectOneById(id);
    }

    public List<Category> queryByParentId(Integer id) {
        QueryWrapper wrapper = QueryWrapper.create();
        wrapper.select()
                .where(CATEGORY.PID.eq(id))
                .and(CATEGORY.DELETED.eq(false));
        return categoryMapper.selectListByQuery(wrapper);
    }

    public List<Category> queryL2ByIds(List<Integer> catIds) {
        QueryWrapper wrapper = QueryWrapper.create();
        wrapper.select()
                .where(CATEGORY.LEVEL.eq("L2"))
                .and(CATEGORY.ID.in(catIds))
                .and(CATEGORY.DELETED.eq(false));
        return categoryMapper.selectListByQuery(wrapper);
    }

    public List<Category> queryL1() {
        QueryWrapper wrapper = QueryWrapper.create();
        wrapper.select()
                .and(CATEGORY.LEVEL.eq("L1"))
                .and(CATEGORY.DELETED.eq(false));
        return categoryMapper.selectListByQuery(wrapper);
    }

    public Page<Category> queryPage(Integer page, Integer limit, String id, String name) {
        QueryWrapper wrapper = QueryWrapper.create();
        wrapper.select().and("1 = 1");
        if (StrUtil.isNotBlank(id)) {
            wrapper.and(CATEGORY.ID.eq(id));
        }
        if (StrUtil.isNotBlank(name)) {
            wrapper.and(CATEGORY.NAME.like(name));
        }
        wrapper.and(CATEGORY.DELETED.eq(false))
                .orderBy(CATEGORY.ADD_TIME.desc());
        return categoryMapper.paginateWithRelations(page, limit, wrapper);
    }
}
