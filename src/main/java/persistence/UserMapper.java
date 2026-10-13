package persistence;

import entities.User;

import java.sql.*;

//UserMapper står for al SQL der har med brugere at gøre (Data Mapper pattern)
public class UserMapper {

    private ConnectionPool connectionPool;

    //ConnectionPool bliver givet med udefra (Dependency Injection)
    public UserMapper(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    //Finder en bruger ud fra brugernavn og password. Returnerer null hvis der ikke er et match.
    public User login(String username, String password) throws DatabaseException {
        String sql = "SELECT user_id, username, email, password FROM users WHERE username = ? AND password = ?";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new User(rs.getInt("user_id"), rs.getString("username"),
                        rs.getString("email"), rs.getString("password"));
            }
            return null;
        } catch (SQLException e) {
            throw new DatabaseException("Kunne ikke logge ind", e);
        }
    }
}