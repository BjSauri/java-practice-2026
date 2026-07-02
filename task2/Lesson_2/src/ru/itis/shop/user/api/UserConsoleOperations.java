package ru.itis.shop.user.api;

import ru.itis.shop.user.application.UserService;
import ru.itis.shop.user.domain.User;

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
            case "1": {
                signUp();
            }
            break;
            case "2": {
                signIn();
            }
            break;
            case "4":{
                updateInformation();
            }
            case "0": {
                System.exit(0);
            }
        }
    }

    private static void printUserMenu() {
        System.out.println("1. Регистрация пользователя");
        System.out.println("2. Вход в систему");
        System.out.println("3. Найти пользователя по id");
        System.out.println("4. Обновить данные пользователя по почте");
        System.out.println("0. Выход");
    }

    private void signUp() {
        System.out.println("Сейчас будем регистрировать пользователя");
        System.out.println("Введите email:");
        String email = scanner.nextLine();
        System.out.println("Введите password:");
        String password = scanner.nextLine();
        System.out.println("Введите описание профиля:");
        String profileDescription = scanner.nextLine();

        userService.signUp(email, password, profileDescription);
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

    private void updateInformation(){
        System.out.println("Введите почту пользователя, которого хотите обновть");
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

        System.out.println("\nВведите новый пароль (или оставьте пустым, чтобы не менять):");
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

}
