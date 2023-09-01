package com.zqz.shop.bean.admin;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: StatVo
 * @Date: Created in 13:53 2023-8-30
 */
@Data
public class StatVo implements Serializable {
    private static final long serialVersionUID = 7573418414206554815L;

    private String[] columns = new String[0];

    private List<Map> rows = new ArrayList<>();
}
