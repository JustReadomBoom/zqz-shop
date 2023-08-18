package com.zqz.shop.service.business;

import com.mybatisflex.core.query.QueryWrapper;
import com.zqz.shop.entity.GoodsProduct;
import com.zqz.shop.mapper.GoodsProductMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import static com.zqz.shop.entity.table.GoodsProductTableDef.GOODS_PRODUCT;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: GoodsProductBusService
 * @Date: Created in 14:24 2023-8-18
 */
@Service
public class GoodsProductBusService {
    @Resource
    private GoodsProductMapper productMapper;


    public List<GoodsProduct> queryListByGoodsId(Integer id) {
        QueryWrapper wrapper = QueryWrapper.create();
        wrapper.select()
                .and(GOODS_PRODUCT.GOODS_ID.eq(id))
                .and(GOODS_PRODUCT.DELETED.eq(false));
        return productMapper.selectListByQuery(wrapper);
    }
}
