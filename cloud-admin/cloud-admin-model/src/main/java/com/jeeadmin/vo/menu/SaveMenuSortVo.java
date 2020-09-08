package com.jeeadmin.vo.menu;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 修改角色排序
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SaveMenuSortVo {
    /**
     * 菜单ID
     */
    private Long menuId;
    /**
     * 显示顺序
     */
    private Integer menuSort;
}
