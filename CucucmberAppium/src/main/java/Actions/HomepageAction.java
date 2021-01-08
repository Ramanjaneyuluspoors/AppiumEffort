package Actions;

import java.net.MalformedURLException;
import java.text.ParseException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;

import common_Steps.AndroidLocators;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import modules_test.Forms_basic;
import utils.CommonUtils;
import utils.MediaPermission;

public class HomepageAction {
	public static pagefactory.HomePage HomePage;

	public HomepageAction() {
		HomePage = new pagefactory.HomePage();
		PageFactory.initElements(CommonUtils.getdriver(), HomePage);
	}

	// verify user sign-in and perform accordingly
	public static void signInAction() throws MalformedURLException, InterruptedException, ParseException {
		CommonUtils.waitForElementVisibility("//*[@resource-id='in.spoors.effortplus:id/startStopWorkSwitch']");
		CommonUtils.SwitchStatus("startStopWorkSwitch");
		if (CommonUtils.SwitchStatus("startStopWorkSwitch").contains("OFF")) {
			CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@class='android.widget.Switch'][@text='OFF']"))
					.click();
			Thread.sleep(1000);
			sign_in_Options();
		} // closing if block
	}

	// signin cases
	public static void sign_in_Options() throws InterruptedException, MalformedURLException, ParseException {
		// sign-in form(if sign-in form exist then capturing location page will not
		// display)
		CommonUtils.interruptSyncAndLetmeWork();
		if (CommonUtils.getdriver().findElements(By.id("saveForm")).size() > 0) {
			Forms_basic.verifyFormPagesAndFill();
			Forms_basic.formSaveButton();
		} else if (CommonUtils.getdriver().findElements(By.id("signInButton")).size() > 0) {
			CommonUtils.getdriver().findElement(By.id("signInButton")).click();
			if (CommonUtils.getdriver().findElements(By.id("heading")).size() > 0) {
				CommonUtils.getdriver().findElements(By.className("android.widget.Button")).get(0).click();
			}
			CommonUtils.interruptSyncAndLetmeWork();
			if (CommonUtils.getdriver().findElements(By.id("saveForm")).size() > 0) {
				Forms_basic.verifyFormPagesAndFill();
				Forms_basic.formSaveButton();
			}
		} else if (CommonUtils.getdriver().findElements(By.id("heading")).size() > 0) {
			CommonUtils.getdriver().findElements(By.className("android.widget.Button")).get(0).click();
		} else if (CommonUtils.getdriver().findElements(By.xpath("//*[@text='SIGN IN']")).size() > 0) {
			CommonUtils.getdriver().findElement(By.xpath("//*[@text='SIGN IN']")).click();
		} else if (CommonUtils.getdriver()
				.findElements(By.xpath("//*[@resource-id='com.android.permissioncontroller:id/permission_message']"))
				.size() > 0) {
			MediaPermission.signinMediaPermission();
			CommonUtils.getdriver().findElement(MobileBy.id("signInButton")).click();
		} else {
			System.out.println("Signin activity is not found");
		}
		CommonUtils.waitForElementVisibility("//*[@class='android.widget.Switch'][@text='ON']");
	}

	// verifying user home page navigation
	public static void NavigationVerfication() throws InterruptedException {
		Thread.sleep(3000);
		CommonUtils.waitForElementVisibility("//*[@text='Home']");
		MobileElement name = CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@text='Home']"));
		if (name.getText().contains("Home")) {
			System.out.println("---- User navigation is done ---- ");
		} else {
			System.out.println("*** App is taking more time to load *** ");
		}
	}

	// signout
	public static void signOutAction() throws MalformedURLException, InterruptedException, ParseException {
		Thread.sleep(3000);
		CommonUtils.waitForElementVisibility("//*[@class='android.widget.Switch']");
		CommonUtils.SwitchStatus("startStopWorkSwitch");
		if (CommonUtils.SwitchStatus("startStopWorkSwitch").contains("ON")) {
			CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@class='android.widget.Switch'][@text='ON']"))
					.click();
			// verify if sign-out form exist then fill if not signout from map
			signout_cases();
		}
	}

	// signout cases
	public static void signout_cases() throws InterruptedException, MalformedURLException, ParseException {
		CommonUtils.interruptSyncAndLetmeWork();
		if (CommonUtils.getdriver().findElements(By.id("saveForm")).size() > 0) {
			Forms_basic.verifyFormPagesAndFill();
			Forms_basic.formSaveButton();
		} else if (CommonUtils.getdriver().findElements(By.id("signInButton")).size() > 0) {
			CommonUtils.getdriver().findElement(By.id("signInButton")).click();
			// capture reason
			if (CommonUtils.getdriver().findElements(By.id("heading")).size() > 0) {
				CommonUtils.getdriver().findElements(By.className("android.widget.Button")).get(0).click();
			}
			if (CommonUtils.getdriver().findElements(By.id("saveForm")).size() > 0) {
				Forms_basic.verifyFormPagesAndFill();
				Forms_basic.formSaveButton();
			}
		} else if (CommonUtils.getdriver().findElements(By.xpath("//*[@text='SIGN OUT']")).size() > 0) {
			CommonUtils.getdriver().findElement(By.xpath("//*[@text='SIGN OUT']")).click();
		} else {
			System.out.println("Location not found");
		}
		CommonUtils.waitForElementVisibility("//*[@class='android.widget.Switch'][@text='OFF']");
	}

	// form alert signout
	public static void form_SignIn_SignOut() {
		if (CommonUtils.getdriver().findElementsByXPath("//*[@text='SIGN-IN']").size() > 0) {
			CommonUtils.getdriver().findElementByXPath("//*[@text='SIGN-IN']").click();
		} else if (CommonUtils.getdriver().findElementsByXPath("//*[@text='SIGN-OUT']").size() > 0) {
			CommonUtils.getdriver().findElementByXPath("//*[@text='SIGN-OUT']").click();
		}
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// scroll to settings and send debug information
	public static void perform_debug_action() throws MalformedURLException, InterruptedException {
		MobileActionGesture.scrollUsingText("Settings");
		if (CommonUtils.getdriver()
				.findElements(By.xpath("//*[@class='android.widget.LinearLayout']/*[@text='Settings']")).size() > 0) {
			AndroidLocators.xpath("//*[@class='android.widget.LinearLayout']/*[@text='Settings']").click();
		} else {
			CommonUtils.openMenu();
			MobileActionGesture.scrollUsingText("Settings");
			Thread.sleep(1000);
		}
		CommonUtils.allow_bluetooth();
		CommonUtils.waitForElementVisibility("//*[@text='Settings']");
	}

	// select work/form/customer/dayplan from home fab dialog
	public static void select_dialog_list(String selectValue) {
		List<MobileElement> selectList = CommonUtils.getdriver()
				.findElements(MobileBy.AndroidUIAutomator("new UiSelector().resourceId(\"android:id/text1\")"));
		for (int i = 0; i < selectList.size(); i++) {
			String List_view_Text = selectList.get(i).getText();
			if (List_view_Text.contains(selectValue)) {
				CommonUtils.getdriver().findElement(By.xpath("//*[contains(@text,'" + selectValue + "')]")).click();
				break;
			}
		}

	}

}
