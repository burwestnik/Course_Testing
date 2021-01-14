package okayga;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class WowheadWebTest{
    public static WebDriver driver;

    @BeforeClass
    public static void setup(){
        System.out.println("Testing Wowhead.");
        System.setProperty("webdriver.chrome.driver", ConfigPropertiesReader.getProperty("chromedriver"));
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @Test
    public void TestMainPage(){
        driver.get(ConfigPropertiesReader.getProperty("wowhead.mainpage"));
        String url = driver.getCurrentUrl();
        assertEquals("https://www.wowhead.com/",url);
    }

    @Test
    public void TestClickEntity(){
        driver.get(ConfigPropertiesReader.getProperty("wowhead.mainpage"));
        driver.findElement(By.className("header-nav-sire-denathrius")).click();
        String title = driver.findElement(By.className("title")).getText();
        assertEquals("Castle Nathria Raid Guides",title);
    }

    @Test
    public void TestClickFirstItem() {
        driver.get(ConfigPropertiesReader.getProperty("wowhead.items"));
        driver.findElements(By.className("listview-row")).get(0).click();
        String name = driver.findElement(By.className("heading-size-1")).getText();
        assertNotNull(name);
    }

    @Test
    public void TestCompareTwoItems() {
        driver.get(ConfigPropertiesReader.getProperty("wowhead.items"));
        List<WebElement> items = driver.findElements(By.className("listview-row"));
        String name1 = items.get(1).findElement(By.className("listview-cleartext")).getText();
        String name2 = items.get(2).findElement(By.className("listview-cleartext")).getText();
        assertNotEquals(name1, name2);
    }

    @Test
    public void ClickFirstItemWithFilters() {
        driver.get(ConfigPropertiesReader.getProperty("wowhead.items"));
        driver.findElement(By.name("min-level")).sendKeys("200");
        driver.findElement(By.name("max-level")).sendKeys("210");
        Select selectSide = new Select(driver.findElement(By.name("side")));
        selectSide.selectByVisibleText("Horde");
        Select selectClass = new Select(driver.findElement(By.name("class")));
        selectClass.selectByVisibleText("Warrior");
        driver.findElement(By.className("filter-row")).submit();
        driver.findElements(By.className("listview-row")).get(0).click();
        String name = driver.findElement(By.className("heading-size-1")).getText();
        assertEquals("Dreadfire Vessel",name);
    }

    @AfterClass
    public static void TearDown(){
        driver.quit();
        System.out.println("Testing finished.");
    }
}
