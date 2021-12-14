import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.MatcherAssert.assertThat;

public class ComprarArticulos {
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
    public void debeAnadirArticuloAlCarrito(){
        login(correo,contrasena);

        WebElement irSeccionDresses = driver.findElement(By.xpath("(//a[@title='Dresses'])[2]"));
        irSeccionDresses.click();

        agregarAlCarritoYContinuarComprando("2");
        agregarAlCarritoYContinuarComprando("5");


        WebElement carritoCompras = driver.findElement(By.xpath("//span[@class='ajax_cart_quantity unvisible']"));
        assertThat("El carrito ya no esta vacio",Integer.valueOf(carritoCompras.getText()), Matchers.is(5));
    }
    public void login(String e_mail, String pass){

        WebElement signInBtn = driver.findElement(By.className("login"));
        signInBtn.click();
        WebElement email = driver.findElement(By.id("email"));
        WebElement password = driver.findElement(By.id("passwd"));
        WebElement btnlogin = driver.findElement(By.id("SubmitLogin"));
        email.sendKeys(e_mail);
        password.sendKeys(pass);
        btnlogin.click();
    }
    public void agregarArticuloAlCarrito(String numeroDelArticulo){

            WebElement articuloCompra = new WebDriverWait(driver, 10)
                    .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//div[@class='product-container'])[" + numeroDelArticulo + "]")));
            //System.out.println("Articulo "+numeroDelArticulo+" es visible? "+articuloCompra.isDisplayed());
            Actions actionProvider = new Actions(driver);
            actionProvider.moveToElement(articuloCompra).build().perform();
            WebElement addToCard = new WebDriverWait(driver, 10).
                    until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//span[contains(.,'Add to cart')])[" + numeroDelArticulo + "]")));
            actionProvider.click(addToCard).perform();


    }

    public void  agregarAlCarritoYContinuarComprando (String numeroDelArticulo){

        agregarArticuloAlCarrito(numeroDelArticulo);

        WebElement checkOut = new WebDriverWait(driver, 10).until(ExpectedConditions
                .visibilityOfElementLocated(By.xpath("//span[@title='Continue shopping']")));
        WebElement seguirComprando = driver.findElement(By.xpath("//i[@class='icon-chevron-left left']"));
        seguirComprando.click();

        WebElement salida = new WebDriverWait(driver, 5).until(ExpectedConditions
                .visibilityOfElementLocated(By.xpath("(//div[@class='product-container'])["+numeroDelArticulo+"]")));

    }
}
