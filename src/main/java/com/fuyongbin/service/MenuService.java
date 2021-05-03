package com.fuyongbin.service;

import com.fuyongbin.domain.AjaxRes;
import com.fuyongbin.domain.Menu;
import com.fuyongbin.domain.PageListRes;
import com.fuyongbin.domain.QueryVo;

import java.util.List;

public interface MenuService {

    PageListRes getmenuList(QueryVo vo);

    List<Menu> getparentList();

    void saveMenu(Menu menu);

    AjaxRes updataMenu(Menu menu);

    AjaxRes delectMenuById(Long id);

    List<Menu> getTreeData();
}
