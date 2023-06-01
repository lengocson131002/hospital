package com.hospital.booking.daos;

import com.hospital.booking.database.DatabaseConnection;
import com.hospital.booking.enums.TokenType;
import com.hospital.booking.models.Department;
import com.hospital.booking.models.Token;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TokenDao {
    public Token getToken(int accountId, TokenType type) {
        String sql = "select * from Token where AccountId = ? and Type = ?";
        Connection connection = null;
        try {
            connection = DatabaseConnection.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, accountId);
            statement.setString(2, type.name());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Token(
                        resultSet.getInt("Id"),
                        resultSet.getString("Token"),
                        resultSet.getTimestamp("CreatedAt").toLocalDateTime(),
                        resultSet.getTimestamp("ExpiredAt").toLocalDateTime(),
                        TokenType.valueOf(resultSet.getString("Type")),
                        resultSet.getInt("AccountId")
                );
            }
        } catch (SQLException ex) {
            Logger.getLogger(Department.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }finally {
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        return null;
    }

    public Token getToken(String token, TokenType type) {
        String sql = "select * from Token where Token = ? and Type = ?";
        Connection connection = null;
        try {
            connection = DatabaseConnection.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, token);
            statement.setString(2, type.name());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Token(
                        resultSet.getInt("Id"),
                        resultSet.getString("Token"),
                        resultSet.getTimestamp("CreatedAt").toLocalDateTime(),
                        resultSet.getTimestamp("ExpiredAt").toLocalDateTime(),
                        TokenType.valueOf(resultSet.getString("Type")),
                        resultSet.getInt("AccountId")
                );
            }
        } catch (SQLException ex) {
            Logger.getLogger(Department.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }finally {
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        return null;
    }


    public boolean setUserToken(Token token) {
        String sql = "insert into Token (Token, CreatedAt, ExpiredAt, Type, AccountId) values (?,?,?,?, ?)";
        Connection connection = null;
        try {
            connection = DatabaseConnection.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, token.getToken());
            statement.setTimestamp(2, Timestamp.valueOf(token.getCreatedAt()));
            statement.setTimestamp(3, Timestamp.valueOf(token.getExpiredAt()));
            statement.setString(4, token.getType().name());
            statement.setInt(5, token.getAccountId());

            if (statement.executeUpdate() > 0) {
                return true;
            }
        }catch (SQLException ex) {
            Logger.getLogger(Department.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        } finally {
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return false;
    }

    public boolean deleteToken(String token, TokenType type) {
        String sql = "delete from Token where Token = ? and Type = ?";
        Connection connection = null;
        try {
            connection = DatabaseConnection.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, token);
            statement.setString(2, type.name());
            if (statement.executeUpdate() > 0) {
                return true;
            }
        }catch (SQLException ex) {
            Logger.getLogger(Department.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        } finally {
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return false;
    }

}
