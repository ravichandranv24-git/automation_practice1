package Testautomationpractice_blogspot;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DynamicWebTableChromeCPU {

    public static void main(String[] args) {

        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://testautomationpractice.blogspot.com/");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        
        WebElement table = driver.findElement(By.xpath("//table[@name='BookTable']"));
        ((org.openqa.selenium.JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView(true);", table);

        // Locate all rows
        List<WebElement> rows = driver.findElements(
                By.xpath("//table[@name='BookTable']/tbody/tr"));

        boolean found = false;

        for (int i = 2; i <= rows.size(); i++) { 

            String name = driver.findElement(
                    By.xpath("//table[@name='BookTable']/tbody/tr[" + i + "]/td[1]"))
                    .getText();

            if (name.equalsIgnoreCase("Chrome")) {

                String cpu = driver.findElement(
                        By.xpath("//table[@name='BookTable']/tbody/tr[" + i + "]/td[2]"))
                        .getText();

                System.out.println("Name: " + name);
                System.out.println("CPU: " + cpu);

                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("Chrome not found in table.");
        }

       // driver.quit();
    }
}