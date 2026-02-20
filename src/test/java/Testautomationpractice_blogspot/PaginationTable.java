package Testautomationpractice_blogspot;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PaginationTable {

    public static void main(String[] args) {

        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://testautomationpractice.blogspot.com/");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Wait until table is visible
        WebElement table = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("productTable")));

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView(true);", table);

        boolean found = false;

        List<WebElement> pages = driver.findElements(
                By.xpath("//ul[@class='pagination']/li"));

        int totalPages = pages.size();

        for (int p = 1; p <= totalPages; p++) {

            // Re-locate rows every time after page load
            List<WebElement> rows = wait.until(
                    ExpectedConditions.presenceOfAllElementsLocatedBy(
                            By.xpath("//table[@id='productTable']/tbody/tr")));

            for (WebElement row : rows) {

                String productName = row.findElement(By.xpath("./td[2]"))
                        .getText().trim();

                if (productName.equalsIgnoreCase("VR Headset")) {

                    String price = row.findElement(By.xpath("./td[3]"))
                            .getText().trim();

                    System.out.println("Product Found on Page: " + p);
                    System.out.println("Product Name: " + productName);
                    System.out.println("Price: " + price);

                    WebElement checkbox = row.findElement(
                            By.xpath(".//input[@type='checkbox']"));

                    // Wait until checkbox is clickable
                    wait.until(ExpectedConditions.elementToBeClickable(checkbox));

                    checkbox.click();   // Normal click (Best practice)

                    found = true;
                    break;
                }
            }

            if (found)
                break;

            // Click next page
            if (p < totalPages) {

                WebElement nextPage = driver.findElement(
                        By.xpath("//ul[@class='pagination']/li[" + (p + 1) + "]"));

                nextPage.click();

                // Wait until next page becomes active
                wait.until(ExpectedConditions.textToBePresentInElementLocated(
                        By.xpath("//ul[@class='pagination']/li[@class='active']"),
                        String.valueOf(p + 1)));
            }
        }

        if (!found) {
            System.out.println("Product not found.");
        }

        driver.quit();
    }
}