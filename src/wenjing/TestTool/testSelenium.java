package wenjing.TestTool;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class testSelenium {

	public static void main(String[] args){
	   
		System.getProperties().setProperty("webdriver.chrome.driver", "/Users/y/Documents/spiderSpace/webCollectoer/chromedriver");
        WebDriver webDriver = new ChromeDriver();
        webDriver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
        webDriver.get("http://www.ebiotrade.com/newsf/2015-4/2015417145915706.htm");
        WebElement webElement = webDriver.findElement(By.xpath("/html"));
       
        
        System.out.println(webDriver.getPageSource());
        webDriver.close();
		
		
	}
		
}
