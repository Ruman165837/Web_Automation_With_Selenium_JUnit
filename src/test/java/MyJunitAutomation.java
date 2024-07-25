import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;

public class MyJunitAutomation {
    static WebDriver driver;

    @BeforeAll
    public static void setup(){
        driver = new ChromeDriver();
        driver.manage().window().maximize();;
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
    }


    @Test
    public void formFillup() throws InterruptedException {
        driver.get("https://demoqa.com/text-box");
        driver.findElement(By.id("userName")).sendKeys("Ruman Islam");
        driver.findElements(By.className("mr-sm-2")).get(1).sendKeys("rumanislam165837@gmail.com");
        driver.findElements(By.tagName("textarea")).get(0).sendKeys("Panthapath, Dhaka");
        driver.findElements(By.tagName("textarea")).get(1).sendKeys("Katiadi, Kishoreganj");
        Utils.scroll(driver,0,500);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.id("submit")));
        element.click();
        String nameActual = driver.findElement(By.id("name")).getText();
        String nameExpected = "Ruman Islam";
        Assertions.assertTrue(nameActual.contains(nameExpected));

    }


    @Test
    public void mouseHover() throws InterruptedException {
        driver.get("https://su.edu.bd/");
        Actions actions = new Actions(driver);
        List<WebElement> menuAdmission = driver.findElements(By.xpath("//a[contains(text(),\"Admission\")]"));
        actions.moveToElement(menuAdmission.get(1)).perform();
        Thread.sleep(3000);
    }


    @Test
    public void keyboardEvents() throws InterruptedException {
        driver.get("https://www.google.com/");
        WebElement searchElement = driver.findElement(By.name("q"));
        Actions action = new Actions(driver);
        action.moveToElement(searchElement);
        action.keyDown(Keys.SHIFT);
        action.sendKeys("Sonargaon University")
                .keyUp(Keys.SHIFT)
                .doubleClick()
                .contextClick()
                .perform();

        Thread.sleep(5000);
    }


    @Test
    public void clickOnMultipleButon() throws IOException {
        driver.get("https://demoqa.com/buttons");
        Actions action = new Actions(driver);
        List<WebElement> buttons= driver.findElements(By.className("btn-primary"));
        action.doubleClick(buttons.get(0)).perform();
        String text= driver.findElement(By.id("doubleClickMessage")).getText();
        Assertions.assertTrue(text.contains("You have done a double click"));

        action.contextClick(buttons.get(1)).perform();
        String text2= driver.findElement(By.id("rightClickMessage")).getText();
        Assertions.assertTrue(text2.contains("You have done a right click"));

        action.click(buttons.get(2)).perform();
        String text3= driver.findElement(By.id("dynamicClickMessage")).getText();
        Assertions.assertTrue(text3.contains("You have done a dynamic click"));
        Utils.getScreenshot(driver);


    }


    @Test
    public void modalDialog() throws InterruptedException {
        driver.get("https://demoqa.com/modal-dialogs");
        driver.findElement(By.id("showSmallModal")).click();
        String text= driver.findElement(By.className("modal-body")).getText();
        System.out.println(text);
        driver.findElement(By.id("closeSmallModal")).click();
    }


    @Test
    public void uploadImage(){
        driver.get("https://demoqa.com/upload-download");
        WebElement uploadElement = driver.findElement(By.id("uploadFile"));
        uploadElement.sendKeys("C:\\Users\\hishabee\\Downloads\\3.jpg");

        String text= driver.findElement(By.id("uploadedFilePath")).getText();
        Assertions.assertTrue(text.contains("3.jpg"));
    }


    @Test
    public void downloadFile(){
        driver.get("https://demoqa.com/upload-download");
        driver.findElement(By.id("downloadButton")).click();
    }


    @Test
    public void handleMultipleTab() throws InterruptedException {
        driver.get("https://demoqa.com/browser-windows");
        driver.findElement(By.id("tabButton")).click();
        Thread.sleep(3000);
        ArrayList<String> w = new ArrayList(driver.getWindowHandles());
        System.out.println(w.get(0));
        System.out.println(w.get(1));
           //switch to open tab
        driver.switchTo().window(w.get(1));
        System.out.println("New tab title: " + driver.getTitle());
        String text = driver.findElement(By.id("sampleHeading")).getText();
        Assertions.assertEquals(text,"This is a sample page");
        driver.close();
        driver.switchTo().window(w.get(0));
    }

    @Test
    public void handleMultipleWindow(){
        driver.get("https://demoqa.com/browser-windows");

//Thread.sleep(5000);
//WebDriverWait wait = new WebDriverWait(driver, 30);
//wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("windowButton")));
        driver.findElement(By.id(("windowButton"))).click();
        String mainWindowHandle = driver.getWindowHandle();
        Set<String> allWindowHandles = driver.getWindowHandles();
        Iterator<String> iterator = allWindowHandles.iterator();

        while (iterator.hasNext()) {
            String ChildWindow = iterator.next();
            if (!mainWindowHandle.equalsIgnoreCase(ChildWindow)) {
                driver.switchTo().window(ChildWindow);
                String text= driver.findElement(By.id("sampleHeading")).getText();
                Assertions.assertTrue(text.contains("This is a sample page"));
            }

        }
        driver.close();
        driver.switchTo().window(mainWindowHandle);
    }


    @Test
    public void webTables(){
        driver.get("https://demoqa.com/webtables");
        driver.findElement(By.xpath("//span[@id='edit-record-1']//*[@stroke='currentColor']")).click();
        driver.findElement(By.id("submit")).click();

    }


    @Test
    public void scrapData(){
        driver.get("https://demoqa.com/webtables");
        WebElement table = driver.findElement(By.className("rt-tbody"));
        List<WebElement> allRows = table.findElements(By.className("rt-tr"));
        int i=0;
        for (WebElement row : allRows) {
            List<WebElement> cells = row.findElements(By.className("rt-td"));
            for (WebElement cell : cells) {
                i++;
                System.out.println("num["+i+"] "+ cell.getText());

            }
        }
    }

    @Test
    public void handleIframe(){
        driver.get("https://demoqa.com/frames");
        driver.switchTo().frame("frame1");
        String text= driver.findElement(By.id("sampleHeading")).getText();
        Assertions.assertTrue(text.contains("This is a sample page"));
        driver.switchTo().defaultContent();

    }

    @AfterAll
    public static void closeTest(){
//        driver.close();
    }
}
