package ru.itis.shop.user.infrastructure.persistence;

import ru.itis.shop.user.domain.User;
import ru.itis.shop.user.repository.UserRepository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserFileRepository implements UserRepository {

    private final String fileName;

    private final UserMapper userMapper;

    public UserFileRepository(String fileName, UserMapper userMapper) {
        this.fileName = fileName;
        this.userMapper = userMapper;
    }

    @Override
    public void save(User user) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            String id = UUID.randomUUID().toString();
            user.setId(id);
            writer.write(userMapper.toLine(user));
            writer.newLine();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {

            String line = reader.readLine();

            while (line != null) {

                User user = userMapper.fromLine(line);

                if (user.getEmail().equals(email)) {
                    return Optional.of(user);
                }

                line = reader.readLine();
            }

            return Optional.empty();

        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Optional<User> findById(String id) {
        return Optional.empty();
    }


    @Override
    public boolean updateByEmail(String email, User updatedUser) {
        try {
            List<User> users = new ArrayList<>();
            try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
                String line = reader.readLine();
                while (line != null) {
                    User user = userMapper.fromLine(line);
                    users.add(user);
                    line = reader.readLine();
                }
            }

            boolean found = false;
            for (int i = 0; i < users.size(); i++) {
                User user = users.get(i);
                if (user.getEmail().equals(email)) {
                    String oldId = user.getId();
                    updatedUser.setId(oldId);
                    if (updatedUser.getEmail() == null || updatedUser.getEmail().isEmpty()) {
                        updatedUser.setEmail(user.getEmail());
                    }
                    users.set(i, updatedUser);
                    found = true;
                    break;
                }
            }

            if (!found) {
                return false;
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
                for (User user : users) {
                    writer.write(userMapper.toLine(user));
                    writer.newLine();
                }
            }

            return true;

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
