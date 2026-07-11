package ru.itis.shop.user.repository;

import ru.itis.shop.user.domain.User;
import java.util.List;

public interface UserRepositoryJdbcImpl {
    List<User> findAll();

}
