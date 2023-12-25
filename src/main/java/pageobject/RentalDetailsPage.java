
package pageobject;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.Assert;


public class RentalDetailsPage {
    private final WebDriver driver;
    private final WebDriverWait wait; // Ожидание для использования в методе

    // Локаторы
    //Локатор для поля ввода даты доставки самоката, открывает календарь
    private final By deliveryDateField = By.cssSelector("input[placeholder='* Когда привезти самокат']");

    // Локатор для поля выбора срока аренды (выпадающий список)
    private final By rentalDurationField = By.className("Dropdown-control");

    // Локатор для выбора опции срока аренды из выпадающего списка
    private final By durationOptionLocator = By.xpath("//div[contains(@class, 'Dropdown-menu')]");

    // Локатор для чекбокса выбора черного цвета самоката
    private final By blackScooterCheckbox = By.xpath("//input[@id='black']");

    // Локатор для чекбокса выбора серого цвета самоката
    private final By grayScooterCheckbox = By.xpath("//input[@id='grey']");

    // Локатор для поля ввода комментария для курьера
    private final By courierCommentField = By.cssSelector("input.Input_Input__1iN_Z.Input_Responsible__1jDKN[placeholder='Комментарий для курьера']");

    // Локатор для кнопки <Заказать> для отправки формы заказа
    private final By orderButton = By.xpath("//button[@class='Button_Button__ra12g Button_Middle__1CSJM']");

    //Локатор модального окна подтверждения заказа
    private final By orderConfirmationModal = By.cssSelector("div.Order_Modal__YZ-d3");

    //Локатор кнопки подтверждения заказа в модальном окне
    private final By confirmOrderButton = By.xpath("//button[contains(@class, 'Button_Button__ra12g') and contains(@class, 'Button_Middle__1CSJM') and text()='Да']");

    // Локатор модального окна успешного заказа
    private final By orderConfirmationPopup = By.cssSelector(".Order_Modal__YZ-d3");
    // Локатор кнопки "Посмотреть статус" в попапе успеха оформления ордера
    private final By nextButtonInOrderConfirmationPopup = By.cssSelector(".Order_NextButton__1_rCA > button:nth-child(1)");

    // Конструктор класса
    public RentalDetailsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 5);
    }

    // Метод выбора даты
    public void enterDeliveryDate() {
        String formattedDate = LocalDate.now().plusDays(1).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        WebElement dateField = driver.findElement(deliveryDateField);
        dateField.sendKeys(formattedDate + Keys.ENTER);
    }

    // Метод выбора срока аренды из выпадающего списка
    public void selectRentalDuration(String rentalDuration) {
        WebElement durationDropdown = driver.findElement(rentalDurationField);
        durationDropdown.click();
        WebElement durationOption = driver.findElement(durationOptionLocator); // Этот локатор должен быть доработан для выбора конкретного срока аренды
        durationOption.click();
    }

    // Метод выбора цвета самоката
    public void selectColor(boolean isSelectBlack, boolean isSelectGray) {
        if (isSelectBlack) {
            WebElement blackCheckbox = driver.findElement(blackScooterCheckbox);
            blackCheckbox.click(); // выбор черного самоката
        }
        if (isSelectGray) {
            WebElement grayCheckbox = driver.findElement(grayScooterCheckbox);
            grayCheckbox.click(); // выбор серого самоката
        }
    }

    //Метод заполнения комментария для курьера
    public void enterCourierComment(String courierComment) {
        WebElement commentInput = driver.findElement(courierCommentField);
        commentInput.sendKeys(courierComment);
    }

    // Метод нажатия кнопки для заказа
    public void submitOrder() {
        WebElement orderBtn = driver.findElement(orderButton);
        orderBtn.click();
    }

    // Метод подтвердждения заказа
    public void confirmOrder() {
        WebElement confirmationModal = wait.until(ExpectedConditions.visibilityOfElementLocated(orderConfirmationModal));
        WebElement confirmBtn = driver.findElement(confirmOrderButton);
        confirmBtn.click();
        boolean isPopupClosed = wait.until(ExpectedConditions.invisibilityOf(confirmationModal));
        Assert.assertTrue("Модальное окно с заказом не закрылось", isPopupClosed);
    }

    // Метод джидания появления и клика на кнопку "Посмотреть статус"
    public void viewOrderStatus() {
        WebElement confirmationPopup = wait.until(ExpectedConditions.visibilityOfElementLocated(orderConfirmationModal));
        WebElement viewStatusButton = driver.findElement(nextButtonInOrderConfirmationPopup);
        viewStatusButton.click();
        boolean isPopupClosed = wait.until(ExpectedConditions.invisibilityOf(confirmationPopup));
        Assert.assertTrue("Модальное окно с заказом не закрылось", isPopupClosed);
    }

    // Общий метод для заполнения всей формы полностью
    public void completeOrder(String rentalDuration, boolean isSelectBlack, boolean isSelectGray, String courierComment) {
        enterDeliveryDate();
        selectRentalDuration(rentalDuration);
        selectColor(isSelectBlack, isSelectGray);
        enterCourierComment(courierComment);
        submitOrder();
        confirmOrder();
        viewOrderStatus();
    }
}
