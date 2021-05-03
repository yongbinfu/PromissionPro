package com.fuyongbin.web;

import com.fuyongbin.domain.AjaxRes;
import com.fuyongbin.domain.Menu;
import com.fuyongbin.domain.PageListRes;
import com.fuyongbin.domain.QueryVo;
import com.fuyongbin.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.net.idn.Punycode;

import java.util.List;

@Controller
public class MenuController {
    @Autowired
    private MenuService menuService;

    @RequestMapping("/menu")
    public String menu() {
        return "menu";
    }

    @RequestMapping("/menuList")
    @ResponseBody
    public PageListRes menuList(QueryVo vo) {
        System.out.println("来到了menu控制层");
        PageListRes pageListRes = menuService.getmenuList(vo);
        System.out.println(pageListRes);
        return pageListRes;
    }

    @RequestMapping("/parentList")
    @ResponseBody
    public List<Menu> getparentList() {
        return menuService.getparentList();
    }

    @RequestMapping("/saveMenu")
    @ResponseBody
    public AjaxRes saveMenu(Menu menu) {
        AjaxRes ajaxRes = new AjaxRes();
        try {
            menuService.saveMenu(menu);
            ajaxRes.setSuccess(true);
            ajaxRes.setMessage("保存成功");
        } catch (Exception e) {
            ajaxRes.setSuccess(false);
            ajaxRes.setMessage("保存失败");
        }
        return ajaxRes;
    }
    @RequestMapping("/updataMenu")
    @ResponseBody
    public AjaxRes updataMenu(Menu menu){
        return menuService.updataMenu(menu);
    }
    @RequestMapping("/delectMenu")
    @ResponseBody
    public AjaxRes delectMenu(Long id){
        return menuService.delectMenuById(id);
    }
    @RequestMapping("/getTreeData")
    @ResponseBody
    public List<Menu> getTreeData(){
        return menuService.getTreeData();
    }
}
