import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.MatcherAssert.assertThat;

public class ContactUS {
    WebDriver driver;
    String path1, correo = "pruebas6.2021@gmail.com", contrasena="formulario12345";

    @BeforeEach
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")
                .concat("\\src\\test\\resources\\drivers\\chromedriver.exe"));
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("http://automationpractice.com/index.php");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS) ;
    }

    @AfterEach
    public void tearDown() throws InterruptedException {
        Thread.sleep(2000);
        driver.quit();
    }
    @Test
    public void seDebePoderSeleccionarAreaEncargada(){
        goToContactUs();

        seleccionarArea("Customer service");
    }
    @Test
    public void debeValidarLaEstructuraDelCorreo(){
        goToContactUs();
        ingresarEmail("osopolarenlospolos.com");

        WebElement validacionEmail = driver.findElement(By.xpath("//p[@class='form-group form-ok']"));
        System.out.println(validacionEmail.isDisplayed());
    }
    public void seleccionarArea(String area){
        WebElement subjectHeading = driver.findElement(By.id("id_contact"));
        Select selectSubjectHeading = new Select(subjectHeading);
        selectSubjectHeading.selectByVisibleText(area);
    }
    public void ingresarEmail(String email){
        WebElement correo = driver.findElement(By.id("email"));
        correo.sendKeys(email, Keys.TAB);
    }
    public void goToContactUs(){
        WebElement btnContactUS = driver.findElement(By.cssSelector("#contact-link"));
        btnContactUS.click();
        WebElement titlepage = driver.findElement(By.cssSelector("h1"));
        assertThat("estamos en el apartado Contact Us",titlepage.getText(), Matchers.containsString("CONTACT US"));

    }

}
