package com.zqz.shop.service.business;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.zqz.shop.entity.Goods;
import com.zqz.shop.mapper.GoodsMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.List;

import static com.zqz.shop.entity.table.GoodsTableDef.GOODS;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: GoodsBusService
 * @Date: Created in 15:33 2023-8-16
 */
@Service
public class GoodsBusService {
    @Resource
    private GoodsMapper goodsMapper;


    public Object doQueryCount() {
        QueryWrapper wrapper = QueryWrapper.create();
        wrapper.select()
                .where(GOODS.IS_ON_SALE.eq(true))
                .and(GOODS.DELETED.eq(false));
        return goodsMapper.selectCountByQuery(wrapper);
    }

    public Page<Goods> queryByParam(Integer categoryId, String keyword, Boolean isNew, Boolean isHot, Integer page, Integer size) {
        QueryWrapper wrapper = QueryWrapper.create();
        QueryWrapper select = wrapper.select().where("1 = 1");
        if (ObjectUtil.isNotEmpty(categoryId)) {
            select = select.and(GOODS.CATEGORY_ID.eq(categoryId));
        }
        if (StrUtil.isNotBlank(keyword)) {
            select = select.and(GOODS.KEYWORDS.like(keyword));
        }
        if (ObjectUtil.isNotEmpty(isNew)) {
            select = select.and(GOODS.IS_NEW.eq(isNew));
        }
        if (ObjectUtil.isNotEmpty(isHot)) {
            select = select.and(GOODS.IS_HOT.eq(isHot));
        }
        select.and(GOODS.IS_ON_SALE.eq(true))
                .and(GOODS.DELETED.eq(false))
                .orderBy(GOODS.ADD_TIME.desc());
        return goodsMapper.paginateWithRelations(page, size, wrapper);
    }

    public List<Integer> queryCategoryIds(String keyword, Boolean isNew, Boolean isHot) {
        List<Integer> ids = new ArrayList<>();
        QueryWrapper wrapper = QueryWrapper.create();
        QueryWrapper select = wrapper.select().where("1 = 1");
        if (StrUtil.isNotBlank(keyword)) {
            select = select.and(GOODS.KEYWORDS.like(keyword));
        }
        if (ObjectUtil.isNotEmpty(isNew)) {
            select = select.and(GOODS.IS_NEW.eq(isNew));
        }
        if (ObjectUtil.isNotEmpty(isHot)) {
            select = select.and(GOODS.IS_HOT.eq(isHot));
        }
        select.and(GOODS.IS_ON_SALE.eq(true))
                .and(GOODS.DELETED.eq(false))
                .orderBy(GOODS.ADD_TIME.desc());
        List<Goods> goods = goodsMapper.selectListByQuery(wrapper);
        if (CollectionUtil.isNotEmpty(goods)) {
            for (Goods good : goods) {
                ids.add(good.getId());
            }
        }
        return ids;
    }

    public Goods queryById(Integer id) {
        QueryWrapper wrapper = QueryWrapper.create();
        wrapper.select()
                .and(GOODS.ID.eq(id))
                .and(GOODS.DELETED.eq(false));
        return goodsMapper.selectOneByQuery(wrapper);
    }

    public Integer queryCount() {
        QueryWrapper wrapper = QueryWrapper.create();
        wrapper.select()
                .and(GOODS.DELETED.eq(false));
        return (int) goodsMapper.selectCountByQuery(wrapper);

    }
}
