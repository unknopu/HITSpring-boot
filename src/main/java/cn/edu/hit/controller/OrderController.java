package cn.edu.hit.controller;


import cn.edu.hit.po.*;
import cn.edu.hit.service.OrderService;
import cn.edu.hit.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    ProductService productService;

    @Autowired
    HttpSession session;

    @Autowired
    OrderService service;

    @RequestMapping("/toverify")
    public  String toverify(){
        return  "verify";
    }

    // 从立即购买进入订单
    @RequestMapping("/toverify1")
    public String  toverify1(Integer pId , Model model){
        Product productById = productService.getProductById(pId);

        model.addAttribute("product",productById);

        return "verify1";
    }

    //doPay

    @RequestMapping("/doPay")
    public String doPay(Order order , Model model){
        // 收件地址
        User user = (User) session.getAttribute("user");

        if(user == null){
            //未登陆
            return "login";
        }

        Cart cart = (Cart) session.getAttribute("cart");

        // 新增订单

        Integer oId = service.addOrder(order,user,cart);

        model.addAttribute("oId",oId);

        session.setAttribute("cart",null);
        return "pay";

    }



    //doPay1
    @RequestMapping("/doPay1")
    public String doPay1(Order order,Integer pId,Model model){
        // 收件地址
        User user = (User) session.getAttribute("user");

        if(user == null){
            //未登陆
            return "login";
        }

        Product productById = productService.getProductById(pId);

        // 购物车

        CartItem cartItem = new CartItem();
        cartItem.setCount(1);
        cartItem.setProduct(productById);
        Cart cart = new Cart();
        cart.setMap(cartItem);
        Integer oId = service.addOrder(order,user,cart);

        model.addAttribute("oId",oId);

        return  "pay";

    }

    // 我的订单

    @RequestMapping("/toMyOrder")
    public String toMyOrder(ProductExt<OrderExt> productExt , Model model){
        User user = (User) session.getAttribute("user");

        if(user == null){
            return  "login";
        }

        productExt.setPageSize(3);

        ProductExt<OrderExt> orderExtList = service.selallOrder(user.getuId() , productExt);

        // 订单状态

        Map<Long , Integer> map =  service.selState(user.getuId());

        model.addAttribute("map",map);
        model.addAttribute("orderExt",orderExtList);

        return  "myOrder";
    }


    // 订单单项去付款
    // 更新状态

    @RequestMapping("/doPay2")
    public String doPay2(Order order , Model model){
        User user = (User) session.getAttribute("user");
        if(user == null){
            return  "login";
        }

        Integer i = service.updateState(order);// 1 √

        model.addAttribute("oId",order.getoId());

        if(i == i){
            return "pay";
        }else{
            return "PayError";
        }

    }

    // 删除订单

    @RequestMapping("/updatastate")
    public  String updatestate(Order order){
        Integer i = service.updateState(order);
        if(i == 1){
            return "redirect:http://localhost:7070/shop/order/toMyOrder";
        }else{
            return "PayError";
        }
    }


    // 确认收货

    @RequestMapping("toconfirmReceipt")

    public ModelAndView toconfirmReceipt(Order order){
        OrderExt orderExt = service.selAll(order.getoId());

        ModelAndView mv = new ModelAndView();

        mv.setViewName("confirmReceipt");
        mv.addObject("orderExt",orderExt);

        return mv;

    }

    @RequestMapping("/topaySuccess")
    public String topaySuccess(Integer oId , Model model){
        // 支付成功  订单状态
        service.upId(oId);
        
        // 查询商品信息

        OrderExt orderExt = service.selAll(oId);

        model.addAttribute("orderExt", orderExt);

        return "paySuccess";

    }
}
















