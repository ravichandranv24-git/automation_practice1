package Testautomationpractice_blogspot;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class PaginationTableOptimized {

    public static void main(String[] args) throws InterruptedException {

        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));

        driver.get("https://testautomationpractice.blogspot.com/");

        // Scroll to table
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,2500)");

        boolean productFound = false;

        while (!productFound) {

            // Get complete table text
            WebElement table = driver.findElement(By.xpath("//table[@name='BookTable']"));
            String tableContent = table.getText();

            if (tableContent.contains("Portable Charger")) {
            	
                System.out.println("Portable Charger is Present");

                
                driver.findElement(By.xpath("//td[text()='Portable Charger']/preceding-sibling::td/input"))
                        .click();

                productFound = true;
            } 
            else {
                // Click Next button
                WebElement nextBtn = driver.findElement(By.id("paginationNext"));
                
                if (nextBtn.getAttribute("class").contains("disabled")) {
                    System.out.println("Product not found in any page");
                    break;
                }

                nextBtn.click();
                Thread.sleep(1000);
            }
        }

        driver.quit();
    }
}