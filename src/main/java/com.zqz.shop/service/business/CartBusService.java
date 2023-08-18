package com.zqz.shop.service.business;

import com.mybatisflex.core.query.QueryWrapper;
import com.zqz.shop.entity.Cart;
import com.zqz.shop.mapper.CartMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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


}
