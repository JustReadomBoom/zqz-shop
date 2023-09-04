package com.zqz.shop.service.business;

import com.mybatisflex.core.query.QueryWrapper;
import com.zqz.shop.entity.Storage;
import com.zqz.shop.mapper.StorageMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import static com.zqz.shop.entity.table.StorageTableDef.STORAGE;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: StorageBusService
 * @Date: Created in 16:13 2023-9-4
 */
@Service
public class StorageBusService {
    @Resource
    private StorageMapper storageMapper;


    public Storage queryByKey(String key) {
        QueryWrapper wrapper = QueryWrapper.create();
        wrapper.select()
                .and(STORAGE.KEY.eq(key))
                .and(STORAGE.DELETED.eq(false));
        return storageMapper.selectOneByQuery(wrapper);
    }

    public int add(Storage storage) {
        return storageMapper.insertSelective(storage);
    }
}
