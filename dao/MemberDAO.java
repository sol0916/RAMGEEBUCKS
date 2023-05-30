package ramgee.project.dao;

import ramgee.project.db.DBProperties;
import ramgee.project.vo.AuthorityVO;
import ramgee.project.vo.MemberVO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MemberDAO {
    private String url = DBProperties.URL;
    private String uid = DBProperties.UID;
    private String upw = DBProperties.UPW;
    private Connection connection;

    public MemberDAO() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            connection = DriverManager.getConnection(url, uid, upw);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void addMember(MemberVO member) {
        String sql = "INSERT INTO members (member_no, username, password, name, email, phoneNumber, authority_no) VALUES (member_no_seq.NEXTVAL, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, member.getUsername());
            statement.setString(2, member.getPassword());
            statement.setString(3, member.getName());
            statement.setString(4, member.getEmail());
            statement.setString(5, member.getPhoneNumber());
            statement.setInt(6, member.getAuthorityVO().getAuthority_no());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void updateMember(MemberVO member) {
        String sql = "UPDATE members SET username = ?, password = ?, name = ?, email = ?, phoneNumber = ?, authority_no = ? WHERE member_no = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, member.getUsername());
            statement.setString(2, member.getPassword());
            statement.setString(3, member.getName());
            statement.setString(4, member.getEmail());
            statement.setString(5, member.getPhoneNumber());
            statement.setInt(6, member.getAuthorityVO().getAuthority_no());
            statement.setInt(7, member.getMember_no());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void deleteMember(int member_no) {
        String sql = "DELETE FROM members WHERE member_no = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, member_no);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public MemberVO findMemberByMemberNo(int member_no) {
        String sql = "SELECT * FROM members WHERE member_no = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, member_no);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    MemberVO memberVO = new MemberVO(
                            resultSet.getInt("member_no"),
                            resultSet.getString("username"),
                            resultSet.getString("password"),
                            resultSet.getString("name"),
                            resultSet.getString("email"),
                            resultSet.getString("phoneNumber"),
                            null
                    );

                    AuthorityVO authorityVO = new AuthorityVO(resultSet.getInt("authority_no"), resultSet.getString("authorityName"));
                    memberVO.setAuthorityVO(authorityVO);

                    return memberVO;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public List<MemberVO> getAllMembers() {
        List<MemberVO> member_list = new ArrayList<>();
        String sql = "SELECT * FROM member";
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                MemberVO memberVO = new MemberVO(
                        resultSet.getInt("member_no"),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getString("name"),
                        resultSet.getString("email"),
                        resultSet.getString("phoneNumber"),
                        null
                );
                AuthorityVO authorityVO = new AuthorityVO(resultSet.getInt("authority_no"), resultSet.getString("authorityName"));
                memberVO.setAuthorityVO(authorityVO);

                member_list.add(memberVO);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return member_list;
  