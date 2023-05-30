package ramgee.project.vo;

import java.sql.Date;
import java.util.List;

public class OrderVO {
    private int order_no;
    private int cart_no;
    private Date orderDate;
    private double totalAmount;
    private String status;
    private List<ProductVO> products;

    public OrderVO(int order_no, int cart_no, Date orderDate, double totalAmount, String status, List<ProductVO> products) {
        this.order_no = order_no;
        this.cart_no = cart_no;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
        this.status = status;
        this.products = products;
    }

    public int getOrder_no() {
        return order_no;
    }

    public void setOrder_no(int order_no) {
        this.order_no = order_no;
    }

    public int getCart_no() {
        return cart_no;
    }

    public void setCart_no(int cart_no) {
        this.cart_no = cart_no;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ProductVO> getProducts() {
        return products;
    }

    public void setProducts(List<ProductVO> products) {
        this.products = products;
    }
}

