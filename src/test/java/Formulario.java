import net.bytebuddy.agent.builder.AgentBuilder;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;

public class Formulario {
    WebDriver driver;
    String path1, correo = "pruebas"+aletario()+".2021@gmail.com", contrasena="formulario12345";

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
    public void llenarFormularioDeRegistro() throws InterruptedException {

        // hace clic en el boton de Sign In
        WebElement signInBtn = driver.findElement(By.className("login"));
        signInBtn.click();
        System.out.println(correo);
        //ingresa el correo y hace clic en crear usuario
        WebElement email=driver.findElement(By.id("email_create"));
        email.sendKeys(correo);
        WebElement createdAccount=driver.findElement(By.xpath("//span[contains(.,'Create an account')]"));
        createdAccount.click();


        //PERSONAL INFORMATION
        WebElement checkbtn= driver.findElement(By.id("id_gender1"));
        checkbtn.click();
        WebElement firstname=driver.findElement(By.id("customer_firstname"));
        WebElement lastname=driver.findElement(By.id("customer_lastname"));
        WebElement password=driver.findElement(By.id("passwd"));

        //envio de informacion al formulario
        firstname.sendKeys("Selenium");
        lastname.sendKeys("Test");
        password.sendKeys(contrasena);

        //seleccion de fecha
        WebElement day= driver.findElement(By.id("days"));
        WebElement month=driver.findElement(By.id("months"));
        WebElement year=driver.findElement(By.id("years"));
        Select selectDay = new Select(day);
        Select selectYear = new Select(year);
        Select selectMonth = new Select(month);
        selectDay.selectByIndex(20);
        selectMonth.selectByIndex(6);
        selectYear.selectByValue("1992");
        WebElement check = driver.findElement(By.id("uniform-optin"));
        check.click();

        //YOUR ADDRESS
        WebElement company = driver.findElement(By.id("company"));
        company.sendKeys("Dulcedecoco ltda");
        WebElement adrress1 = driver.findElement(By.id("address1"));
        adrress1.sendKeys("Av 1 1-11");
        WebElement adrress2 = driver.findElement(By.id("address2"));
        adrress1.sendKeys("Av 1 1-08");
        WebElement city = driver.findElement(By.id("city"));
        city.sendKeys("Bogota DC");
        WebElement state = driver.findElement(By.id("id_state"));
        Select selectState = new Select(state);
        selectState.selectByValue("27");
        WebElement postcode = driver.findElement(By.id("postcode"));
        postcode.sendKeys("23001");
        WebElement country = driver.findElement(By.id("id_country"));
        Select selectCountry = new Select(country);
        selectCountry.selectByValue("21");
        WebElement additionalInformation = driver.findElement(By.id("other"));
        additionalInformation.sendKeys("se agrega información adicional");
        WebElement homePhone = driver.findElement(By.id("phone"));
        homePhone.sendKeys("5555555");
        WebElement mobilePhone = driver.findElement(By.id("phone_mobile"));
        mobilePhone.sendKeys("6666666666");
        WebElement alias = driver.findElement(By.id("alias"));
        alias.clear();
        alias.sendKeys("El testzito");
        WebElement register = driver.findElement(By.id("submitAccount"));
        register.click();

        //comprobacion con el titulo My Account

        WebElement title = driver.findElement(By.cssSelector("h1[class='page-heading']"));
        Assertions.assertEquals("MY ACCOUNT", title.getText());

    }

    @Test
    public void noDebePermiteLogin(){

        login(correo,contrasena);
        WebElement alerta= driver.findElement(By.xpath("/html/body/div/div[2]/div/div[3]/div/div[1]"));
        //System.out.println(alerta.getText());
        assertThat("El correo o la contraseña son errados",alerta.getText(), Matchers.containsString("Authentication failed."));
    }

    @Test
    public void debePermiteLogin(){
        login("pruebas6.2021@gmail.com",contrasena);
        WebElement title = driver.findElement(By.cssSelector("h1[class='page-heading']"));
        assertThat("Se muestra en la pantalla el titulo de MY ACCOUNT",title.getText(),Matchers.comparesEqualTo("MY ACCOUNT"));
    }
    @Test
    public void precioArticulos(){
        login("pruebas6.2021@gmail.com",contrasena);

        WebElement btnWomen = driver.findElement(By.xpath("//a[@title='Women']"));
        btnWomen.click();

        /*
        WebElement botoniravestidos = driver.findElement(By.xpath("(//a[@title='Dresses'])[2]"));
        botoniravestidos.click();

        WebElement botoniravestidos = driver.findElement(By.xpath("(//a[@title='T-shirts'])[2]"));
        botoniravestidos.click();
         */

        WebElement tituloSeccion = driver.findElement(By.xpath("//span[@class='navigation_page']"));
        assertThat("Se muestra el titulo de Women  en la pantalla",tituloSeccion.getText(),Matchers.comparesEqualTo("Women"));

        ArrayList<WebElement> price = (ArrayList<WebElement>) driver.findElements(By.xpath("//div[@class='product-container']/div[@class='right-block']/div[1]"));
        List listaPrecios = new ArrayList<String>();// Se crea lista para almacenar los valores de los articulos

        double totalPrecios=0;
        /**Ciclo para recorrer el Arraylist price */
        for (int i = 0; i<price.size(); i++){
            String priceSinSigno = price.get(i).getText().replace("$",""); // quita el signo $
            int numIndex= Integer.valueOf(priceSinSigno.indexOf(" "));// retorna la posición del primer espacion en blanco en contrado si no tien retorn -1
            /** agrega a la lista precios los valores del Arralyst price */
            if (numIndex==-1){
                listaPrecios.add(priceSinSigno);
            }
            else{
                listaPrecios.add(priceSinSigno.substring(0,numIndex)); //agrega a la lista precios el subestring desde 0 hasta antes del primer espacio(quitando las dos tafifas)
            }

            totalPrecios = totalPrecios + Double.valueOf((String) listaPrecios.get(i));// convierte a doble cada uno de los valores de la lista y los sumaas
        }

        WebElement items = driver.findElement(By.xpath("//div[@class='product-count']"));
        int numeroItems= optenerNumeroItems(items.getText());

        assertThat("la cantidad de items son "+numeroItems,price.size(),Matchers.equalTo(numeroItems));
        assertThat("La suma de los precios de todas las prendas es $196",totalPrecios,Matchers.equalTo(196.38));
    }
    @Test
    public void agregarPrendaAlCarrito() throws InterruptedException {

        login("pruebas6.2021@gmail.com", contrasena);

        WebElement botonVestidos = driver.findElement(By.xpath("//a[@title='Women']"));
        botonVestidos.click();

        WebElement articuloCompra = driver.findElement(By.xpath("(//div[@class='product-container'])[1]"));

        Actions actionProvider = new Actions(driver);
        actionProvider.moveToElement(articuloCompra).perform();

        //WebElement addToCard = driver.findElement(By.xpath("//div[@class='product-container']/div[@class='right-block']/div[@class='button-container']/a[@title='Add to cart']//span"));
        WebElement addToCard = driver.findElement(By.xpath("//span[contains(.,'Add to cart')]"));
        //WebElement addToCard = driver.findElement(By.xpath("(//div[@class='product-container'])[2]//span[contains(.,'Add to cart')]"));

        //System.out.println(addToCard.isEnabled());
        actionProvider.click(addToCard).build().perform();

        WebElement checkOut = new WebDriverWait(driver, 20)
                .until(ExpectedConditions
                        .visibilityOfElementLocated(By.xpath("//div[@id='layer_cart']/div[@class='clearfix']/div[@class='layer_cart_cart col-xs-12 col-md-6']")));
        WebElement cerrarCheckOut = driver.findElement(By.xpath("//div[@id='layer_cart']/div[@class='clearfix']/div[@class='layer_cart_product col-xs-12 col-md-6']/span"));
        cerrarCheckOut.click();

        WebElement carritoCompras = driver.findElement(By.xpath("//span[@class='ajax_cart_quantity unvisible']"));
        assertThat("El carrito ya no esta vacio",Integer.valueOf(carritoCompras.getText()),Matchers.is(1));
        //System.out.println(carritoCompras.getText());

    }



    /********* metodos creados ********************/

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
    /** genera un número aleatorio para concatenarlo al correo*/
    public String aletario(){
        int numero = (int)(Math.random()*1000+1);
        System.out.println(numero);
        String path=String.valueOf(numero);
        return path;
    }
    /**  extrae el penultimo texto de una cadena separada por espacio " " y lo convierte entero */
    int optenerNumeroItems(String str){

       String[] partes = str.split(" ");
       String penultima = partes[partes.length-2];
       int intPenultima= Integer.valueOf(penultima);
       return intPenultima;
    }
}