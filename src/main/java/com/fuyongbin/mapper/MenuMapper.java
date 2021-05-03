package com.fuyongbin.mapper;

import com.fuyongbin.domain.Menu;
import com.fuyongbin.domain.QueryVo;

import java.util.List;

public interface MenuMapper {
    int deleteByPrimaryKey(Long id);

    /*int insert(Menu record);*/

    Menu selectByPrimaryKey(Long id);

    List<Menu> selectAll();

    int updateByPrimaryKey(Menu record);

    void saveMenu(Menu menu);

    Long selectParentIdById(Long id);

    void updateMenuRel(Long id);

    List<Menu> getTreeData();

    List<Menu> listMenuChild();
}