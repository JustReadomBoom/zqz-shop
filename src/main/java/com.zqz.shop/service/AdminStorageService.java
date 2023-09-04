package com.zqz.shop.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: AdminStorageService
 * @Date: Created in 16:05 2023-9-4
 */
public interface AdminStorageService {
    Object doCreateStorage(Integer adminUserId, MultipartFile file);
}
