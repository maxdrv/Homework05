import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.Locatable;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class SeleniumTest1 {
    WebDriver driver;

    @Before
    public void startTest() {
        System.setProperty("webdriver.chrome.driver", "G:\\Applana\\homework05\\homework05\\src\\driver\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        // step 1 Перейти по ссылке http://www.rgs.ru
        driver.get("http://www.rgs.ru");
    }

    @After
    public void closeDriver() {
        //driver.close();
    }

    @Test
    public void testRgsDms() {
        // step 2 Выбрать пункт меню – Страхование
        WebElement insuranceNavBarButton = findByXpath("//*[@id='main-navbar-collapse']//a[contains(text(), 'Страхование')]");
        insuranceNavBarButton.click();

        // step 3 Путешествия – Страхование выезжающих за рубеж
        WebElement abroadNavBarButton = findByXpath("//*[@class='adv-analytics-navigation-line3-link']//a[contains(text(), 'Выезжающим за рубеж')]");
        abroadNavBarButton.click();

        // step 4 Нажать рассчитать – Онлайн
        WebElement calculateOnline = findByXpath("//*[@class='thumbnail-footer']//a[contains(text(), 'Рассчитать ')]");
        // внизу страницы имеется context-bar, который располагается поверх кнопки
        // поэтому используется проктурка, что бы вывести кнопку из под context-bar
        scrollToElement(By.xpath("//*[@class='thumbnail-footer']//a[contains(text(), 'Рассчитать ')]"));
        calculateOnline.click();

        // step 5 Проверить наличие заголовка – Страхование выезжающих за рубеж
        WebElement checkForText = findByXpath("//*[@class='page-header']");
        String expectedText = "Страхование выезжающих за рубеж";
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.pollingEvery(Duration.ofMillis(300))
                .until(ExpectedConditions.elementToBeClickable(checkForText));
        Assert.assertEquals("Значения заголовка не соотвествует  ожидаемому", expectedText, checkForText.getText());

        // step 6 Заполнить форму:
        //        Несколько поездок в течении года
        //        Я согласен на обработку данных  - выбрать чекбокс
        // step 7 Нажать рассчитать
        // step 8 Заполнить поля:
        //        Куда едем – Шенген
        //        Страна въезда – Испания
        //        Дата первой поездки – 1 ноября
        //        Сколько дней планируете пробыть за рубежом за год – не более 90
        //        ФИО
        //        Дата рождения
        //        Планируется активный отдых
        //


        //WebElement howMuchTrips = findByXpath("//*[@class = 'btn-group-select width-xs-19rem btn-group']//button[@type='button' and contains(., 'Несколько')]");
        // Выбор несоклько поездок
        WebElement howMuchTrips = findByXpath("//*[@class = 'btn-group-select width-xs-19rem btn-group']//button[@type='button' and contains(., 'Несколько')]");
        // ожидание пока элемент станет виден
        wait.pollingEvery(Duration.ofMillis(300))
                .until(ExpectedConditions.elementToBeClickable(howMuchTrips));
        howMuchTrips.click();

        // Ввод "Шенген" в поле
        WebElement whereWeGoInput = findByXpath("//*[@class = 'form-control-multiple-autocomplete-actual-input tt-input']");
        whereWeGoInput.sendKeys("Шенген");

        // Выбор шенген из списка
        Actions action = new Actions(driver);
        action.sendKeys(Keys.DOWN).sendKeys(Keys.ENTER).perform();

        // Выбор списка стран
        WebElement choseFromInput = driver.findElement(By.name("ArrivalCountryList"));
        choseFromInput.click();
        // Выбор Италии из списка
        Select chooseRegion = new Select(choseFromInput);
        chooseRegion.selectByVisibleText("Испания");

        // Дата первой поездки
        WebElement dateInput = findByXpath("//*[@class = 'form-control-multiple-autocomplete-actual-input tt-input']");


//        WebElement choseCountryInput = findByXpath("//*[@class = 'form-control validation-control-has-error']");
//        choseCountryInput.click();

        //  step 9 Нажать рассчитать


//        10. Проверить значения:
//        Условия страхования – Многократные поездки в течении года
//        Территория – Шенген
//                Застрахованный
//        Дата рождения
//        Активный отдых - включен


    }

    public void scrollToElement(By by) {
        Locatable element = (Locatable) driver.findElement(by);
        Point p = element.getCoordinates().onPage();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(" + p.getX() + "," + (p.getY()+150) + ");");
    }

    private WebElement findByXpath(String xpath) {
        return driver.findElement(By.xpath(xpath));
    }
}