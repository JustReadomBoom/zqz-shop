package com.zqz.shop.service.business;

import com.mybatisflex.core.query.QueryWrapper;
import com.zqz.shop.entity.GoodsAttribute;
import com.zqz.shop.mapper.GoodsAttributeMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

import static com.zqz.shop.entity.table.GoodsAttributeTableDef.GOODS_ATTRIBUTE;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: GoodsAttributeBusService
 * @Date: Created in 13:47 2023-8-18
 */
@Service
public class GoodsAttributeBusService {

    @Resource
    private GoodsAttributeMapper goodsAttributeMapper;


    public List<GoodsAttribute> queryListByGoodsId(Integer id) {
        QueryWrapper wrapper = QueryWrapper.create();
        wrapper.select()
                .and(GOODS_ATTRIBUTE.GOODS_ID.eq(id))
                .and(GOODS_ATTRIBUTE.DELETED.eq(false));
        return goodsAttributeMapper.selectListByQuery(wrapper);
    }


    public int deleteByGoodsId(Integer id) {
        QueryWrapper wrapper = QueryWrapper.create();
        wrapper.select().and(GOODS_ATTRIBUTE.GOODS_ID.eq(id));
        return goodsAttributeMapper.deleteByQuery(wrapper);
    }

    public int add(GoodsAttribute attribute) {
        attribute.setAddTime(new Date());
        attribute.setUpdateTime(new Date());
        return goodsAttributeMapper.insertSelective(attribute);
    }
}
