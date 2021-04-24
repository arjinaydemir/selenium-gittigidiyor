import org.apache.logging.log4j.LogManager;
import org.junit.*;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.*;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class examples extends Base {
    static Logger log = Logger.getLogger(examples.class.getName());

    private By login=By.xpath("//div[@class=\"gekhq4-4 egoSnI\"]");
    private By girisYap=By.xpath("//span[text()=\"Giriş Yap\"]");
    private By mail=By.id("L-UserNameField");
    private By password=By.id("L-PasswordField");
    private By enter=By.id("gg-login-enter");
    private By kesfet=By.xpath("//input[@placeholder=\"Keşfetmeye Bak\"]");
    private By find=By.xpath("//span[text()=\"BUL\"]");

    @Test
    public void TestSearch() throws InterruptedException {
        element(login).click();
        element(girisYap).click();
        element(mail).sendKeys("arjinaydemir72@gmail.com");
        element(password).sendKeys("arjin.123");
        element(enter).click();
        element(kesfet).sendKeys("bilgisayar");
        element(find).click();
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        WebElement secondPage = driver.findElement(By.xpath("(//a[@href='/arama/?k=bilgisayar&sf=2'])[1]"));
//        https://stackoverflow.com/questions/30635228/how-to-click-a-href-link-using-selenium
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", secondPage);
//        rastgele ürün secimi
        int numberOfProducts = driver.findElements(By.xpath("//*[@class=\"image-container product-hslider-container\"]")).size();
        log.info("numberOfProducts: " + numberOfProducts);
        Random rand = new Random();
        int randomNumber = rand.nextInt(numberOfProducts);
        log.info("randomNumber: " + randomNumber);
        driver.findElement(By.xpath("(//*[@class=\"image-container product-hslider-container\"])[" + randomNumber + "]")).click();
        WebElement ele = driver.findElement(By.xpath("//button[@id='add-to-basket']"));
        //http://makeseleniumeasy.com/2020/05/25/elementclickinterceptedexception-element-click-intercepted-not-clickable-at-point-other-element-would-receive-the-click/
        jse.executeScript("arguments[0].click()", ele);
        WebElement urunSayfasiFiyat = driver.findElement(By.xpath("//span[@class='product-new-price']"));
        String getPPriceText = urunSayfasiFiyat.getText();
        System.out.println("product page price: " + getPPriceText);
        By sepet = By.cssSelector("a[class='gg-ui-btn-default padding-none']");
        driver.findElement(sepet).click();
        WebElement sepetFiyat = driver.findElement(By.xpath("//p[@class='new-price']"));
        String getSepetFiyat = sepetFiyat.getText();
        System.out.println("priceText: " + getSepetFiyat);
        Assert.assertEquals("fiyatlar esit değil", getSepetFiyat, getPPriceText);
        By numberBox = By.cssSelector("select[class='amount']");
        driver.findElement(numberBox).click();
        driver.findElement(By.cssSelector("select[class='amount']")).sendKeys(Keys.ARROW_DOWN);
        driver.findElement(By.cssSelector("select[class='amount']")).sendKeys(Keys.ENTER);
        Thread.sleep(5000);
        By deleteButton = By.cssSelector("i[class='gg-icon gg-icon-bin-medium']");
        driver.findElement(deleteButton).click();
        Thread.sleep(5000);
    }
}
