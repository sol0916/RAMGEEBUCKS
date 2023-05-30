package ramgee.project.dao;

import ramgee.project.db.DBProperties;
import ramgee.project.vo.CartOrderVO;
import ramgee.project.vo.CartVO;
import ramgee.project.vo.OrderVO;
import ramgee.project.vo.ProductVO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CartDAO {
    private String url = DBProperties.URL;
    private String uid = DBProperties.UID;
    private String upw = DBProperties.UPW;
    private Connection connection;

    public CartDAO() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            connection = DriverManager.getConnection(url, uid, upw);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void addCart(CartVO cartVO) {
        String sql = "INSERT INTO carts (cart_no, member_no) VALUES (cart_no_seq.NEXTVAL, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, cartVO.getMember_no());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    public void updateCart(CartVO cartVO) {
        String sql = "UPDATE carts SET member_no = ? WHERE cart_no = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, cartVO.getMember_no());
            statement.setInt(2, cartVO.getCart_no());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    public void deleteCart(int cart_no) {
        String sql = "DELETE FROM carts WHERE cart_no = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, cart_no);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    public CartVO findCartByCartNo(int cart_no) {
        String sql = "SELECT * FROM carts WHERE cart_no = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, cart_no);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    CartVO cartVO = new CartVO(
                            resultSet.getInt("cart_no"),
                            resultSet.getInt("member_no"),
                            findOrdersByCartNo(cart_no)
                    );
                    return cartVO;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }

        return null;
    }

    public List<CartVO> findAllCarts() {
        List<CartVO> cart_list = new ArrayList<>();
        String sql = "SELECT * FROM carts";
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int cart_no = resultSet.getInt("cart_no");
                int member_no = resultSet.getInt("member_no");

                List<OrderVO> orders = findOrdersByCartNo(cart_no);

                CartVO cartVO = new CartVO(cart_no, member_no, orders);
                cart_list.add(cartVO);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return cart_list;
    }

    private List<OrderVO> findOrdersByCartNo(int cart_no) {
        List<OrderVO> orders = new ArrayList<>();
        String sql = "SELECT o.order_no, o.cart_no, o.order_date, o.total_amount, o.status " +
                "FROM orders o " +
                "JOIN cart_order co ON o.order_no = co.order_no " +
                "WHERE co.cart_no = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, cart_no);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int order_no = resultSet.getInt("order_no");
                    int cartNo = resultSet.getInt("cart_no");
                    Date orderDate = resultSet.getDate("order_date");
                    double totalAmount = resultSet.getDouble("total_amount");
                    String status = resultSet.getString("status");

                    List<ProductVO> products = findProductsByOrderNo(order_no);

                    OrderVO orderVO = new OrderVO(order_no, cartNo, orderDate, totalAmount, status, products);
                    orders.add(orderVO);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orders;
    }

    private List<ProductVO> findProductsByOrderNo(int order_no) {
        List<ProductVO> product_list = new ArrayList<>();
        String sql = "SELECT p.product_no, p.name, p.price, p.description, p.amount " +
                "FROM products p " +
                "JOIN order_product op ON p.product_no = op.product_no " +
                "WHERE op.order_no = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, order_no);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int product_no = resultSet.getInt("product_no");
                    String name = resultSet.getString("name");
                    double price = resultSet.getDouble("price");
                    String description = resultSet.getString("description");
                    int amount = resultSet.getInt("amount");

                    ProductVO productVO = new ProductVO(product_no, name, price, description, amount);
                    product_list.add(productVO);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return product_list;
    }

    private void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
