import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class FirstTest {
    private WebDriver driver;

    private void testSleep() throws InterruptedException {
        Thread.sleep(1000);
    }

    @BeforeAll
    public static void webDriverInstall() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void webDriverStart() {
        driver = new ChromeDriver();
        driver.get("https://otus.home.kartushin.su/training.html");
    }

    @AfterEach
    public void webDriverStop() {
        if (driver != null)
            driver.close();
    }

    @Test
    @DisplayName("1 - Ввод текста")
    public void enterText() throws InterruptedException {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless"); // --headless нужен для запуска автотеста без активного окна браузера

        WebElement element = driver.findElement(By.id("textInput"));
        String inputValue = "OTUS";

        element.sendKeys(inputValue);
        testSleep();

        Assertions.assertEquals(
                inputValue,
                element.getAttribute("value")
        );
    }

    @Test
    @DisplayName("2 - Модальное окно")
    public void modularWindow() throws InterruptedException {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--kiosk");

        // Нажатие кнопки для вызова модульного кона
        WebElement element = driver.findElement(By.id("openModalBtn"));
        element.click();

        // Обработка модульного окна
        WebElement modal = driver.findElement(By.id("myModal"));
        Assertions.assertNotNull(modal); // Не уверен, что решение правильное
        testSleep();
    }

    @Test
    @DisplayName("3 - Регистрация")
    public void registration() throws InterruptedException {
        driver.manage().window().fullscreen();
        String userName = "Pavel";
        String mail = "pavel@mail.ru";

        WebElement name = driver.findElement(By.id("name"));
        name.sendKeys(userName);
        testSleep();

        WebElement email = driver.findElement(By.id("email"));
        email.sendKeys(mail);
        testSleep();

        WebElement submitButton = driver.findElement(By.cssSelector("#sampleForm > button"));
        submitButton.click();
        testSleep();

        WebElement dinamicResponse = driver.findElement(By.id("messageBox"));
        Assertions.assertEquals(
                String.format("Форма отправлена с именем: %s и email: %s", userName, mail),
                dinamicResponse.getText()
        );


    }
}
