import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class FlightSearch {

    public WebDriver driver = null;
    public final String url = "http://jt-dev.azurewebsites.net/#/SignUp";
    public final String name = "Punar";
    public final String organisation = "Punar";
    public final String emailAddress = "soniPunar@gmail";

    @Before
    public void setUp(){
        String webDriverPath = "src/test/resources/chromedriver.exe";
        System.setProperty("webdriver.chrome.driver", webDriverPath);
        driver = new ChromeDriver();
    }

    @Test  // Run this Test to Check Automation
    public void executeTest() throws Exception{
        launchFlightServicesPage();
        validateDropDown();
        fillDetails();
        verifyEmail();
    }

    public void launchFlightServicesPage() throws Exception{
        driver.navigate().to(url);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    public void validateDropDown() throws Exception{
        driver.findElement(By.xpath("//*[@id=\"language\"]")).click();
        List<WebElement> webElementList = driver.findElements(By.className("ui-select-choices-row-inner"));
        Assert.assertEquals("English option unavailable",webElementList.get(0).getText(),"English");
        Assert.assertEquals("Dutch option unavailable",webElementList.get(1).getText(),"Dutch");
    }

    public void fillDetails() throws Exception{
        driver.findElement(By.id("name")).click();
        driver.findElement(By.id("name")).sendKeys(name);
        driver.findElement(By.id("orgName")).sendKeys(organisation);
        driver.findElement(By.id("singUpEmail")).sendKeys(emailAddress);
        driver.findElement(By.xpath("//*[@id=\"content\"]/div/div[3]/div/section/div[1]/form/fieldset/div[4]/label/span")).click();
    }

    public void verifyEmail() throws Exception{
        WebDriverWait wait = new WebDriverWait(driver,30);

        WebElement button = driver.findElement(By.xpath("//*[@id=\"content\"]/div/div[3]/div/section/div[1]/form/fieldset/div[5]/button"));
        wait.until(ExpectedConditions.visibilityOf(button));
        if(button.isEnabled())
            button.click();
        else
            Assert.fail("Data not filled correctly");

        WebElement alert = driver.findElement(By.xpath("//*[@id=\"content\"]/div/div[3]/div/section/div[1]/form/div"));
        wait.until(ExpectedConditions.visibilityOf(alert));
        if(alert.getText().toLowerCase().contains("invalid"))
            Assert.fail("Email Verification failed");
    }

    @After
    public void tearDown(){
        driver.quit();
    }
}
