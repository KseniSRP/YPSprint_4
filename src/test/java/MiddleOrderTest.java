import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import pageobject.MainPage;
import pageobject.PersonalDetailsPage;
import pageobject.RentalDetailsPage;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class MiddleOrderTest extends BaseTest {
    // Параметры для заполнения первой страницы
    private final String firstName;
    private final String lastName;
    private final String address;
    private final String phoneNumber;
    private final String metroStation;

    // Параметры для заполнения воторой страницы
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
                {"Иван", "Иванов", "ул. Примерная, 1", "+79124444444", "Бульвар Рокоссовского", "двое суток", true, false, "черный самокат" },
                {"Иван", "Иванов", "ул. Примерная, 1", "+79124444444", "Бульвар Рокоссовского", "трое суток", false, true, "серый самокат" }
        });
    }

    @Test
    public void testCreateOrderSuccess() {
        /// Нулевой шаг - заходим на форму заказа кликнув по кнопке "Заказать" на странице
        MainPage mainPage = new MainPage(driver);
        mainPage.clickMiddleOrderButton();

        // Первый шаг
        PersonalDetailsPage personalDetailsPage = new PersonalDetailsPage(driver);
        personalDetailsPage.fillAndSubmitForm(firstName, lastName, address, phoneNumber, metroStation);

        // Второй шаг заполнения деталей аренды и подтверждение заказа
        RentalDetailsPage rentalDetailsPage = new RentalDetailsPage(driver);
        rentalDetailsPage.completeOrder(rentalDuration, isSelectBlack, isSelectGray, courierComment);
    }
}