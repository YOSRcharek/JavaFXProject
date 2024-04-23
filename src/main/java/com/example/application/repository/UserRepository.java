    package com.example.application.repository;

    import com.example.application.DatabaseConnection;
    import com.example.application.model.User;
    import org.mindrot.jbcrypt.BCrypt;

    import java.sql.Connection;
    import java.sql.PreparedStatement;
    import java.sql.ResultSet;
    import java.sql.SQLException;
    import java.util.ArrayList;
    import java.util.List;

    public class UserRepository {
        private Connection connection;

        public UserRepository() {
            connection = DatabaseConnection.getConnection();
        }

        // Method to create a new user
        public boolean createUser(User user) {
            String sql = "INSERT INTO user (email, password, roles, is_verified) VALUES (?, ?, ?, ?)";

            try {
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, user.getEmail());
                user.setPassword(user.getPassword()); // Hash the password before storing
                preparedStatement.setString(2, user.getPassword());
                preparedStatement.setString(3, user.getRoles()); // Store roles directly as JSON string
                preparedStatement.setBoolean(4, user.isVerified());

                int rowsInserted = preparedStatement.executeUpdate();

                preparedStatement.close();

                return rowsInserted > 0;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }


    //    public boolean authenticateUser(String email, String password) {
    //        String sql = "SELECT * FROM user WHERE email = ? AND password = ?";
    //
    //        try {
    //            PreparedStatement preparedStatement = connection.prepareStatement(sql);
    //            preparedStatement.setString(1, email);
    //            preparedStatement.setString(2, password);
    //
    //            ResultSet resultSet = preparedStatement.executeQuery();
    //
    //            boolean userExists = resultSet.next();
    //
    //            preparedStatement.close();
    //            resultSet.close();
    //
    //            return userExists;
    //        } catch (SQLException e) {
    //            e.printStackTrace();
    //            return false;
    //        }
    //    }

        public User authenticateUser(String email, String password) {
            String sql = "SELECT id, email, password, roles, is_verified FROM user WHERE email = ?";

            try {
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, email);

                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String userEmail = resultSet.getString("email");
                    String hashedPassword = resultSet.getString("password");
                    String roles = resultSet.getString("roles");
                    boolean isVerified = resultSet.getBoolean("is_verified");

                    if (BCrypt.checkpw(password, hashedPassword)) {
                        User authenticatedUser = new User(userEmail, hashedPassword, roles, isVerified);
                        authenticatedUser.setId(id);
                        return authenticatedUser;
                    }
                }

                preparedStatement.close();
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return null;
        }



        // Method to update an existing user
        public boolean updateUser(User user) {
            String sql = "UPDATE user SET email=?, password=?, roles=?, is_verified=? WHERE id=?";

            try {
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, user.getEmail());
                user.setPassword(user.getPassword()); // Hash the password before storing
                preparedStatement.setString(2, user.getPassword());

                preparedStatement.setString(3, user.getRoles()); // Store roles directly as JSON string
                preparedStatement.setBoolean(4, user.isVerified());
                preparedStatement.setInt(5, user.getId());

                int rowsUpdated = preparedStatement.executeUpdate();

                preparedStatement.close();

                return rowsUpdated > 0;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }

        // Method to delete an existing user by ID
        public boolean deleteUser(int userId) {
            String sql = "DELETE FROM user WHERE id=?";

            try {
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, userId);

                int rowsDeleted = preparedStatement.executeUpdate();

                preparedStatement.close();

                return rowsDeleted > 0;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }

        // Method to retrieve a user by ID
        public User getUserById(int userId) {
            String sql = "SELECT * FROM user WHERE id=?";

            try {
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, userId);

                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    User user = new User();
                    user.setId(resultSet.getInt("id"));
                    user.setEmail(resultSet.getString("email"));
                    user.setPassword(resultSet.getString("password"));
                    user.setRoles(resultSet.getString("roles")); // Retrieve roles directly as JSON string
                    user.setVerified(resultSet.getBoolean("is_verified"));

                    preparedStatement.close();
                    resultSet.close();

                    return user;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return null;
        }

        // Method to retrieve all users
        public List<User> getAllUsers() {
            List<User> users = new ArrayList<>();
            String sql = "SELECT * FROM user";

            try {
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    User user = new User();
                    user.setId(resultSet.getInt("id"));
                    user.setEmail(resultSet.getString("email"));
                    user.setPassword(resultSet.getString("password"));
                    user.setRoles(resultSet.getString("roles")); // Retrieve roles directly as JSON string
                    user.setVerified(resultSet.getBoolean("is_verified"));

                    users.add(user);
                }

                preparedStatement.close();
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return users;
        }
    }
