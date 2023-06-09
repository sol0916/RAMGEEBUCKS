package ramgee.project.dao;

import ramgee.project.db.DBProperties;
import ramgee.project.vo.OrderVO;
import ramgee.project.vo.ProductVO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {
	
	//field
    private String url = DBProperties.URL;
    private String uid = DBProperties.UID;
    private String upw = DBProperties.UPW;
    private Connection connection;

    //생성자
    public OrderDAO() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            connection = DriverManager.getConnection(url, uid, upw);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    //주문 추가
    public void addOrder(OrderVO orderVO) {
        String sql = "INSERT INTO orders (order_no, cart_no, order_date, total_amount, total_price, status) VALUES (order_no_seq.NEXTVAL, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, orderVO.getCart_no());
            statement.setDate(2, orderVO.getOrderDate());
            statement.setDouble(3, orderVO.getTotalAmount());
            statement.setInt(4, orderVO.getTotalPrice());
            statement.setString(5, orderVO.getStatus());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }
    
    //주문 수정
    public void updateOrder(OrderVO orderVO) {
        String sql = "UPDATE orders SET cart_no = ?, order_date = ?, total_amount = ?, total_price = ?, status = ? WHERE order_no = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, orderVO.getCart_no());
            statement.setDate(2, orderVO.getOrderDate());
            statement.setDouble(3, orderVO.getTotalAmount());
            statement.setInt(4, orderVO.getTotalPrice());
            statement.setString(5, orderVO.getStatus());
            statement.setInt(6, orderVO.getOrder_no());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    //주문 삭제
    public void deleteOrder(int order_no) {
        String sql = "DELETE FROM orders WHERE order_no = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, order_no);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    //
    public OrderVO findOrderByOrderNo(int order_no) {
        String sql = "SELECT * FROM orders WHERE order_no = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, order_no);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    OrderVO order = new OrderVO(
                            resultSet.getInt("order_no"),
                            resultSet.getInt("cart_no"),
                            resultSet.getDate("order_date"),
                            resultSet.getDouble("total_amount"),
                            resultSet.getInt("total_price"),
                            resultSet.getString("status"),
                            findProductsByOrderNo(order_no)
                    );
                    return order;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }

        return null;
    }

    //모든 주문 출
    public List<OrderVO> findAllOrders() throws SQLException{
        List<OrderVO> order_list = new ArrayList<>();
        String sql = "SELECT * FROM ORDERS";
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int order_no = resultSet.getInt("order_no");
                int cart_no = resultSet.getInt("cart_no");
                Date orderDate = resultSet.getDate("order_date");
                double totalAmount = resultSet.getDouble("total_amount");
                int totalPrice = resultSet.getInt("total_price");
                String status = resultSet.getString("status");

                List<ProductVO> products = findProductsByOrderNo(order_no);

                OrderVO orderVO = new OrderVO(order_no, cart_no, orderDate, totalAmount, totalPrice, status, products);
                order_list.add(orderVO);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return order_list;
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
