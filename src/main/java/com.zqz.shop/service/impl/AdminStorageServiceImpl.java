package com.zqz.shop.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.zqz.shop.bean.admin.resp.CreateStorageResp;
import com.zqz.shop.entity.Storage;
import com.zqz.shop.service.AdminStorageService;
import com.zqz.shop.service.business.FileService;
import com.zqz.shop.service.business.StorageBusService;
import com.zqz.shop.utils.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: AdminStorageServiceImpl
 * @Date: Created in 16:05 2023-9-4
 */
@Service
@Slf4j
public class AdminStorageServiceImpl implements AdminStorageService {
    @Autowired
    private FileService fileService;
    @Autowired
    private StorageBusService storageBusService;

    @Override
    public Object doCreateStorage(Integer adminUserId, MultipartFile file) {
        if (ObjectUtil.isEmpty(adminUserId)) {
            return ResponseUtil.unlogin();
        }
        CreateStorageResp storageResp = new CreateStorageResp();
        try {
            String contentType = file.getContentType();
            String originalFilename = file.getOriginalFilename();
            String url = fileService.upload(file, originalFilename);
            if (StrUtil.isNotBlank(url) && StrUtil.isNotBlank(originalFilename)) {
                String key = generateKey(originalFilename);
                Storage storage = new Storage();
                storage.setName(originalFilename);
                storage.setSize((int) file.getSize());
                storage.setType(contentType);
                storage.setAddTime(new Date());
                storage.setKey(key);
                storage.setUrl(url);
                storageBusService.add(storage);
                storageResp.setUrl(url);
            } else {
                return ResponseUtil.fileUploadError();
            }
        } catch (Exception e) {
            log.error("文件上传失败:{}", e.getMessage());
            return ResponseUtil.fileUploadError();
        }
        return ResponseUtil.ok(storageResp);

    }

    private String generateKey(String originalFilename) {
        int index = originalFilename.lastIndexOf('.');
        String suffix = originalFilename.substring(index);

        String key;
        Storage storageInfo;

        do {
            key = RandomUtil.randomString(20) + suffix;
            storageInfo = storageBusService.queryByKey(key);
        } while (storageInfo != null);
        return key;
    }
}
