package pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class PersonalDetailsPage {

    private final WebDriver driver;
    private final WebDriverWait wait; // Ожидание для использования в методе

    //Локаторы
    private final By firstNameInput = By.cssSelector("input[placeholder='* Имя']");// Локатор для поля ввода имени
    private final By lastNameInput = By.cssSelector("input[placeholder='* Фамилия']");// Локатор для поля ввода фамилии
    private final By deliveryAddressInput = By.cssSelector("input[placeholder='* Адрес: куда привезти заказ']"); // Локатор для поля ввода адреса доставки
    private final By metroStationSelector = By.cssSelector("input[placeholder='* Станция метро']");// Локатор для поля выбора станции метро
    private final By phoneNumberInput = By.cssSelector("input[placeholder='* Телефон: на него позвонит курьер']");    // Локатор для поля ввода номера телефона
    private final By nextStepButton = By.cssSelector(".Button_Button__ra12g.Button_Middle__1CSJM"); // Локатор для кнопки "Далее"

    // Локаторы для метода выбора станции метро
    private final By stationOptions = By.cssSelector("li.select-search__row[role='menuitem']");
    private final By stationText = By.cssSelector("div.Order_Text__2broi");

    // Конструктор класса
    public PersonalDetailsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 5);
    }


    //Метод заполнения формы персональных данных
    public void fillAndSubmitForm(String firstName, String lastName, String address, String phoneNumber, String metroStation) {

        driver.findElement(firstNameInput).sendKeys(firstName); // Ввод имени
        driver.findElement(lastNameInput).sendKeys(lastName); // Ввод фамилии
        driver.findElement(deliveryAddressInput).sendKeys(address); // Ввод адреса
        selectMetroStation(metroStation);// Выбор станции метро
        driver.findElement(phoneNumberInput).sendKeys(phoneNumber); // Ввод номера телефона
        scrollToNextButton(); // Скроллим до кнопки, если она не видна
        driver.findElement(nextStepButton).click();// Нажатие на кнопку "Далее"
    }

    // Метод подскролла к кнопке далее если она оказалась в невидимой части
    public void scrollToNextButton(){
        WebElement elementToScrollTo = driver.findElement(nextStepButton);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", elementToScrollTo);
    }

    // Метод выбора метро из бекдропа
    public void selectMetroStation(String stationName) {
        driver.findElement(metroStationSelector).click();  // Открытие выпадающего списка станций метро
        wait.until(ExpectedConditions.visibilityOfElementLocated(stationOptions));  // Ожидание загрузки элементов списка

        //Получение списка всех станций метро и итерация по ним
        List<WebElement> stationOptionsList = driver.findElements(stationOptions);
        for (WebElement option : stationOptionsList) {
            WebElement station = option.findElement(stationText);
            if (station.getText().equals(stationName)) {
                station.click();  // Клик по станции метро
                break;  // Выход из цикла после выбора станции
            }
        }
    }


}
