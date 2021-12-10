import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class Sesion {
    WebDriver driver;

    @BeforeEach
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")
                .concat("\\src\\test\\resources\\drivers\\chromedriver.exe"));
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("http://automationpractice.com/index.php");
    }

    @AfterEach
    public void tearDown() throws InterruptedException {
        Thread.sleep(2000);
        driver.quit();
    }

    @Test
    public void debePermitirLogin() {

        WebElement email, password, signInBtn, submitLogin, title;

        signInBtn = driver.findElement(By.className("login"));
        signInBtn.click();

        email = driver.findElement(By.id("email"));
        password = driver.findElement(By.id("passwd"));
        submitLogin = driver.findElement(By.id("SubmitLogin"));

        email.sendKeys("pruebas_2021@gmail.com");
        password.sendKeys("pruebas12345");
        submitLogin.click();

        title = driver.findElement(By.cssSelector("h1[class='page-heading']"));

        Assertions.assertEquals("MY ACCOUNT", title.getText());


    }
}
