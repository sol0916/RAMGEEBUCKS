package ramgee.project.dao;

import ramgee.project.db.DBProperties;
import ramgee.project.vo.OrderVO;
import ramgee.project.vo.PayVO;
import ramgee.project.vo.ProductVO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PayDAO {
   private String url = DBProperties.URL;
   private String uid = DBProperties.UID;
   private String upw = DBProperties.UPW;
   private Connection connection;

   public PayDAO() {
      try {
         Class.forName("oracle.jdbc.driver.OracleDriver");
         connection = DriverManager.getConnection(url, uid, upw);
      } catch (ClassNotFoundException | SQLException e) {
         e.printStackTrace();
      }
   }

   public void addPay(PayVO payVO) {
      String sql = "INSERT INTO pays (pay_no, order_no, amount_paid, payment_date) VALUES (pay_no_seq.NEXTVAL, ?, ?, ?)";
      try (PreparedStatement statement = connection.prepareStatement(sql)) {
         statement.setInt(1, payVO.getOrder_no());
         statement.setDouble(2, payVO.getAmountPaid());
         statement.setDate(3, payVO.getPaymentDate());

         statement.executeUpdate();
      } catch (SQLException e) {
         e.printStackTrace();
      } finally {
         closeConnection();
      }
   }

   public void updatePay(PayVO payVO) {
      String sql = "UPDATE pays SET order_no = ?, amount_paid = ?, payment_date = ? WHERE pay_no = ?";
      try (PreparedStatement statement = connection.prepareStatement(sql)) {
         statement.setInt(1, payVO.getOrder_no());
         statement.setDouble(2, payVO.getAmountPaid());
         statement.setDate(3, payVO.getPaymentDate());
         statement.setInt(4, payVO.getPay_no());

         statement.executeUpdate();
      } catch (SQLException e) {
         e.printStackTrace();
      } finally {
         closeConnection();
      }
   }

   public void deletePay(int pay_no) {
      String sql = "DELETE FROM pays WHERE pay_no = ?";
      try (PreparedStatement statement = connection.prepareStatement(sql)) {
         statement.setInt(1, pay_no);

         statement.executeUpdate();
      } catch (SQLException e) {
         e.printStackTrace();
      } finally {
         closeConnection();
      }
   }

   public PayVO findPayByPayNo(int pay_no) {
      String sql = "SELECT * FROM pays WHERE pay_no = ?";
      try (PreparedStatement statement = connection.prepareStatement(sql)) {
         statement.setInt(1, pay_no);

         try (ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
               PayVO payVO = new PayVO(
                     resultSet.getInt("pay_no"),
                     resultSet.getInt("order_no"),
                     resultSet.getDouble("amount_paid"),
                     resultSet.getDate("payment_date")
                     );
               return payVO;
            }
         }
      } catch (SQLException e) {
         e.printStackTrace();
      } finally {
         closeConnection();
      }

      return null;
   }

   public List<PayVO> findAllPays() {
      List<PayVO> pay_list = new ArrayList<>();
      String sql = "SELECT * FROM pays";
      try (PreparedStatement pstmt = connection.prepareStatement(sql);
            ResultSet resultSet = pstmt.executeQuery()) {

         while (resultSet.next()) {
            int pay_no = resultSet.getInt("pay_no");
            int order_no = resultSet.getInt("order_no");
            double amountPaid = resultSet.getDouble("amount_paid");
            Date paymentDate = resultSet.getDate("payment_date");

            PayVO payVO = new PayVO(pay_no, order_no, amountPaid, paymentDate);
            pay_list.add(payVO);
         }
      } catch (SQLException e) {
         e.printStackTrace();
      } finally {
         closeConnection();
      }
      return pay_list;
   }




   ////////////
   public PayVO findPayByOrder_no(int Total_price) {
      String sql = "SELECT Total_price FROM Orders o JOIN pays p ON  o.order_No = p.order_No";
      try (PreparedStatement statement = connection.prepareStatement(sql)) {
         statement.setInt(1, Total_price);

         try (ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
               PayVO payVO = new PayVO(
                     resultSet.getInt("o.Total_price"),
                     resultSet.getInt("order_no"),
                     resultSet.getDouble("amount_paid"),
                     resultSet.getDate("payment_date")
                     );
               return payVO;
            }
         }
      } catch (SQLException e) {
         e.printStackTrace();
      } finally {
         closeConnection();
      }

      return null;
   }

   ////////////


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