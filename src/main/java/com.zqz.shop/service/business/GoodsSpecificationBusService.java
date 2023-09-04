package com.zqz.shop.service.business;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.mybatisflex.core.query.QueryWrapper;
import com.zqz.shop.bean.GoodsSpecificationVo;
import com.zqz.shop.entity.GoodsSpecification;
import com.zqz.shop.mapper.GoodsSpecificationMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

import static com.zqz.shop.entity.table.GoodsSpecificationTableDef.GOODS_SPECIFICATION;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: GoodsSpecificationBusService
 * @Date: Created in 14:03 2023-8-18
 */
@Service
public class GoodsSpecificationBusService {
    @Resource
    private GoodsSpecificationMapper specificationMapper;


    public List<GoodsSpecification> queryByGoodsId(Integer id) {
        QueryWrapper wrapper = QueryWrapper.create();
        wrapper.select()
                .and(GOODS_SPECIFICATION.GOODS_ID.eq(id))
                .and(GOODS_SPECIFICATION.DELETED.eq(false));
        return specificationMapper.selectListByQuery(wrapper);
    }

    public List<GoodsSpecificationVo> queryListVoByGoodsId(Integer id) {
        List<GoodsSpecification> specificationList = queryByGoodsId(id);
        if (CollectionUtil.isEmpty(specificationList)) {
            return null;
        }

        Map<String, GoodsSpecificationVo> map = new HashMap<>();
        List<GoodsSpecificationVo> specificationVoList = new ArrayList<>();

        for (GoodsSpecification specification : specificationList) {
            String spec = specification.getSpecification();
            GoodsSpecificationVo specificationVo = map.get(spec);
            if (ObjectUtil.isEmpty(specificationVo)) {
                specificationVo = new GoodsSpecificationVo();
                specificationVo.setName(spec);
                List<GoodsSpecification> valueList = new ArrayList<>();
                valueList.add(specification);
                specificationVo.setValueList(valueList);
                map.put(spec, specificationVo);
                specificationVoList.add(specificationVo);
            } else {
                List<GoodsSpecification> valueList = specificationVo.getValueList();
                valueList.add(specification);
            }
        }
        return specificationVoList;
    }

    public int deleteByGoodsId(Integer id) {
        QueryWrapper wrapper = QueryWrapper.create();
        wrapper.select().and(GOODS_SPECIFICATION.GOODS_ID.eq(id));
        return specificationMapper.deleteByQuery(wrapper);
    }

    public int add(GoodsSpecification specification) {
        specification.setAddTime(new Date());
        return specificationMapper.insertSelective(specification);
    }
}
