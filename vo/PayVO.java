package ramgee.project.vo;

import java.sql.Date;

public class PayVO {
    private int pay_no;
    private int order_no;
    private double amountPaid;
    private Date paymentDate;

    public PayVO(int pay_no, int order_no, double amountPaid, Date paymentDate) {
        this.pay_no = pay_no;
        this.order_no = order_no;
        this.amountPaid = amountPaid;
        this.paymentDate = paymentDate;
    }

    public int getPay_no() {
        return pay_no;
    }

    public void setPay_no(int pay_no) {
        this.pay_no = pay_no;
    }

    public int getOrder_no() {
        return order_no;
    }

    public void setOrder_no(int order_no) {
        this.order_no = order_no;
    }

    public double getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(double amountPaid) {
        this.amountPaid = amountPaid;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }
}
