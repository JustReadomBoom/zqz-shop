package com.zqz.shop.service.business;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.mybatisflex.core.query.QueryWrapper;
import com.zqz.shop.bean.GoodsProductVo;
import com.zqz.shop.entity.GoodsProduct;
import com.zqz.shop.mapper.GoodsProductMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
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


    public List<GoodsProductVo> queryListByGoodsId(Integer id) {
        QueryWrapper wrapper = QueryWrapper.create();
        wrapper.select()
                .and(GOODS_PRODUCT.GOODS_ID.eq(id))
                .and(GOODS_PRODUCT.DELETED.eq(false));
        List<GoodsProduct> productList = productMapper.selectListByQuery(wrapper);
        if (CollectionUtil.isNotEmpty(productList)) {
            List<GoodsProductVo> resultList = new ArrayList<>();
            for (GoodsProduct product : productList) {
                GoodsProductVo productVo = new GoodsProductVo();
                BeanUtil.copyProperties(product, productVo);
                productVo.setSpecifications(JSONUtil.parseArray(product.getSpecifications()).toList(String.class));
                resultList.add(productVo);
            }
            return resultList;
        }
        return null;
    }

    public GoodsProduct queryById(Integer id) {
        return productMapper.selectOneById(id);
    }
}
