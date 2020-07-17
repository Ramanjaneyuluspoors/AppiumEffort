package utils;


import io.appium.java_client.MobileBy;

public class MediaPermission {

	// verifying media permission & location
	public static void signinMediaPermission() throws InterruptedException {
		try {
			if (CommonUtils.getdriver()
					.findElement(MobileBy
							.xpath("//*[@resource-id='com.android.permissioncontroller:id/permission_message']"))
					.isDisplayed()) {
				CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@text='Allow']")).click();
				System.out.println("media permission is allowed");
			} else {
				System.out.println("media is not found");
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		Thread.sleep(1000);
		if (CommonUtils.getdriver().findElement(MobileBy.id("signInButton")).isDisplayed()) {
			CommonUtils.getdriver().findElement(MobileBy.id("signInButton")).click();
		} else if (CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@text='SIGN IN']")).isDisplayed()) {
			CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@text='SIGN IN']")).click();
		} else {
			System.out.println("Location not found");
		}

	}
	
	public static void mediaPermission() {
		try {        
			if (CommonUtils.getdriver()
					.findElement(MobileBy.AndroidUIAutomator(
							"new UiSelector().resourceId(\"com.android.packageinstaller:id/permission_allow_button\")"))
					.isDisplayed()) {
				CommonUtils.getdriver().findElement(MobileBy.xpath("//*[@text='Allow']")).click();
				System.out.println("media permission is allowed");
			}
		} catch (Exception e) {
			System.out.println("media permission alert is not found");
		}
	}
	

	
	public static void formImageCapture(String imageClick, String selectClickedImage) throws InterruptedException {
		signinMediaPermission();
		CommonUtils.getdriver().findElement(MobileBy.id(imageClick)).click();
		CommonUtils.getdriver().findElement(MobileBy.xpath(selectClickedImage));
		CommonUtils.waitForElementVisibility("//*[@text='VIEW']");
	}
	
	public static void formVideoCapture(String startVideoXpath, String stopVideoXpath, String selectVideoXpath) throws InterruptedException { ////*[@content-desc='Start video']  ////*[@content-desc='Stop video']
		signinMediaPermission();
		CommonUtils.getdriver().findElement(MobileBy.xpath(startVideoXpath)).click();   ////*[@resource-id='com.google.android.GoogleCamera:id/shutter_button']
		Thread.sleep(3000); 
		CommonUtils.getdriver().findElement(MobileBy.xpath(stopVideoXpath)).click();
		CommonUtils.getdriver()
				.findElementByXPath(selectVideoXpath).click();
		CommonUtils.waitForElementVisibility("//*[@text='PLAY']");
	}
	
	
}
