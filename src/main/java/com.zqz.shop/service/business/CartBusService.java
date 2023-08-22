package com.zqz.shop.service.business;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.update.UpdateWrapper;
import com.zqz.shop.entity.Cart;
import com.zqz.shop.mapper.CartMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.Date;
import java.util.List;

import static com.zqz.shop.entity.table.CartTableDef.CART;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: CartBusService
 * @Date: Created in 14:54 2023-8-18
 */
@Service
public class CartBusService {
    @Resource
    private CartMapper cartMapper;


    public List<Cart> queryByUserId(Integer userId) {
        QueryWrapper wrapper = QueryWrapper.create();
        wrapper.select()
                .and(CART.USER_ID.eq(userId))
                .and(CART.DELETED.eq(false));
        return cartMapper.selectListByQuery(wrapper);
    }


    public Cart queryExist(Integer goodsId, Integer productId, Integer userId) {
        QueryWrapper wrapper = QueryWrapper.create();
        wrapper.select()
                .and(CART.GOODS_ID.eq(goodsId))
                .and(CART.PRODUCT_ID.eq(productId))
                .and(CART.USER_ID.eq(userId))
                .and(CART.DELETED.eq(false));
        return cartMapper.selectOneByQuery(wrapper);
    }

    public int add(Cart cart) {
        return cartMapper.insertSelective(cart);
    }

    public int updateById(Cart cart) {
        cart.setUpdateTime(new Date());
        return cartMapper.update(cart);
    }

    public int updateByParam(Integer userId, List<Integer> productIds, Boolean isChecked) {
        return cartMapper.updateByProductIds(isChecked, productIds, userId, false);
    }
}
