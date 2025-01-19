import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class FirstTest {
    private WebDriver driver;

    private String getUrl() throws IOException {
        Properties properties = new Properties();
        InputStream input = ClassLoader.getSystemResourceAsStream("config.properties");
        properties.load(input);

        return properties.getProperty("url");
    }

    private WebElement getElement(By condition) {
        WebElement result = driver.findElement(condition);
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.presenceOfElementLocated(condition));

        return result;
    }

    @BeforeAll
    public static void webDriverInstall() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void webDriverStart() throws IOException {
        driver = new ChromeDriver();
        driver.get(getUrl());
    }

    @AfterEach
    public void webDriverStop() {
        if (driver != null)
            driver.close();
    }

    @Test
    @DisplayName("1 - Ввод текста")
    public void enterText() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless"); // --headless нужен для запуска автотеста без активного окна браузера

        String inputValue = "OTUS";

        WebElement element = getElement(By.id("textInput"));
        element.sendKeys(inputValue);

        Assertions.assertEquals(
                inputValue,
                element.getAttribute("value")
        );
    }

    @Test
    @DisplayName("2 - Модальное окно")
    public void modularWindow() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--kiosk");

        // Нажатие кнопки для вызова модульного кона
        WebElement moduleButton = getElement(By.id("openModalBtn"));
        moduleButton.click();

        // Обработка модульного окна
        WebElement moduleWindow = getElement(By.id("myModal"));
        Assertions.assertNotNull(moduleWindow); // Не уверен, что решение правильное
    }

    @Test
    @DisplayName("3 - Регистрация")
    public void registration() {
        driver.manage().window().fullscreen();
        String userName = "Pavel";
        String mail = "pavel@mail.ru";

        WebElement name = getElement(By.id("name"));
        name.sendKeys(userName);

        WebElement email = getElement(By.id("email"));
        email.sendKeys(mail);

        WebElement submitButton = getElement(By.cssSelector("#sampleForm > button"));
        submitButton.click();

        WebElement dynamicResponse = getElement(By.id("messageBox"));
        Assertions.assertEquals(
                String.format("Форма отправлена с именем: %s и email: %s", userName, mail),
                dynamicResponse.getText()
        );
    }
}
