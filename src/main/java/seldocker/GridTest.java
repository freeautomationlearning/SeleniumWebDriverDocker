package seldocker;

import java.net.MalformedURLException;
import java.net.URL;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

/**
 * @author CHIRAG
 *
 */
public class GridTest {

	WebDriver driver;

	@BeforeSuite
	public void startContainer()
	{
		CommonMethods.runTerminalCommand("docker-compose up","Registered a node");
	}

	@BeforeTest
	@Parameters({"browser"})
	public void setup(@Optional("firefox")String browser) throws MalformedURLException
	{
		DesiredCapabilities cap = new DesiredCapabilities();
		URL url = new URL("http://192.168.99.100:4444/wd/hub");
		if(browser.equalsIgnoreCase("chrome"))
		{
			cap.setBrowserName("chrome");
			cap.setCapability("name", "ChromeTest"); //
			driver = new RemoteWebDriver(url, cap);
			driver.get("https://www.google.com");
		}else if(browser.equalsIgnoreCase("firefox"))
		{
			cap.setCapability(CapabilityType.BROWSER_NAME,BrowserType.FIREFOX);
			driver = new RemoteWebDriver(url, cap);
			driver.get("https://www.facebook.com");
		}
		System.out.println("=> Öpening in the "+browser);
	}

	@Test
	public void getTitle() throws MalformedURLException
	{
		System.out.println(driver.getTitle());
	}

	@AfterTest
	public void tearDown()
	{
		driver.quit();
	}

	@AfterSuite
	public void stopContainer()
	{
		CommonMethods.runTerminalCommand("docker-compose down","Removing selenium-hub");
	}

}
