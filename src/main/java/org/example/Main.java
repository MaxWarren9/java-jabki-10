package org.example;

import org.example.customException.InsufficientBalanceException;
import org.example.customException.InvalidRatingException;
import org.example.customException.InvalidTransferAmountException;
import org.example.customException.ItemNotFoundException;
import org.example.customException.LoginFailedException;
import org.example.customException.NegativeDepositException;
import org.example.deposit.Account;
import org.example.item.Item;
import org.example.item.ItemStore;
import org.example.login.Login;
import org.example.person.Person;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.example.deposit.Transfer.transfer;

public class Main {
    public static void main(String[] args) {
        // 1.
        safeDivide(10, 3);
        safeDivide(10, 0);
        // 2.
        try {
            isEmptyString(" a");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }

        try {
            isEmptyString("      ");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }

        // 3.
        List<String> list = List.of("10", "abc", "5");
        System.out.println(transformStringsToNumbers(list));

        // 4. Метод setAge(int age) должен выбрасывать IllegalArgumentException, если возраст меньше нуля.
        Person p = new Person("Crab", 1);
        try {
            p.setAge(-3);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        // 5. Создайте исключение NegativeDepositException, и метод deposit(double amount),который выбрасывает
        // это исключение при отрицательном значении. Обработайте его в main.
        Account account = new Account("1A", 100);
        Account account2 = new Account("2B", 120);
        System.out.println(account);
        try {
            account.deposit(100);
            System.out.println(account.getBalance());
            account.deposit(-300);
            System.out.println(account.getBalance());
        } catch (NegativeDepositException e) {
            System.out.println(e.getMessage());
        } finally {
            System.out.println(account.getBalance());
        }

        // 6. Реализуйте метод getItem(String code). Если код не найден в карте товаров, выбросите ItemNotFoundException,
        // унаследованное от RuntimeException. Продемонстрируйте поведение в main.
        ItemStore store = new ItemStore();
        store.addItem("338", new Item("Манная каша"));
        store.addItem("001", new Item("Маринованные огурцы"));

        try {
            System.out.println(store.getItem("001").toString());
            store.getItem("009");
        } catch (ItemNotFoundException e) {
            System.out.println(e.getMessage());
        }

        // 7.
        String path = "/Users/maximvoinov/Downloads/java-jabki-10/src/main/resources/file.txt";
        System.out.println(readFile(path));
        System.out.println(readFile("txt"));

        // 8.Создайте метод login(String username, String password), в котором логин и пароль проверяются на корректность.
        // Если один из них не совпадает — выбрасывается LoginFailedException. Исключение должно наследоваться от Exception.
        Login log = new Login();
        log.registerUser("Maxim", "1234");
        log.registerUser("Andrey", "1A2B3C");
        log.registerUser("Luke", "C3PO");

        try {
            log.login("Kleo", "1");
        } catch (LoginFailedException e) {
            System.out.println(e.getMessage());
        }

        // 9.  Метод transfer(fromAccount, toAccount, amount): выбрасывает InvalidTransferAmountException, если сумма <= 0,
        // выбрасывает InsufficientBalanceException, если баланс отправителя меньше суммы, содержит try-catch в main
        System.out.println(account.getBalance());
        System.out.println(account2.getBalance());
        try {
            transfer(account, account2, -1);
        } catch (InvalidTransferAmountException e) {
            System.out.println(e.getMessage());
        } catch (InsufficientBalanceException e) {
            System.out.println(e.getMessage());
        } finally {
            System.out.println(account.getBalance());
            System.out.println(account2.getBalance());
        }

        // 10. Сервис оценки товара
        Item pickles = store.getItem("001");
        try {
            pickles.rateProduct("6");
        } catch (InvalidRatingException e) {
            System.out.println(e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
        }

        try {
            pickles.rateProduct("AB");
        } catch (InvalidRatingException e) {
            System.out.println(e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
        }

    }

    // 1. Метод безопасного деления, который возвращает a / b. Если b == 0, перехватите исключение и выведите сообщение: "Деление на ноль запрещено".
    public static void safeDivide(int a, int b) {
        try {
            int i = a / b;
            System.out.println(i);
        } catch (ArithmeticException e) {
            System.out.println("Деление на ноль запрещено");
        }
    }

    // 2. Проверка строки. Напишите метод, который принимает строку и выбрасывает IllegalArgumentException, если строка пуста или состоит только из пробелов.
    public static void isEmptyString(String s) throws IllegalArgumentException {
        if (s == null || s.isBlank()) {
            throw new IllegalArgumentException("Строка пустая или состоит только из пробелов.\n");
        } else {
            System.out.println("Все ок!");
        }
    }

    // 3. Дан список строк List.of("10", "abc", "5"). Преобразуйте его в список чисел, перехватывая NumberFormatException. Ошибки не должны останавливать выполнение.
    public static List<Integer> transformStringsToNumbers(List<String> list) {
        List<Integer> numbersList = new ArrayList<>();
        for (String i : list) {
            try {
                numbersList.add(Integer.parseInt(i));
                System.out.printf("Строка %s преобразована в число!%n", i);
            } catch (NumberFormatException e) {
                System.out.printf("Строка %s не преобразуема в число!%n", i);
            }
        }
        return numbersList;
    }


    // 7. Реализуйте метод readFile(String path), который читает текстовый файл и возвращает список строк.
    // Используйте BufferedReader, перехватите IOException, выведите сообщение об ошибке.
    public static List<String> readFile(String path) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            lines = br.lines().toList();
        } catch (IOException e) {
            System.out.println("файл не найден: " + e.getMessage());
        }
        return lines;
    }

}