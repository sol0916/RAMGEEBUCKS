package ramgee.project.vo;

public class CartOrderVO {
    private int cart_no;
    private int order_no;

    public CartOrderVO(int cart_no, int order_no) {
        this.cart_no = cart_no;
        this.order_no = order_no;
    }

    public int getCart_no() {
        return cart_no;
    }

    public void setCart_no(int cart_no) {
        this.cart_no = cart_no;
    }

    public int getOrder_no() {
        return order_no;
    }

    public void setOrder_no(int order_no) {
        this.order_no = order_no;
    }
}

