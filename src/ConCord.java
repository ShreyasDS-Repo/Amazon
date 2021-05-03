import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class ConCord {

	public static WebDriver driver;
	@BeforeTest
	public void setUp(){
		System.setProperty("webdriver.chrome.driver", "chromedriver//chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://www.amazon.in/");
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	}
	
	@Test
	public void Test() throws InterruptedException, IOException {
		System.err.println("Execution starting...");
		System.out.println("Reading data from excel file");
		String input =Utility.getData();
		
		
		System.out.println("entering data letter by letter");
		
		List<WebElement> suggestion = null;
		loop1:
		for(int i=0;i<input.length();i++){
			String temp = input.charAt(i)+"";
			driver.findElement(By.id("twotabsearchtextbox")).sendKeys(temp);
			Thread.sleep(2000);
			suggestion = driver.findElements(By.xpath("//div[@class='s-suggestion']"));
			
			for(WebElement e :suggestion){
				if(e.getText().toUpperCase().contains(input.toUpperCase())){
					System.out.println("writing the suggestions into a file");
					Utility.writeDataToFile(suggestion);
					e.click();
					break loop1;
				}
			}
		}
		
		System.out.println("store the displayed price");
		String price = driver.findElements(By.cssSelector("span[class='a-price-whole']")).get(0).getText();
		
		
		System.out.println("clicking on first result");
		List<WebElement> result = driver.findElements(By.cssSelector("a[class='a-link-normal a-text-normal']"));
		result.get(0).click();
		
		List<String> handles = new ArrayList<String>(driver.getWindowHandles());
		
		driver.switchTo().window(handles.get(1));
		
		System.out.println("clicking on add to cart button");
		driver.findElement(By.id("add-to-cart-button")).click();
		
		System.out.println("clicking on cart button");
		driver.findElement(By.id("hlb-view-cart-announce")).click();
		
		String total = driver.findElement(By.cssSelector("span[class='a-size-medium a-color-base sc-price sc-white-space-nowrap']")).getText();
		
		Assert.assertEquals(total.trim().split("\\.")[0], price);
		
		System.err.println("\nExecution ended...");
		
		
	}
	
	@AfterTest
	public void tearDown(){
		System.out.println("closing the browser");
		driver.quit();
	}

}
