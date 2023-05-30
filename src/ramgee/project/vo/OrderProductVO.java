package ramgee.project.vo;

public class OrderProductVO {
    private int order_no;
    private int product_no;
    private int quantity;

    public OrderProductVO(int order_no, int product_no, int quantity) {
        this.order_no = order_no;
        this.product_no = product_no;
        this.quantity = quantity;
    }

    public int getOrder_no() {
        return order_no;
    }

    public void setOrder_no(int order_no) {
        this.order_no = order_no;
    }

    public int getProduct_no() {
        return product_no;
    }

    public void setProduct_no(int product_no) {
        this.product_no = product_no;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

