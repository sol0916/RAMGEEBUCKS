package ramgee.project.vo;

import java.util.List;

public class CartVO {
    private int cart_no;
    private int member_no;
    private List<OrderVO> orders;

    public CartVO(int cart_no, int member_no, List<OrderVO> orders) {
        this.cart_no = cart_no;
        this.member_no = member_no;
        this.orders = orders;
    }

    public int getCart_no() {
        return cart_no;
    }

    public void setCart_no(int cart_no) {
        this.cart_no = cart_no;
    }

    public int getMember_no() {
        return member_no;
    }

    public void setMember_no(int member_no) {
        this.member_no = member_no;
    }

    public List<OrderVO> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderVO> orders) {
        this.orders = orders;
    }
}
