package ru.itis.shop.user.infrastructure.persistence;

import ru.itis.shop.user.domain.User;
import ru.itis.shop.user.repository.UserRepository;

public class UserDatabaseRepository implements UserRepository {
    @Override
    public void save(User user) {
    }

    @Override
    public User findById(String id) {
        System.out.println("Поиск пользователя по id...");
        return null;
    }
}
