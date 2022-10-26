package cn.edu.hit.controller;

import cn.edu.hit.po.User;
import cn.edu.hit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;
    // 注册
    @RequestMapping("/toregister")
    public  String toregister(){
        // 页面
        return "register";
    }


    @RequestMapping("/changeName")
    @ResponseBody
    public String changeName(String name){
        // 判断名字是否可用
        int i = userService.changeName(name);
        if(i == 0){
            return "ok";
        }else{
            return "no";
        }
    }

    @RequestMapping("/doregister")
    public String doregister(User user , Model model){

        // 用户名 去掉多余空格
        if(user.getUsername() == null || user.getUsername().trim() == ""){
            model.addAttribute("error","注册失败");
            return  "register";
        }

        //验证密码
        String pattern = "^(?=.*[a-zA-Z])(?=.*[\\d])(?=.*[\\W])[a-zA-Z\\d\\W]{6,16}$";

        if(!user.getPassword().matches(pattern)){
            model.addAttribute("error","注册失败");
            return "register";
        }

        //验证手机号
        String phontPatter="^1[3,5,7,8][\\d]{9}$";
        if (!user.getPhone().matches(phontPatter)){
            model.addAttribute("error","注册失败");
            return "register";
        }

        // 添加用户
        userService.addUser(user);

        // 去登陆  重定向

        return "redirect:http://localhost:7070/shop/user/tologin";
    }

    @RequestMapping("/tologin")
    public  String tologin(){
        return  "login";
    }



    @RequestMapping("/dologin")
    public String dologin(User user , String checkBox , HttpSession httpSession, HttpServletResponse response ,Model model){
        // 校验user信息  --> Dao

        User user1 = userService.login(user);
        if(user1 == null){
            // 登陆错误  用户不存在
            model.addAttribute("error","用户名不存在,或用户名密码错误");
            return "login";
        }else{
            // 登陆正确

            // 信息存起来   域对象
            // pagecontext  当前页面有效
            // request      一次请求内有效
            // senvletcontext       服务区
            // session      一次会话有效

            httpSession.setAttribute("user",user1);
            // 是否够选了记录密码

            if("on".equals(checkBox)){
                // 记住密码
                Cookie cookie1 = new Cookie("name", user1.getUsername());
                Cookie cookie2 = new Cookie("pass", user1.getPassword());

                cookie1.setPath("/");
                cookie2.setPath("/");

                // 时效性
                cookie1.setMaxAge(60*60*24*7);
                cookie2.setMaxAge(60*60*24*7);

                response.addCookie(cookie1);
                response.addCookie(cookie2);
            }else{
                // 不记住  存过 , 要删掉

                Cookie cookie1 = new Cookie("name", user1.getUsername());
                Cookie cookie2 = new Cookie("pass", user1.getPassword());

                cookie1.setPath("/");
                cookie2.setPath("/");

                // 时效性
                cookie1.setMaxAge(0);
                cookie2.setMaxAge(0);

                response.addCookie(cookie1);
                response.addCookie(cookie2);
            }

            // 登陆成功  进入首页   // md5(md5())

            return "redirect:http://localhost:7070/shop/index/toindex";

        }

    }


//    @RequestMapping("/test")
//    public String test(){
//        userService.test();
//        return null;
//    }
}


















