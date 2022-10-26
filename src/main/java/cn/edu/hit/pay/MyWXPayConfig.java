package cn.edu.hit.pay;

import java.io.InputStream;

public class MyWXPayConfig extends WXPayConfig{

    // APPID
    @Override
    String getAppID() {

        return "wx796055a9a5d2822b";
    }

    // 商户号
    @Override
    String getMchID() {
        return "1617197168";
    }


    // APPSecret
    @Override
    String getKey() {
        return "sahuan66sahuan66sahuan66sahuan66";
    }

    @Override
    InputStream getCertStream() {
        return null;
    }

    @Override
    IWXPayDomain getWXPayDomain() {
        MyWXPayDomain myWXPayDomain = new MyWXPayDomain();

        return myWXPayDomain;
    }
}
