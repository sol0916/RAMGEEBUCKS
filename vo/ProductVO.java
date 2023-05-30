package ramgee.project.vo;

public class ProductVO {
    private int product_no;
    private String name;
    private double price;
    private String description;
    private int amount;

    public ProductVO(int product_no, String name, double price, String description, int amount) {
        this.product_no = product_no;
        this.name = name;
        this.price = price;
        this.description = description;
        this.amount = amount;
    }

    public int getProduct_no() {
        return product_no;
    }

    public void setProduct_no(int product_no) {
        this.product_no = product_no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}

