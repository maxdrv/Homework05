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
        driver.quit();
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


        WebElement howMuchTrips = findByXpath("//button[@type='button']//span[contains(text(), 'Несколько')]");
        wait.pollingEvery(Duration.ofMillis(300))
                .until(ExpectedConditions.elementToBeClickable(howMuchTrips));
        //howMuchTrips.click();
        // клик с использованием JavaScript
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].click();", howMuchTrips);

        /*
        JavascriptExecutor javascript = (JavascriptExecutor) driver;
        javascript.executeScript("var element = document.querySelector(\"//button[@type='button']//span[contains(text(), 'Несколько')]\");");
        //element.value = "whatever";
        */

        // Ввод "Шенген" в поле
        WebElement whereWeGoInput = findByXpath("//*[@class = 'form-control-multiple-autocomplete-actual-input tt-input']");
        whereWeGoInput.sendKeys("Шенген");

        // Выбор шенген из списка
        Actions action = new Actions(driver);
        action.sendKeys(Keys.DOWN).sendKeys(Keys.ENTER).perform();

        // Выбор списка стран
        WebElement choseFromInput = driver.findElement(By.xpath("//select[@id='ArrivalCountryList']"));
        wait.pollingEvery(Duration.ofMillis(300))
                .until(ExpectedConditions.elementToBeClickable(choseFromInput));
        Select chooseRegion = new Select(choseFromInput);
        chooseRegion.selectByVisibleText("Испания");

        // Дата первой поездки
        WebElement dateInput = findByXpath("//input[@data-test-name ='FirstDepartureDate']");
        scrollToElement(By.xpath("//input[@data-test-name ='FirstDepartureDate']"));
        dateInput.click();
        dateInput.sendKeys("01112018");
        action.sendKeys(Keys.ENTER).perform();

        // Выбор Не более 90 дней
        WebElement amountOfDays = findByXpath("//*[@class = 'form-group clearfix']//label[contains(text(), 'Не более 90 дней')]");
        scrollToElement(By.xpath("//*[@class = 'form-group clearfix']//label[contains(text(), 'Не более 90 дней')]"));
        amountOfDays.click();

        // Ввод имени и фамилии
        //div[@class = 'form-group validation-group-has-error']//input[1]
        WebElement nameInput = findByXpath("//div[@data-test-name = 'InsuredPerson']//input[@data-test-name = 'FullName']");
        wait.pollingEvery(Duration.ofMillis(300))
                .until(ExpectedConditions.elementToBeClickable(nameInput));
        nameInput.click();
        nameInput.sendKeys("ivanov Ivan");

        // Ввод даты рождения
        WebElement dateOfBirthInput = findByXpath("//div[@data-test-name = 'InsuredPerson']//input[@data-test-name = 'BirthDate']");
        dateOfBirthInput.click();
        dateOfBirthInput.sendKeys("10101990");

        // Планируется активный отдых
        //div[@class = 'toggle toggle-rgs off']//input[@type = 'checkbox']
        WebElement activeInput = findByXpath("//div[@class = 'toggle off toggle-rgs' and @data-toggle = 'toggle']");
        //scrollToElement(By.xpath("//div[@class = 'toggle off toggle-rgs' and @data-toggle = 'toggle']"));
        activeInput.click();

        // Согласен на обработку данных

        WebElement personalDataInput = findByXpath("//input[@type = 'checkbox' and @data-test-name = 'IsProcessingPersonalDataToCalculate' ]");
        scrollToElement(By.xpath("//input[@type = 'checkbox' and @data-test-name = 'IsProcessingPersonalDataToCalculate' ]"));
        personalDataInput.click();

        // step 9 Нажать рассчитать
        WebElement calculateInput = findByXpath("//button[@type = 'submit' and contains(., 'Рассчитать')]");
        scrollToElement(By.xpath("//button[@type = 'submit' and contains(., 'Рассчитать')]"));
        calculateInput.click();

        // step 10 Проверить значения:
        // Условия страхования – Многократные поездки в течении года
        checkForText = findByXpath("//div[@class = 'summary-row' and contains(., 'Условия страхования')]//span[@class = 'summary-value']");
//        wait.pollingEvery(Duration.ofMillis(300))
//                .until(ExpectedConditions.elementToBeClickable(checkForText));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class = 'summary-row' and contains(., 'Условия страхования')]//span[@class = 'summary-value']")));
        scrollToElement(By.xpath("//div[@class = 'summary-row' and contains(., 'Условия страхования')]//span[@class = 'summary-value']"));
        expectedText = "Многократные поездки в течение года";
        Assert.assertEquals("Значение условия страхования не соотвествует  ожидаемому", expectedText, checkForText.getText());

        // Территория – Шенген
        checkForText = findByXpath("//span[@data-bind = 'foreach: countries']/strong[1]");
        WebDriverWait wait2 = new WebDriverWait(driver, 5);
//        wait2.pollingEvery(Duration.ofMillis(300))
//                .until(ExpectedConditions.elementToBeClickable(checkForText));
        expectedText = "Шенген";
        Assert.assertEquals("Значение территории действия не соотвествует  ожидаемому", expectedText, checkForText.getText());

        // Застрахованный
        checkForText = findByXpath("//div[@class = 'summary-row' and contains(., 'Застрахованный')]//span[@class = 'summary-value']/strong[1]");
        expectedText = "IVANOV IVAN";
//        wait2.pollingEvery(Duration.ofMillis(300))
//                .until(ExpectedConditions.elementToBeClickable(checkForText));
        Assert.assertEquals("Значения ФИ застрахованного не соотвествует  ожидаемому", expectedText, checkForText.getText());

        // Дата рождения
        checkForText = findByXpath("//div[@class = 'summary-row' and contains(., 'Застрахованный')]//span[@class = 'summary-value']/span[1]/strong[1]");
        expectedText = "10.10.1990";
        Assert.assertEquals("Значения даты рождения не соотвествует  ожидаемому", expectedText, checkForText.getText());

        // Активный отдых - включен
        checkForText = findByXpath("//div[@class = 'summary-row']//span[@class = 'summary-value' and contains(., 'включая активный отдых')]/span[@class = 'text-bold']");
        expectedText = "Включен";
        Assert.assertEquals("Значения активного отдыха не соотвествует  ожидаемому", expectedText, checkForText.getText());
    }

    public void scrollToElement(By by) {
        Locatable element = (Locatable) driver.findElement(by);
        Point p = element.getCoordinates().onPage();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(" + p.getX() + "," + (p.getY()+50) + ");");
    }

    private WebElement findByXpath(String xpath) {
        return driver.findElement(By.xpath(xpath));
    }
}