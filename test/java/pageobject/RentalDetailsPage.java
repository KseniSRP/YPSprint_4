
 package pageobject;

import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class RentalDetailsPage {
    private WebDriver driver;
    private WebDriverWait wait; // Ожидание для использования в методе


    // Локаторы


    //Локатор для поля ввода даты доставки самоката, открывает календарь
    private By deliveryDateField = By.cssSelector("input[placeholder='* Когда привезти самокат']");

    // Локатор для поля выбора срока аренды (выпадающий список)
    private By rentalDurationField = By.className("Dropdown-control");

    // Локатор для выбора опции срока аренды из выпадающего списка
    private By durationOptionLocator = By.xpath("//div[contains(@class, 'Dropdown-menu')]");

    // Локатор для чекбокса выбора черного цвета самоката
    private By blackScooterCheckbox = By.xpath("//input[@id='black']");

    // Локатор для чекбокса выбора серого цвета самоката
    private By grayScooterCheckbox = By.xpath("//input[@id='grey']");

    // Локатор для поля ввода комментария для курьера
    private By courierCommentField = By.cssSelector("input.Input_Input__1iN_Z.Input_Responsible__1jDKN[placeholder='Комментарий для курьера']");

    // Локатор для кнопки <Заказать> для отправки формы заказа
    private By orderButton = By.xpath("//button[@class='Button_Button__ra12g Button_Middle__1CSJM']");

    //Локатор модального окна подтверждения заказа
    private By orderConfirmationModal = By.cssSelector("div.Order_Modal__YZ-d3");

    //Локатор кнопки подтверждения заказа в модальном окне
    private By confirmOrderButton = By.xpath("//button[contains(@class, 'Button_Button__ra12g') and contains(@class, 'Button_Middle__1CSJM') and text()='Да']");

    // Локатор кнопки "Посмотреть статус" в попапе успеха оформления ордера
    private By nextButtonInOrderConfirmationPopup = By.cssSelector(".Order_NextButton__1_rCA > button:nth-child(1)");

    // Конструктор класса
    public RentalDetailsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 5);
    }


    // Метод заполнения формы
    public void submitRentalRequest(String rentalDuration, boolean isSelectBlack, boolean isSelectGray, String courierComment) {
        // Форматирование текущей даты
        String formattedDate = LocalDate.now().plusDays(1).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        // Вводим отформатированную дату в поле ввода даты (минуя календарь)
        WebElement dateField = driver.findElement(deliveryDateField);
        dateField.sendKeys(formattedDate + Keys.ENTER);
        System.out.println("Дата заполнена: " + formattedDate);



        // Выбор срока аренды
        WebElement durationDropdown = driver.findElement(rentalDurationField);
        durationDropdown.click();
        WebElement durationOption = driver.findElement(durationOptionLocator);
        durationOption.click();
        System.out.println("Выбран срок аренды: " + rentalDuration);

        // Выбор цвета самоката
        if (isSelectBlack) {
            WebElement blackCheckbox = driver.findElement(blackScooterCheckbox);
            blackCheckbox.click(); // Выбор черного цвета
            System.out.println("Выбран черный самокат");
        }
        if (isSelectGray) {
            WebElement grayCheckbox = driver.findElement(grayScooterCheckbox);
            grayCheckbox.click(); // Выбор серого цвета
            System.out.println("Выбран серый самокат");
        }

        // Ввод комментария для курьера
        WebElement commentInput = driver.findElement(courierCommentField);
        commentInput.sendKeys(courierComment);
        System.out.println("Комментарий для курьера заполнен: " + courierComment);

        // Отправка формы заказа
        WebElement orderBtn = driver.findElement(orderButton);
        orderBtn.click();
        System.out.println("Кнопка заказа нажата");

        // Ждем показ модального окна о подтверждении заказа
        WebElement confirmationModal = wait.until(ExpectedConditions.elementToBeClickable(orderConfirmationModal));
        Assert.assertNotNull("Модальное окно подтверждения заказа не найдено", confirmationModal);
        System.out.println("Модальное окно подтверждения отображено");

        // Подтверждение заказа в модальном окне
        WebElement confirmBtn = driver.findElement(confirmOrderButton);
        confirmBtn.click();
        System.out.println("Нажата кнопка подтверждения заказа");

        // Ожидание закрытия попапа и проверка
        boolean isPopupClosed = wait.until(ExpectedConditions.invisibilityOf(confirmationModal));
        Assert.assertTrue("Модальное окно с заказом не закрылось", isPopupClosed);
        System.out.println("Модальное окно подтверждения заказа закрыто");

        // Переход к статусу заказа
        WebElement nextButtonInOrderConfirmation = wait.until(ExpectedConditions.elementToBeClickable(nextButtonInOrderConfirmationPopup));
        Assert.assertNotNull("Кнопка для перехода к статусу заказа не найдена", nextButtonInOrderConfirmation);
        nextButtonInOrderConfirmation.click();
        System.out.println("Переход к статусу заказа выполнен");
    }
}