package com.ubtechinc.remote.adjustdata;
import com.ubtechinc.remote.utils.JdbcUtils;
import com.ubtechinc.remote.model.datamodel.User;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;
import java.util.List;
public class AdjustData {

    // 添加用户
    public int addUser(User user) {
        String sql = "INSERT INTO user_info(user_name, password, mobile) VALUES (?, ?, ?)";
        //调用 JdbcUtils.getConnection() 获取数据库连接对象
        //Connection 对象表示的是你和数据库之间的一条 物理连接通道,通过这条连接，你可以发送 SQL 指令给数据库执行。
        try (Connection connection = JdbcUtils.getConnection();
             //PreparedStatement 是 Connection 提供的一个方法
             //预编译 SQL：数据库会先把 SQL 模板编译好，后续只需要填参数，不用重复解析。
             //简化代码：用 ps.setString()、ps.setInt() 等方法直接绑定参数，不用自己拼字符串。
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, user.getUserName());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getMobile());
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // 删除用户mobile
    public int deleteUserMobile(int mobile) {
        String sql = "DELETE FROM user_info WHERE mobile=?";
        try (Connection conn = JdbcUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, mobile);
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // 更新用户
    public int updateUser(User user) {
        String sql = "UPDATE user_info SET password=?, mobile=? WHERE user_name=?";
        try (Connection conn = JdbcUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, user.getPassword());
            ps.setString(2, user.getMobile());
            ps.setString(3, user.getUserName());
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // 查询所有用户
    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        String sql = "SELECT user_name, password, mobile FROM user_info";
        try (Connection conn = JdbcUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             //这里是什么意思，开一个query？
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                User u = new User();
                u.setUserName(rs.getString("user_name"));
                u.setPassword(rs.getString("password"));
                u.setMobile(rs.getString("mobile"));
                list.add(u);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }



}
