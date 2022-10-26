package cn.edu.hit.pay;

public class MyWXPayDomain implements IWXPayDomain{
    @Override
    public void report(String domain, long elapsedTimeMillis, Exception ex) {

    }

    @Override
    public DomainInfo getDomain(WXPayConfig config) {
        // 微信支付的后外太地址
        DomainInfo domainInfo = new DomainInfo("api.mch.weixin.qq.com",true);
        return domainInfo;
    }
}
