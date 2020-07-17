package runner;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.BasicConfigurator;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import utils.CommonUtils;
import utils.ServerService;

@RunWith(Cucumber.class)
@CucumberOptions(features = "C:\\Testing Software\\CucucmberAppium\\src\\test\\java\\Featurefile\\sanity.feature", 
glue = "in.Effort.Steps",strict=true, monochrome=true, tags= {"@signin"},
plugin = {"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"})
public class TestRunner { 
	public static AndroidDriver<MobileElement> driver;
	static String featureLoc = System.getProperty("user.dir") + "\\src\\test\\java\\Featurefile\\sanity.feature";
	static String nodejs = "C:/Program Files/nodejs/node.exe";
    static String appiumjs= System.getProperty("user.home") + "/AppData/Roaming/npm/node_modules/appium/build/lib/main.js";       //"C:/Users/spoors/AppData/Roaming/npm/node_modules/appium/build/lib/main.js";
	static String propertiesPath=  System.getProperty("user.dir") + "/src/test/resources/Effort.properties";                  //"C:/Testing Software/CucucmberAppium/src/test/resources/Effort.properties";
    
	@BeforeClass 
	public static void setup() throws IOException {
		if (driver == null) {   
			ServerService.services(""+nodejs+"" , ""+appiumjs+"");
			ServerService.stopService(); 
			ServerService.startService(); 
			CommonUtils.loadConfigProp(""+propertiesPath+"");
			CommonUtils.setCapabilitiesForAndoird();
			driver = CommonUtils.getDriver();
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			BasicConfigurator.configure();  
		}
	}

	@AfterClass
	public static void teardown() {
		driver.quit();
		ServerService.stopService();
	}
}
