package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import model.User;

public class UserDB {

    public boolean addUser(User u) {
        String sql = "INSERT INTO users (name, username, password, role) VALUES (?, ?, ?, ?)";
        try (Connection con = DBConnection.getConnection()) {
            if (con == null) {
                return false;
            }
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, u.getName());
            ps.setString(2, u.getUsername());
            ps.setString(3, u.getPassword());
            ps.setString(4, u.getRole());
            ps.executeUpdate();

            ResultSet keys = ps.getGeneratedKeys();
            if (keys.next()) {
                u.setUserId(keys.getInt(1));
            }
            return true;
        } catch (Exception e) {
            System.out.println("UserDB addUser error: " + e.getMessage());
            return false;
        }
    }

    public boolean updateUser(User u) {
        String sql = "UPDATE users SET name = ?, username = ?, password = ?, role = ? WHERE userId = ?";
        try (Connection con = DBConnection.getConnection()) {
            if (con == null) {
                return false;
            }
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, u.getName());
            ps.setString(2, u.getUsername());
            ps.setString(3, u.getPassword());
            ps.setString(4, u.getRole());
            ps.setInt(5, u.getUserId());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("UserDB updateUser error: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteUser(int userId) {
        String sql = "DELETE FROM users WHERE userId = ?";
        try (Connection con = DBConnection.getConnection()) {
            if (con == null) {
                return false;
            }
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, userId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("UserDB deleteUser error: " + e.getMessage());
            return false;
        }
    }

    public User[] getAllUsers() {
        ArrayList<User> list = new ArrayList<>();
        String sql = "SELECT userId, name, username, password, role FROM users ORDER BY userId";
        try (Connection con = DBConnection.getConnection()) {
            if (con != null) {
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    list.add(new User(
                            rs.getInt("userId"),
                            rs.getString("name"),
                            rs.getString("username"),
                            rs.getString("password"),
                            rs.getString("role")));
                }
            }
        } catch (Exception e) {
            System.out.println("UserDB getAllUsers error: " + e.getMessage());
        }
        User[] result = new User[list.size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = list.get(i);
        }
        return result;
    }
}
