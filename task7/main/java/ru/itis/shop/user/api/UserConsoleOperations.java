package ru.itis.shop.user.api;

import ru.itis.shop.user.api.dto.UserDto;
import ru.itis.shop.user.application.UserService;
import ru.itis.shop.user.domain.User;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class UserConsoleOperations {

    private final UserService userService;
    private final Scanner scanner;

    public UserConsoleOperations(UserService userService) {
        this.userService = userService;
        this.scanner = new Scanner(System.in);
    }

    public void showMenu() {
        printUserMenu();

        String command = scanner.nextLine();

        switch (command) {
            case "1": {signUp();}
            break;
            case "2": {signIn();}
            break;
            case "3": {findUserById();}
            break;
            case "4": {updateInformation();}
            break;
            case "5": {findAllUsers();}
            break;
            case "6": {
                System.out.println("Введите описание профиля:");
                String profileDescription = scanner.nextLine();
                if (profileDescription.trim().isEmpty()) {
                    System.out.println("Ищем пользователей только с заданным описание пользователя");
                } else {
                    System.out.println(userService.findAllByProfileDescription(profileDescription));
                }
            }
            break;
            case "0": {
                System.exit(0);
            }
        }
    }

    private static void printUserMenu() {
        System.out.println("1. Регистрация пользователя");
        System.out.println("2. Вход в систему");
        System.out.println("3. Найти пользователя по id");
        System.out.println("4. Обновить описание пользователя по почте");
        System.out.println("5. Получить информацию обо всех пользователях");
        System.out.println("6. Показать информацию о пользователях с заданным описанием профиля");
        System.out.println("0. Выход");
    }

    private void signUp() {
        System.out.println("Сейчас будем регистрировать пользователя");
        System.out.println("Введите name:");
        String name = scanner.nextLine();
        System.out.println("Введите email:");
        String email = scanner.nextLine();
        System.out.println("Введите password:");
        String password = scanner.nextLine();
        System.out.println("Введите описание профиля:");
        String profileDescription = scanner.nextLine();

        userService.signUp(name, email, password, profileDescription);
        System.out.println("Пользователь зарегистрирован");
    }

    private void signIn() {
        System.out.println("Вы можете войти в приложение");
        System.out.println("Введите email:");
        String email = scanner.nextLine();
        System.out.println("Введите password:");
        String password = scanner.nextLine();

        if (userService.signIn(email, password)) {
            System.out.println("Вы вошли в приложение");
        } else {
            System.out.println("Email или пароль не верны");
        }
    }

    private void findUserById() {
        System.out.println("Введите id пользователя:");
        Integer id = Integer.parseInt(scanner.nextLine());

        Optional<UserDto> userOptional = userService.findById(id);
        if (userOptional.isPresent()) {
            System.out.println(userOptional.get());
        } else {
            System.out.println("Пользователь не найден");
        }
    }

    private void updateInformation() {
        System.out.println("Введите почту пользователя, которого хотите обновить:");
        String email = scanner.nextLine();

        Optional<User> userOptional = userService.findByEmail(email);

        if (userOptional.isEmpty()) {
            System.out.println("Пользователь с такой почтой не найден!");
            return;
        }

        User existingUser = userOptional.get();
        System.out.println("Найден пользователь:");
        System.out.println("Email: " + existingUser.getEmail());
        System.out.println("Описание профиля: " + existingUser.getProfileDescription());

        System.out.println("Введите новый пароль (или оставьте пустым, чтобы не менять):");
        String newPassword = scanner.nextLine();
        System.out.println("Введите новое описание профиля (или оставьте пустым, чтобы не менять):");
        String newProfileDescription = scanner.nextLine();

        boolean updated = userService.updateUserByEmail(email, newPassword, newProfileDescription);

        if (updated) {
            System.out.println("Данные пользователя успешно обновлены!");
        } else {
            System.out.println("Не удалось обновить данные пользователя.");
        }
    }

    private void findAllUsers() {
        List<User> list = userService.findAll();
        for (User user : list) {
            System.out.println(user);
        }
    }

    private void findByProfileDescription() {
        System.out.println("Введите описание профиля:");
        String profileDescription = scanner.nextLine();
        List<User> users = userService.findAllByProfileDescription(profileDescription);

        if (users.isEmpty()) {
            System.out.println("Пользователи с таким описанием не найдены");
            return;
        }

        for (User user : users) {
            System.out.println(user);
        }
    }



}
