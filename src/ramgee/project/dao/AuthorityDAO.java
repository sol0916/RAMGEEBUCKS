package ramgee.project.dao;

import ramgee.project.db.DBProperties;
import ramgee.project.vo.AuthorityVO;
import ramgee.project.vo.AuthorityVO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AuthorityDAO {
    private String url = DBProperties.URL;
    private String uid = DBProperties.UID;
    private String upw = DBProperties.UPW;
    private Connection connection;

    public AuthorityDAO() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            connection = DriverManager.getConnection(url, uid, upw);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void addAuthority(AuthorityVO authorityVO) {
        String sql = "INSERT INTO authorities (authority_no, authority_name) VALUES (authority_no_seq.NEXTVAL, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, authorityVO.getAuthorityName());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    public void updateAuthority(AuthorityVO authorityVO) {
        String sql = "UPDATE authorities SET authority_name = ? WHERE authority_no = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, authorityVO.getAuthorityName());
            statement.setInt(2, authorityVO.getAuthority_no());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    public void deleteAuthority(int authority_no) {
        String sql = "DELETE FROM authorities WHERE authority_no = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, authority_no);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    public AuthorityVO findAuthorityByAuthorityNo(int authority_no) {
        String sql = "SELECT * FROM authorities WHERE authority_no = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, authority_no);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    AuthorityVO authorityVO = new AuthorityVO(
                            resultSet.getInt("authority_no"),
                            resultSet.getString("authority_name")
                    );
                    return authorityVO;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }

        return null;
    }

    public List<AuthorityVO> findAllAuthoritys() {
        List<AuthorityVO> authority_list = new ArrayList<>();
        String sql = "SELECT * FROM authorities";
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int authority_no = resultSet.getInt("authority_no");
                String authorityName = resultSet.getString("authority_name");

                AuthorityVO authorityVO = new AuthorityVO(authority_no, authorityName);
                authority_list.add(authorityVO);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return authority_list;
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
