package com.zqz.shop.service.business;

import com.mybatisflex.core.query.QueryWrapper;
import com.zqz.shop.entity.Region;
import com.zqz.shop.mapper.RegionMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import static com.zqz.shop.entity.table.RegionTableDef.REGION;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: RegionBusService
 * @Date: Created in 11:13 2023-8-23
 */
@Service
public class RegionBusService {
    private static List<Region> DtsRegions;
    @Resource
    private RegionMapper regionMapper;

    public Region queryById(Integer id) {
        return regionMapper.selectOneById(id);
    }


    public List<Region> queryAll() {
        byte b = 4;
        QueryWrapper wrapper = QueryWrapper.create();
        wrapper.select()
                .and(REGION.TYPE.ne(b));
        return regionMapper.selectListByQuery(wrapper);
    }

    public List<Region> getRegions() {
        if (DtsRegions == null) {
            createRegion();
        }
        return DtsRegions;
    }

    private synchronized void createRegion() {
        if (DtsRegions == null) {
            DtsRegions = queryAll();
        }
    }


    public List<Region> queryByPid(Integer pid) {
        QueryWrapper wrapper = QueryWrapper.create();
        wrapper.select()
                .and(REGION.PID.eq(pid));
        return regionMapper.selectListByQuery(wrapper);
    }
}
