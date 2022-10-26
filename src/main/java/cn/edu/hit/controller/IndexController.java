package cn.edu.hit.controller;

import cn.edu.hit.po.CategoryExt;
import cn.edu.hit.po.Product;
import cn.edu.hit.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletContext;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/index")
public class IndexController {
    @Autowired
    IndexService indexService;

    // ServletContext
    @Autowired
    ServletContext servletContext;

    @RequestMapping("/toindex")
    public String toindex(Model model){
        // 1级  + 2级  在域对象中

        List<CategoryExt> category= (List<CategoryExt>) servletContext.getAttribute("category");

//        List<CategoryExt> category = indexService.getCategory();
//        // 发给前台
//        model.addAttribute("category",category);

        // 根据二级分类id --> 产品详情

        Map<Integer, List<Product>> product = indexService.getProduct(category);
        model.addAttribute("product",product);
        return "index";
    }
}
