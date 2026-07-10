package ru.itis.shop.user.infrastructure.persistence;

import ru.itis.shop.user.domain.User;
import ru.itis.shop.user.repository.UserRepository;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserFileRepository implements UserRepository {

    private final String fileName;
    private final UserMapper userMapper;

    //это нужно для реализации метода findAll через базу даннх
    private static final String DB_URL = "jdbc:postgresql://localhost:5433/java_2026";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "123";

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        try {
            Class.forName("org.postgresql.Driver");
            try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                 Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery("select * from accounts")) {

                while (resultSet.next()) {
                    User user = new User(
                            resultSet.getString("id"),
                            resultSet.getString("email"),
                            resultSet.getString("password"),
                            resultSet.getString("profileDescription")
                    );
                    users.add(user);
                }
            }

        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Драйвер не найден!", e);
        } catch (SQLException e) {
            throw new IllegalStateException("Ошибка в методе findAll", e);
        }

        return users;
    }

    public UserFileRepository(String fileName, UserMapper userMapper) {
        this.fileName = fileName;
        this.userMapper = userMapper;
    }

    @Override
    public void save(User user) {
        return;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return Optional.empty();
    }

    @Override
    public Optional<User> findById(String id) {
        return Optional.empty();
    }

    public boolean updateUserByEmail(String email, String newPassword, String newProfileDescription){
        return false;
    }

    public boolean updateByEmail(String email, User updatedUser) {
        return false;
    }

}
