package com.fuyongbin.service.impl;

import com.fuyongbin.domain.*;
import com.fuyongbin.mapper.MenuMapper;
import com.fuyongbin.service.MenuService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {
    @Autowired
    private MenuMapper menuMapper;

    @Override
    public PageListRes getmenuList(QueryVo vo) {
        Page<Object> page = PageHelper.startPage(vo.getPage(), vo.getRows());
        List<Menu> menus = menuMapper.selectAll();
        PageListRes pageListRes = new PageListRes();
        pageListRes.setTotal(page.getTotal());
        pageListRes.setRows(menus);
        return pageListRes;
    }

    @Override
    public List<Menu> getparentList() {
        return menuMapper.selectAll();
    }

    @Override
    public void saveMenu(Menu menu) {
        menuMapper.saveMenu(menu);
    }

    @Override
    public AjaxRes updataMenu(Menu menu) {
        AjaxRes ajaxRes = new AjaxRes();
        /*不能设置自己的子菜单为父菜单*/
        /*先查询出当前选择的父菜单id*/
        Long id = menu.getParent().getId();
        /*查询该id对应的menu2 */
        Long parent_id=menuMapper.selectParentIdById(id);
        if (menu.getId()==parent_id){
            ajaxRes.setSuccess(false);
            ajaxRes.setMessage("不能设置自己的子菜单为父菜单");
            return ajaxRes ;
        }

        try {
            menuMapper.updateByPrimaryKey(menu);
            ajaxRes.setSuccess(true);
            ajaxRes.setMessage("保存成功");
        } catch (Exception e) {
            ajaxRes.setSuccess(false);
            ajaxRes.setMessage("保存失败");
        }
        return ajaxRes;
    }

    @Override
    public AjaxRes delectMenuById(Long id) {
        AjaxRes ajaxRes = new AjaxRes();
        try {
            /*1.打破菜单关系*/
            menuMapper.updateMenuRel(id);
            /*2.删除菜单*/
            menuMapper.deleteByPrimaryKey(id);
            ajaxRes.setSuccess(true);
            ajaxRes.setMessage("保存成功");
        } catch (Exception e) {
            ajaxRes.setSuccess(false);
            ajaxRes.setMessage("保存失败");
        }
        return ajaxRes;
    }

    @Override
    public List<Menu> getTreeData() {
        List<Menu> treeData = menuMapper.getTreeData();
        Subject subject = SecurityUtils.getSubject();
        Employee employee = (Employee) subject.getPrincipal();
        if (!employee.getAdmin()){
            checkPermission(treeData);
        }
        return treeData;
    }
    public void checkPermission(List<Menu> menus){
        /*获取主体*/
        Subject subject = SecurityUtils.getSubject();
        /*迭代器遍历menus菜单*/
        Iterator<Menu> iterator = menus.iterator();
        while (iterator.hasNext()){
            Menu menu = iterator.next();
            if (menu.getPermission()!=null){
                /*判断当前menu是否有权限对象，如果没有当前遍历的菜单从集合当中消除*/
                String presource = menu.getPermission().getPresource();
                if (!subject.isPermitted(presource)){
                    iterator.remove();
                }
            }
            if (menu.getChildren().size()>0){
                checkPermission(menu.getChildren());
            }
        }
    }


}
