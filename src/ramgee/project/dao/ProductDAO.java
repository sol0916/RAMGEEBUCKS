package ramgee.project.dao;

import ramgee.project.db.DBProperties;
import ramgee.project.vo.ProductVO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    private String url = DBProperties.URL;
    private String uid = DBProperties.UID;
    private String upw = DBProperties.UPW;
    private Connection connection;

    public ProductDAO() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            connection = DriverManager.getConnection(url, uid, upw);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void addProduct(ProductVO productVO) {
        String sql = "INSERT INTO products (product_no, name, price, description, amount) VALUES (product_no_seq.NEXTVAL, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, productVO.getName());
            statement.setDouble(2, productVO.getPrice());
            statement.setString(3, productVO.getDescription());
            statement.setInt(4, productVO.getAmount());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    public void updateProduct(ProductVO productVO) {
        String sql = "UPDATE products SET name = ?, price = ?, description = ?, amount = ? WHERE product_no = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, productVO.getName());
            statement.setDouble(2, productVO.getPrice());
            statement.setString(3, productVO.getDescription());
            statement.setInt(4, productVO.getAmount());
            statement.setInt(5, productVO.getProduct_no());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    public void deleteProduct(int product_no) {
        String sql = "DELETE from products WHERE product_no = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, product_no);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    public ProductVO findProductByProductNo(int product_no) {
        String sql = "SELECT * FROM products WHERE product_no = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, product_no);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    ProductVO product = new ProductVO(
                            resultSet.getInt("product_no"),
                            resultSet.getString("name"),
                            resultSet.getDouble("price"),
                            resultSet.getString("description"),
                            resultSet.getInt("amount")
                    );
                    return product;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }

        return null;
    }

    public List<ProductVO> findAllProducts() {
        List<ProductVO> products_list = new ArrayList<>();
        String sql = "SELECT * from products";
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                ProductVO product = new ProductVO(
                        resultSet.getInt("product_no"),
                        resultSet.getString("name"),
                        resultSet.getDouble("price"),
                        resultSet.getString("description"),
                        resultSet.getInt("amount")
                );
                products_list.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }

        return products_list;
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
