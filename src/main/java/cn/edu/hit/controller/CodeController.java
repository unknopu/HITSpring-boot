package cn.edu.hit.controller;

import cn.dsna.util.images.ValidateCode;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.validation.Validator;
import java.io.IOException;

@Controller
@RequestMapping("/val")
public class CodeController {

    @RequestMapping("/Code")
    public  void getCode(HttpServletResponse response , HttpSession httpSession){
        try {
            // 1. 生成图片          1,宽  2, 高   3,字母的个数  4, 干扰线
            ValidateCode validateCode = new ValidateCode(165,66,4,100);
            // 2. 记录答案
            String code = validateCode.getCode();
            httpSession.setAttribute("code",code);
            // 3. 返回给前端   流
            validateCode.write(response.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @RequestMapping("checkValidate")
    @ResponseBody
    public String checkValidate(String code , HttpSession httpSession){
        String code1 = (String) httpSession.getAttribute("code");
        if(code1.equalsIgnoreCase(code)){
            return "ok";
        }else{
            return "no";
        }
    }
}












