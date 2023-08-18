package com.zqz.shop.service.business;

import com.mybatisflex.core.query.QueryWrapper;
import com.zqz.shop.entity.Ad;
import com.zqz.shop.mapper.AdMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import static com.zqz.shop.entity.table.AdTableDef.AD;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: AdBusService
 * @Date: Created in 15:46 2023-8-16
 */
@Service
public class AdBusService {
    @Resource
    private AdMapper adMapper;


    public List<Ad> queryAdList() {
        QueryWrapper wrapper = QueryWrapper.create();
        wrapper.select()
                .where(AD.POSITION.eq(1))
                .and(AD.DELETED.eq(false))
                .and(AD.ENABLED.eq(true));
        return adMapper.selectListByQuery(wrapper);
    }







}
