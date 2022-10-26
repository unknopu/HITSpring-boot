package cn.edu.hit.po;

public class CartItem {

    private Product product;

    private Integer count = 0;

    private  double subTotal = 0.0;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public double getSubTotal() {

        return count * product.getShopPrice();
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }
}
