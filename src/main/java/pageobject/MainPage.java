package pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;

public class MainPage {
    private WebDriver driver;

    // Локатор кнопки "Заказать" в верхней части страницы
    private By topOrderButton = By.cssSelector(".Button_Button__ra12g");

    // Локатор кнопки "Заказать" в нижней части страницы
    private By middleOrderButton = By.cssSelector("button.Button_Button__ra12g.Button_Middle__1CSJM");

    // Локатор для раздела FAQ - используется для подскрола к разделу
    private By faqSection = By.className("Home_FAQ__3uVm4");

    // Базовая часть идентификатора для заголовков FAQ (заголовок)
    private final String faqItemHeaderBase = "accordion__heading-";

    // Базовая часть идентификатора для значений FAQ (подзаголовок)
    private final String faqItemValueBase = "accordion__panel-";


    // Конструктор класса
    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    // Метод для клика по кнопка "Заказать" из хедера
    public void clickTopOrderButton() {

        driver.findElement(topOrderButton).click();
    }

    // Метод для клика по кнопке "Заказать" на странице
    public void clickMiddleOrderButton() {
        //Кнопка заказа находится в средней части MainPage, делаем подскролл к ней
        WebElement elementToScrollTo = driver.findElement(middleOrderButton);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", elementToScrollTo);
        elementToScrollTo.click();
    }

    /// Метод для подскролла до FAQ
    public void scrollToFaq() {
        WebElement elementToScrollTo = driver.findElement(faqSection);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", elementToScrollTo);
    }

    // Клик по пункту FAQ для его раскрытия
    public void clickFAQItem(int i) {
        By headerLocator = By.id(faqItemHeaderBase + i); // я так и не придумала как мне динамически можно локатор определить вне метода, поэтому стригу определила в части описания основных локаторов и просто ее тут добавила
        WebElement faqHeader = driver.findElement(headerLocator);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", faqHeader);
        faqHeader.click();
    }

    // Метод извлечения текста из описания FAQ
    public String getFAQItemValue(int i) {
        By panelLocator = By.id(faqItemValueBase + i); // аналогично предыдущему методу, локатор определяется динамически
        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement faqPanel = wait.until(ExpectedConditions.visibilityOfElementLocated(panelLocator));
        return faqPanel.getText();
    }
}



