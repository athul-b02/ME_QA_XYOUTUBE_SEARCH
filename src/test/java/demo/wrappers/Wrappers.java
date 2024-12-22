package demo.wrappers;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Wrappers {
    /*
     * Write your selenium wrappers here
     */

    public static void clickOnElementWrapper(ChromeDriver driver, By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    public static void displayText(ChromeDriver driver, By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement messageSection = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));

        String text = messageSection.findElement(By.xpath("./h1")).getText() + "\n"
        + messageSection.findElement(By.xpath("./p[1]")).getText() + "\n"
        + messageSection.findElement(By.xpath("./p[2]")).getText();

        System.out.println("About Us Message:-->");
        System.out.println(text);
    }

    public static void scrollTillEndRight(ChromeDriver driver, By locator) {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        while (true) {
            try {
                WebElement scrollButton = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));

                scrollButton.click();

                WebElement scrollButton_updated = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
                if (!scrollButton_updated.isDisplayed())
                    break;
                System.out.println("Clicked on the scroll button");
            } catch (TimeoutException e) {
                // TODO: handle exception
                System.out.println(e.getMessage());
                return;
            }
        }
        System.out.println("Reached the end of the list");
    }

    public static String getMovieRating(ChromeDriver driver, By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement ratingElement = wait.until(ExpectedConditions.presenceOfNestedElementLocatedBy(locator,
                By.xpath(".//div[contains(@class,'style-type-simple')]/p")));
        return ratingElement.getText();
    }

    public static boolean checkMovieCategory(ChromeDriver driver, By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        try {
            wait.until(ExpectedConditions.presenceOfNestedElementLocatedBy(locator,
                    By.xpath(".//h3/span")));
            System.out.println("Category detected");
            return true;
        } catch (TimeoutException e) {
            // TODO: handle exception
            System.out.println("Category not detected");
            return false;
        }
    }

    public static boolean checkSongCount(ChromeDriver driver, By locator,int limit) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement countElement = wait.until(ExpectedConditions.presenceOfNestedElementLocatedBy(locator,
                    By.xpath(".//div[@class='badge-shape-wiz__text']")));
            int currentSongCount = Integer.parseInt(countElement.getText().replaceAll("[^0-9]", ""));
            System.out.println("Number of songs = "+currentSongCount);
            if(currentSongCount<=limit)
                return true;
            else
                return false;    

        } catch (TimeoutException e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
            return false;

        }
    }

    public static void printNewsDetails(ChromeDriver driver, By locator){
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement latestNewsElement = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            
            List <WebElement> newsLinks = latestNewsElement.findElements(By.xpath(".//div[@role='link']"));
            newsLinks = newsLinks.subList(0, 3);

            LinkedHashMap<String,Integer> hMap = new LinkedHashMap<>();
            for(WebElement newsLink : newsLinks){
                
                String title = newsLink.findElement(By.xpath(".//*[@id='home-content-text']/span[1]")).getText();
                int likeCount = 0;
                if(newsLink.findElements(By.xpath(".//span[@id='vote-count-middle']")).size()!=0){
                    likeCount = Integer.parseInt(
                        newsLink.findElement(By.xpath(".//span[@id='vote-count-middle']")).getText().trim()
                    );
                }
                hMap.put(title, likeCount);
            }
            
            int index=0,sum=0;
            for(Map.Entry<String,Integer> entry : hMap.entrySet()){
                System.out.println("News Link"+(++index));
                System.out.println("Content = "+entry.getKey()+"\n");
                sum+=entry.getValue();
            }
            System.out.println("Total number of likes= "+sum);


        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }

    }

}
