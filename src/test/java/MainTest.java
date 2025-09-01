import org.example.Main;
import org.example.customException.InvalidRatingException;
import org.example.customException.ItemNotFoundException;
import org.example.customException.LoginFailedException;
import org.example.customException.NegativeDepositException;
import org.example.deposit.Account;
import org.example.item.Item;
import org.example.item.ItemStore;
import org.example.login.Login;
import org.example.person.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MainTest {

    private final ByteArrayOutputStream out = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(out));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    public void safeDivideTest() {
        Main.safeDivide(10, 2);
        assertEquals("5\n", out.toString());

        Main.safeDivide(10, 0);
        assertEquals("5\nДеление на ноль запрещено\n", out.toString());
    }

    @Test
    void isEmptyStringTest_OkCase() {
        Main.isEmptyString("Привет");
        assertEquals("Все ок!\n", out.toString());
    }

    @Test
    void isEmptyStringTest_EmptyCase() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> Main.isEmptyString("")
        );
        assertEquals("Строка пустая или состоит только из пробелов.\n", exception.getMessage());
    }

    @Test
    void isEmptyStringTest_SpaceCase() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> Main.isEmptyString("   ")
        );
        assertEquals("Строка пустая или состоит только из пробелов.\n", exception.getMessage());
    }

    @Test
    void isEmptyStringTest_OkCase2() {
        Main.isEmptyString(" a ");
        assertEquals("Все ок!\n", out.toString());
    }

    @Test
    void transformStringsToNumbersPassingTest() {
        List<String> initialList = Arrays.asList("10", "20");
        List<Integer> resultingList = Main.transformStringsToNumbers(initialList);
        assertEquals("Строка 10 преобразована в число!\nСтрока 20 преобразована в число!\n", out.toString());
        assertEquals(2, resultingList.size());
        assertEquals(10, resultingList.get(0));
    }

    @Test
    void transformStringsToNumbersFailingTest() {
        List<String> initialList = Arrays.asList("10", "a");
        List<Integer> resultingList = Main.transformStringsToNumbers(initialList);
        assertEquals("Строка 10 преобразована в число!\nСтрока a не преобразуема в число!\n", out.toString());
        assertEquals(10, resultingList.get(0));
        assertEquals(1, resultingList.size());
    }

    @Test
    void readFileTest() {
        List<String> file =  Main.readFile("src/main/resources/file.txt");
        List<String> file2 = Main.readFile("file2.txt");
        assertEquals("файл не найден: file2.txt (No such file or directory)\n", out.toString());
        assertEquals(3, file.size());
        assertTrue(file.contains("Rock that body,"));
    }

    @Test
    void depositTest() throws NegativeDepositException{
        Account account = new Account("1", 0);
        account.deposit(100);
        assertEquals(100, account.getBalance());
        NegativeDepositException exception = assertThrows(
                NegativeDepositException.class,
                () -> account.deposit(-10)
        );
        assertEquals("Введено отрицательное число", exception.getMessage());
        assertEquals(100, account.getBalance());
    }

    @Test
    void rateProductTest() throws InvalidRatingException, NumberFormatException {
        Item item = new Item("вода");
        item.rateProduct("5");

        assertEquals(1, item.getItems().size());
        assertEquals(5, item.getItems().get(0));

        Item item2 = new Item("Еда");

        InvalidRatingException ire = assertThrows(InvalidRatingException.class, () -> item2.rateProduct("7"));
        assertEquals("Рейтинг должен быть в диапазоне от 1 до 5", ire.getMessage());
        assertTrue(item2.getItems().isEmpty());

        item2.rateProduct("abc");
        assertTrue(item2.getItems().isEmpty());
        assertTrue(out.toString().contains("Введенное значение не является числом"));
    }

    @Test
    void getItemTest() throws ItemNotFoundException {
        ItemStore store = new ItemStore();
        Item item = new Item("Вода");
        store.addItem("001", item);

        assertEquals(1, store.getItems().size());
       assertTrue(store.getItems().containsKey("001"));
       assertEquals("ItemName = Вода", store.getItem("001").toString());

        ItemNotFoundException exception = assertThrows(
                ItemNotFoundException.class,
                () -> store.getItem("unknown")
        );
        assertEquals("Товара с данным кодом нет в списке", exception.getMessage());
    }

    @Test
    void loginTest() throws LoginFailedException {
        Login logs = new Login();
        logs.registerUser("Q", "1");
        logs.registerUser("P", "2");

        assertEquals(2, logs.getUsers().size());
        assertTrue(logs.getUsers().containsKey("Q"));
        assertTrue(logs.getUsers().containsKey("P"));


        logs.login("P", "2");
        assertEquals("Вы вошли\n", out.toString());

        LoginFailedException exception = assertThrows(
                LoginFailedException.class,
                () -> logs.login("Q", "wrongPass")
        );
        assertEquals("Пароль для пользователя введен неверно", exception.getMessage());

        LoginFailedException ex = assertThrows(LoginFailedException.class,() -> logs.login("user", "100"));
        assertEquals("Данного пользователя не существует", ex.getMessage());
    }

    @Test
    void setAgeTest() {
        Person p = new Person("Max", 3);
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> p.setAge(-3));
        assertEquals("Возраст не может быть меньше 0.", ex.getMessage());
        assertEquals(3, p.getAge());
    }
}
