package cn.edu.hit.controller;


import cn.edu.hit.pay.MyWXPayConfig;
import cn.edu.hit.pay.WXPay;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import io.goeasy.GoEasy;
import org.apache.http.HttpResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/wxpay")
public class WXPayController {

    @RequestMapping("/dopay")
    public void doPay(HttpServletResponse response) throws Exception {
        // 订单时间
        // 商品id
        // 接收商户名称
        // 订单编号
        // 价格
        // 单位
        // 请求端ip
        // 接收回调地址 / 类型

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHssmm");
        String orderIdPrefix = sdf.format(date);
        String pid = "123" ;
        String orderId = orderIdPrefix + pid;

        MyWXPayConfig config = new MyWXPayConfig();
        WXPay wxPay = new WXPay(config);

        Map<String , String> data = new HashMap<>();
        data.put("body","撒欢购物");
        data.put("out_trade_no" , orderId);
        data.put("device_info","");
        data.put("fee_type","CNY"); //分
        data.put("total_fee","1");
        data.put("spbill_create_ip" , "123.12.12.123");


        data.put("notify_url","http://rznhwr.natappfree.cc/shop/wxpay/payback");
        data.put("trade_type","NATIVE");
        data.put("product_id","123");

        try{
            Map<String, String> resp = wxPay.unifiedOrder(data);

            System.out.println(resp);

            // 生成二维码
            String code_url = resp.get("code_url");

            // 二维码文本内容
            HashMap<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
            hints.put(EncodeHintType.MARGIN, 2);
            try {
                BitMatrix bitMatrix = new MultiFormatWriter().encode(code_url, BarcodeFormat.QR_CODE, 200, 200, hints);
                MatrixToImageWriter.writeToStream(bitMatrix, "PNG", response.getOutputStream());
                System.out.println("创建二维码完成");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }


    @RequestMapping("/payback")
    public void payback(HttpServletResponse response , HttpServletRequest request) throws IOException {
        ServletInputStream is = request.getInputStream();
        byte[] b = new byte[1024];
        int len = 0;
        while ((len = is.read(b)) != -1){
            String str = new String(b,0,len);
        }

        response.getWriter().write("<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>");

        GoEasy goEasy=new GoEasy("http://rest-hangzhou.goeasy.io","BC-446a48ba056f4cb8929b1056ec944cdc");
        goEasy.publish("WXPay","支付成功");

    }
}








