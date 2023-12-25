
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.safari.SafariDriver;


// Класс с вынесеными методами с аннотациями @Before и @After
public class BaseTest {
    protected WebDriver driver;

    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", getClass().getClassLoader().getResource("chromedriver").getPath());
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox", "--disable-dev-shm-usage");
        driver = new ChromeDriver(options);
        driver.get("https://qa-scooter.praktikum-services.ru/");
        System.out.println("Переход на страницу тестового приложения");
    }

    /* Для сафари браузера
    public void setUp() {
        driver = new SafariDriver();
        driver.manage().window().maximize();
        driver.get("https://qa-scooter.praktikum-services.ru/");
        System.out.println("Переход на страницу тестового приложения");
    }

     */

    @After
    public void tearDown() {
        System.out.println("Завершение теста и закрытие браузера");
            driver.quit();

    }
}
