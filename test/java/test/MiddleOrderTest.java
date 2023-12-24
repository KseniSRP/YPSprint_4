package test;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import pageobject.MainPage;
import pageobject.PersonalDetailsPage;
import pageobject.RentalDetailsPage;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class MiddleOrderTest {

    private WebDriver driver;

    // Параметры для теста для первой страницы
    private final String firstName;
    private final String lastName;
    private final String address;
    private final String phoneNumber;
    private final String metroStation;

    // Параметры теста для второй страницы
    private final String rentalDuration;
    private final boolean isSelectBlack;
    private final boolean isSelectGray;
    private final String courierComment;

    // Конструктор для инициализации параметров
    public MiddleOrderTest(String firstName, String lastName, String address, String phoneNumber, String metroStation, String rentalDuration, boolean isSelectBlack, boolean isSelectGray, String courierComment) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.metroStation = metroStation;
        this.rentalDuration = rentalDuration;
        this.isSelectBlack = isSelectBlack;
        this.isSelectGray = isSelectGray;
        this.courierComment = courierComment;

    }

    // Параметризованные данные для теста
    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"Ксения", "Серяпина", "ул. Примерная, 1", "+79124444444", "Бульвар Рокоссовского", "трое суток", true, false, "тестовый коммент курьеру заказ через кнопку на странице сайта" },
                {"Алексей", "Петров-Водкин", "ул. Тестовая, 1б", "+79124444444", "Бульвар Рокоссовского", "двое суток", false, true, "тестовый коммент курьеру заказ через кнопку на странице сайта" }
        });
    }

    @Before
    public void setUp() {
        // Инициализация и настройка WebDriver
        System.setProperty("webdriver.chrome.driver", "/Users/ksenia/Desktop/YP_Projects/chromedriver");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox",  "--disable-dev-shm-usage");
        driver = new ChromeDriver(options);
        driver.get("https://qa-scooter.praktikum-services.ru/");
        System.out.println("Переход на страницу тестового приложения");
    }

    @Test
    public void testCreateOrderSuccess() {
        System.out.println("Начало теста: заказ по верхней кнопке <Заказать>");

        // Нулевой шаг - заходим на форму заказа кликнув по кнопке "Заказать" в верхней части страницы
        MainPage mainPage = new MainPage(driver);
        mainPage.clickBottomOrderButton();

        // Первый шаг
        System.out.println("Первый шаг: Заполненение адреса и личных данных для заказа");
        PersonalDetailsPage personalDetailsPage = new PersonalDetailsPage(driver);
        personalDetailsPage.fillAndSubmitForm(firstName, lastName, address, phoneNumber, metroStation);

        // Второй шаг + проверка попапа успешного оформления заказа
        System.out.println("Второй шаг: Заполненение формы сроков аренды");
        RentalDetailsPage rentalDetailsPage = new RentalDetailsPage(driver);
        rentalDetailsPage.submitRentalRequest( rentalDuration, isSelectBlack, isSelectGray, courierComment);

    }

    @After
    public void tearDown() {
        // Закрыть браузер и завершить сессию
        System.out.println("Завершение теста и закрытие браузера");
        driver.quit();



    }
}

