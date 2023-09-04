package com.zqz.shop.controller.admin;

import com.zqz.shop.annotation.AdminLoginUser;
import com.zqz.shop.entity.Comment;
import com.zqz.shop.service.AdminCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: AdminCommentController
 * @Date: Created in 11:20 2023-9-1
 */
@RestController
@RequestMapping("/admin/comment")
public class AdminCommentController {
    @Autowired
    private AdminCommentService adminCommentService;

    @GetMapping("/list")
    public Object queryList(@AdminLoginUser Integer adminUserId,
                            @RequestParam(defaultValue = "1") Integer page,
                            @RequestParam(defaultValue = "10") Integer limit,
                            String userId,
                            String valueId) {
        return adminCommentService.doQueryList(adminUserId, page, limit, userId, valueId);
    }


    @PostMapping("/delete")
    public Object deleteInfo(@AdminLoginUser Integer adminUserId,
                             @RequestBody Comment comment) {
        return adminCommentService.doDeleteInfo(adminUserId, comment);
    }
}
