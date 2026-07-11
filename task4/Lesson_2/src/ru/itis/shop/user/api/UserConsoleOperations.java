package ru.itis.shop.user.api;

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
            case "1": {
                signUp();
            }
            break;
            case "2": {
                signIn();
            }
            break;
            case "4": {
                updateInformation();
            }
            break;
            case "5": {
                findAllUsers();
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
        System.out.println("4. Обновить данные пользователя по почте");
        System.out.println("5. Показать всех пользователей");
        System.out.println("0. Выход");
    }

    //тут была реализация через фаил в котором хранятся данные,
    //теперь же мы подключили базу данных и реализация будет через неё
    //поэтому я убрала старую реализацию, которая не нужна по дз
    private void signUp() {
        return;
    }

    private void signIn() {
        return;
    }

    private void updateInformation(){
        return;
    }

    public void findAllUsers(){
        List<User> list = userService.findAll();
        for (User user : list) {
            System.out.println(user);
        }
    }

}
