package pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class PersonalDetailsPage {

    private WebDriver driver;
    private WebDriverWait wait; // Ожидание для использования в методе

    //Локаторы
    private By firstNameInput = By.cssSelector("input[placeholder='* Имя']");// Локатор для поля ввода имени
    private By lastNameInput = By.cssSelector("input[placeholder='* Фамилия']");// Локатор для поля ввода фамилии
    private By deliveryAddressInput = By.cssSelector("input[placeholder='* Адрес: куда привезти заказ']"); // Локатор для поля ввода адреса доставки
    private By metroStationSelector = By.cssSelector("input[placeholder='* Станция метро']");// Локатор для поля выбора станции метро
    private By phoneNumberInput = By.cssSelector("input[placeholder='* Телефон: на него позвонит курьер']");    // Локатор для поля ввода номера телефона
    private By nextStepButton = By.cssSelector(".Button_Button__ra12g.Button_Middle__1CSJM"); // Локатор для кнопки "Далее"

    // Локаторы для метода выбора станции метро
    private By stationOptions = By.cssSelector("li.select-search__row[role='menuitem']");
    private By stationText = By.cssSelector("div.Order_Text__2broi");

    // Конструктор класса
    public PersonalDetailsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 5);
    }


    //Метод заполнения формы персональных данных
    public void fillAndSubmitForm(String firstName, String lastName, String address, String phoneNumber, String metroStation) {

        driver.findElement(firstNameInput).sendKeys(firstName);
        System.out.println("Имя заполнено: " + firstName);

        driver.findElement(lastNameInput).sendKeys(lastName);
        System.out.println("Фамилия заполнена: " + lastNameInput);

        driver.findElement(deliveryAddressInput).sendKeys(address);
        System.out.println("Адрес заполнен: " + deliveryAddressInput);

        selectMetroStation(metroStation);// Выбор станции метро

        driver.findElement(phoneNumberInput).sendKeys(phoneNumber);
        System.out.println("Номер телефона заполнен: " + phoneNumber);


        scrollToNextButton(); // Скроллим до кнопки, если она не видна
        // Нажатие на кнопку "Далее"

        driver.findElement(nextStepButton).click();
        System.out.println("Кнопка 'Далее' нажата");
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
