package modules_test;

import java.net.MalformedURLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.text.RandomStringGenerator;
import org.junit.gen5.api.Assertions;
import Actions.MobileActionGesture;
import common_Steps.AndroidLocators;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import utils.CommonUtils;

public class Customer_Advancesetting {
	
	public static String[] criteriaCondition = { "Equals to", "Not Equals To", "Greater Than", "Less Than",
			"Greater Than Or Equals To", "Less Than Or Equals To", "After", "Before", "In between", "On", "Not on",
			"On Or Before", "On Or After" };

	public static void customer_Fields_min_max_testing(int min, int max) throws MalformedURLException {

		// customer fields
		List<MobileElement> customer_fields = AndroidLocators.findElements_With_Xpath(
				"//*[@resource-id='in.spoors.effortplus:id/formHolderEdit']/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.LinearLayout//*[contains(@class,'Text') and not(contains(@resource-id,'android:id/text1'))]");
		
		// get size of list
		int custFieldsCount = customer_fields.size();
		System.out.println("**** Customer fields count **** :" + custFieldsCount);

		// removing elements from list
		customer_fields.clear();

		// find the last element to stop continuos scrolling
		String custLastElement = null;
		// scroll from top to bottom and add customerlist fields
		MobileActionGesture.flingVerticalToBottom_Android();

		// add customer fields to get last element of the customer fields
		customer_fields.addAll(AndroidLocators.findElements_With_Xpath(
				"//*[@resource-id='in.spoors.effortplus:id/formHolderEdit']/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.LinearLayout//*[contains(@class,'Text') and not(contains(@resource-id,'android:id/text1'))]"));

		// get count of elements
		custFieldsCount = customer_fields.size();
		System.out.println("**** Customer fields in the last page are **** :" + custFieldsCount);

		// retrieving the last element of customer
		custLastElement = customer_fields.get(customer_fields.size() - 1).getText();
		System.out.println("==== customer last element ==== : " + custLastElement);

		// removing elelments from list(here we only need last element after finding
		// last element we are removing elements from list)
		customer_fields.clear();

		// scroll to top
		MobileActionGesture.flingToBegining_Android();
		
		// add first screen elements of customer to list
		customer_fields.addAll(AndroidLocators.findElements_With_Xpath(
				"//*[@resource-id='in.spoors.effortplus:id/formHolderEdit']/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.LinearLayout//*[contains(@class,'Text') and not(contains(@resource-id,'android:id/text1'))]"));

		// get count of elements available in the first screen are
		custFieldsCount = customer_fields.size();
		System.out.println("*** customer first screen fields count *** : " + custFieldsCount);

		// scroll and add elelemnts to list until last element found
		while (!customer_fields.isEmpty()) {
			boolean flag = false;
			MobileActionGesture.verticalSwipeByPercentages(0.8, 0.2, 0.5);

			customer_fields.addAll(AndroidLocators.findElements_With_Xpath(
					"//*[@resource-id='in.spoors.effortplus:id/formHolderEdit']/android.widget.LinearLayout//android.widget.LinearLayout//*[contains(@class,'Text') and not(contains(@resource-id,'android:id/text1'))]"));

			// get the count of customer fields
			custFieldsCount = customer_fields.size();
			System.out.println("... After swiping and adding customer fields, the count is ... : " + custFieldsCount);
			
			// if customer last element matches element present in List then break the for loop
			for (int l = 0; l < custFieldsCount; l++) {
				// printing the elements in the list
				System.out.println("*** Fields Inside loop *** : " + customer_fields.get(l).getText());

				// if list matches with last element the loop will break
				if (customer_fields.get(l).getText().contains(custLastElement)) {
					System.out.println("---- customer inside elements ---- : " + customer_fields.get(l).getText());
					flag = true;
				}
			}
			
			// break the loop once last element found
			if (flag == true) {
				break;
			}
		}
        
		MobileActionGesture.flingToBegining_Android();
		
		// providing input for customer fields by iterating the customer list
		for (int i = 0; i < custFieldsCount; i++) {
			String originalText = customer_fields.get(i).getText();
			String cusFieldsText = customer_fields.get(i).getText().split("\\(")[0].replaceAll("[!@#$%&*(),.?\":{}|<>]", "");
			System.out.println("---- Before removing special character ---- : " + originalText
					+ "\n.... After removing special character .... : " + cusFieldsText);
			
			switch (cusFieldsText) {
			case "Name":
				MobileActionGesture.scrollUsingText(cusFieldsText);
				if (AndroidLocators.findElements_With_Xpath("//*[contains(@text,'" + cusFieldsText + "')]")
						.size() > 0) {
					String randomstringCusName = RandomStringUtils.randomAlphabetic(5).toLowerCase();
					CommonUtils.getdriver().findElement(MobileBy.id("nameEditText")).sendKeys(randomstringCusName);
				}
				break;
			case "Text":
				MobileActionGesture.scrollUsingText(cusFieldsText);
				if (AndroidLocators.findElements_With_Xpath("//*[contains(@text,'" + cusFieldsText + "')]")
						.size() > 0) {
					customerField_TextDataType_Min_Max_Validations(cusFieldsText, min, max);
				}
				break;
			case "Number":
				MobileActionGesture.scrollUsingText(cusFieldsText);
				if (AndroidLocators.findElements_With_Xpath("//*[contains(@text,'" + cusFieldsText + "')]")
						.size() > 0) {
					inputValueMin_Max_Validation(cusFieldsText, min, max);
				}
				break;
			case "Currency":
				MobileActionGesture.scrollUsingText(cusFieldsText);
				if (AndroidLocators.findElements_With_Xpath("//*[contains(@text,'" + cusFieldsText + "')]")
						.size() > 0) {
					inputValueMin_Max_Validation(cusFieldsText, min, max);
				}
				break;
			}
		}
	}
	
	
	//customer text field inputting min max value
	public static void customerField_TextDataType_Min_Max_Validations(String cusFieldsText, int min, int max) {
		
		// assigning min max values
		int min_test = min, max_test = max;

		// get above/below element of main element
		String getAboveOrBelowOfMainElement = Work_advanceSettings.commonMethodForInput(cusFieldsText);

		// get random data
		RandomStringGenerator textGenerator = new RandomStringGenerator.Builder().withinRange('a', 'z').build();
		
		// inserting min input value
		for (int n = 0; n < 2; n++) {

			String textMinInput = textGenerator.generate(min_test);
			String textMinInput1 = textGenerator.generate(min);
			System.out.println("*** Given minimum length is *** : " + textMinInput1);
			System.out.println("*** Validation minimum length is *** : " + textMinInput);
			
			// based previous element inputing the main element
			Work_advanceSettings.insertInputBasedOnAboveOrBelowEle(getAboveOrBelowOfMainElement, textMinInput);
			
			//save customer
			CustomerPageActions.saveMethod();
			
			// validating min values
			if (textMinInput.length() < textMinInput1.length()) {
				String text = CommonUtils.OCR();
				System.out.println("---- Expected toast message for min input is ---- : " + text);
				Assertions.assertFalse(
						text.contains("" + cusFieldsText + " Smaller than the minimum allowed length" + (textMinInput1)
								+ "."),
						"" + cusFieldsText + " Smaller than the minimum allowed length " + (textMinInput1) + ".");
			}
			min_test = min_test - 1;
		}
		
		// inserting max input values
		for (int p = 0; p < 2; p++) {

			String textMaxInput = textGenerator.generate(max_test);
			String textMaxInput1 = textGenerator.generate(max);
			System.out.println("*** Given maximum length is *** : " + textMaxInput1);
			System.out.println("*** Validation maximum length is *** : " + textMaxInput);

			// based previous element inputing the main element
			Work_advanceSettings.insertInputBasedOnAboveOrBelowEle(getAboveOrBelowOfMainElement, textMaxInput);

			// save customer
			CustomerPageActions.saveMethod();

			// validating max values
			if (textMaxInput.length() > textMaxInput1.length()) {
				String text = CommonUtils.OCR();
				System.out.println("Expected toast message for max input is " + text);
				Assertions.assertFalse(
						text.contains("" + cusFieldsText + " larger than the maximum allowed length " + (textMaxInput1)
								+ "."),
						"" + cusFieldsText + " larger than the maximum allowed length " + (textMaxInput1) + ".");
			}
			max_test = max_test + 1;
		}

		// based previous element inputing the main element
		Work_advanceSettings.insertInputBasedOnAboveOrBelowEle(getAboveOrBelowOfMainElement,
				textGenerator.generate(max));
	}
	
	
	//currency or number or phone inputting min max value
	public static void inputValueMin_Max_Validation(String cusFieldsText, int min, int max) {
		// assigning min and max values to min_test and max_test
		int min_test = min, max_test = max;

		// get above/below element of main element
		String getAboveOrBelowOfMainElement = Work_advanceSettings.commonMethodForInput(cusFieldsText);

		// inserting min input value
		for (int n = 0; n < 2; n++) {
			
			System.out.println("*** Entering minimum value *** : " + min_test);

			// based previous element inputing the main element
			Work_advanceSettings.insertInputBasedOnAboveOrBelowEle(getAboveOrBelowOfMainElement,
					String.valueOf(min_test));

			// save customer
			CustomerPageActions.saveMethod();

			if (min < min_test) {
				String text = CommonUtils.OCR();
				System.out.println("Expected toast message for min value is :" + text);
				Assertions.assertFalse(text.contains("" + cusFieldsText + " cannot be less than " + min + "."),
						"" + cusFieldsText + " cannot be less than " + min + ".");
			}

			// clear the input
			Work_advanceSettings.clearTheInputBasedOnAboveOrBelowElement(getAboveOrBelowOfMainElement);
			
			//decrease the min value
			min_test = min_test - 1;
		}
		
		
		// inserting max input value
		for (int p = 0; p < 2; p++) {

			System.out.println("*** Entering maximum value is *** : " + max_test);

			// based previous element inputing the main element
			Work_advanceSettings.insertInputBasedOnAboveOrBelowEle(getAboveOrBelowOfMainElement, String.valueOf(max_test));
			
			// save customer
			CustomerPageActions.saveMethod();
			
			// validating max values
			if (max > max_test) {
				String text = CommonUtils.OCR();
				System.out.println("Expected toast message for max value is :" + text);
				Assertions.assertFalse(text.contains("" + cusFieldsText + " cannot be greater than " + max + "."),
						"" + cusFieldsText + " cannot be greater than " + max + ".");
			}

			// clear the input
			Work_advanceSettings.clearTheInputBasedOnAboveOrBelowElement(getAboveOrBelowOfMainElement);
			
			//increase the max value
			max_test = max_test + 1;
		}
		
		// based previous element inputing the main element
		Work_advanceSettings.insertInputBasedOnAboveOrBelowEle(getAboveOrBelowOfMainElement, String.valueOf(max));
	}
	
	public static void regularExpression_Testing(String regExp) {
		String randomstringCusName = RandomStringUtils.randomAlphabetic(5).toLowerCase();
		AndroidLocators.enterTextusingID("nameEditText", randomstringCusName);

		MobileActionGesture.scrollUsingText("Text");
		
		//retring the above/below of main element
		String getAboveOrBelowOfMainElement = Work_advanceSettings.commonMethodForInput("Text");

		
		for (int i = 0; i < 3; i++) {
			String unMatchRegExp = FormAdvanceSettings.unMatch_regExp(regExp);

			// removing special characters from the given string
			getAboveOrBelowOfMainElement = getAboveOrBelowOfMainElement.split("\\(")[0].replaceAll("[!@#$%&*,.?\":{}|<>]", "");
			System.out.println("*** Inputting text of the previous element is *** :" + getAboveOrBelowOfMainElement);
			
			/* inputting the unmatching regular expression(special charcter) */
			MobileActionGesture.scrollUsingText(getAboveOrBelowOfMainElement);
			
			
			// inserting unmatching regular expression
			Work_advanceSettings.insertInputBasedOnAboveOrBelowEle(getAboveOrBelowOfMainElement, unMatchRegExp);
			
			// save customer
			CustomerPageActions.saveMethod();
			
			/**** Matching regular expression ****/
			
			// retrieving regular expression
			String matchRegExp = FormAdvanceSettings.match_regExp(regExp);
			
			MobileActionGesture.scrollUsingText(getAboveOrBelowOfMainElement);
			
			/* inputting the matching regular expression */
			Work_advanceSettings.insertInputBasedOnAboveOrBelowEle(getAboveOrBelowOfMainElement,
					"" + matchRegExp.charAt(i));
			
			// save customer
			CustomerPageActions.saveMethod();
		}
	}
	
	// Field Dependency based on value in other fields
	public static void FieldDependencyBasedOnValueInOtherFieldsTesing(String conditionName, String dependentFieldType,
			String inputData) throws MalformedURLException, InterruptedException {
		
		// initializing and assigning
		String customerBaseCondition = conditionName;
		String customerkFieldType = dependentFieldType;
		String customerFieldInput = inputData;

		// customer fields
		List<MobileElement> customer_fields = AndroidLocators.findElements_With_Xpath(
				"//*[@resource-id='in.spoors.effortplus:id/formHolderEdit']/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.LinearLayout//*[contains(@class,'Text') and not(contains(@resource-id,'android:id/text1'))]");

		// customer specified fields
		List<MobileElement> customer_Dependencyfield = AndroidLocators.findElements_With_Xpath(
				"//*[@resource-id='in.spoors.effortplus:id/formHolderEdit']/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.LinearLayout//*[contains(@text,'"
						+ customerkFieldType + "')]");

		// count of field
		int countOfDependentField = customer_Dependencyfield.size();
		System.out.println(" ===== Customer Field Count is ===== : " + countOfDependentField);

		// remove fields from list
		customer_Dependencyfield.clear();

		// initializig the lastElement
		String CustomerLastElementText = null;

		// scroll to end and get last element text from the list
		MobileActionGesture.flingVerticalToBottom_Android();
		customer_Dependencyfield.addAll(AndroidLocators.findElements_With_Xpath(
				"//*[@resource-id='in.spoors.effortplus:id/formHolderEdit']/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.LinearLayout//*[contains(@class,'Text') and not(contains(@resource-id,'android:id/text1'))]"));

		CustomerLastElementText = customer_Dependencyfield.get(customer_Dependencyfield.size() - 1).getText();
		System.out.println("**** Customer Last element text is **** :" + CustomerLastElementText);

		// remove fields from list
		customer_Dependencyfield.clear();
		MobileActionGesture.flingToBegining_Android();

		//add if dependent field present in the first screen
		customer_Dependencyfield.addAll(AndroidLocators.findElements_With_Xpath(
				"//*[@resource-id='in.spoors.effortplus:id/formHolderEdit']/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.LinearLayout//*[contains(@text,'"
						+ customerkFieldType + "')]"));
		
		countOfDependentField = customer_Dependencyfield.size();
		System.out.println("**** Before swiping count **** : " + countOfDependentField);
		
		// scroll and add elelemnts to list until last element or dependency element found
		while (!customer_fields.isEmpty()) {
			boolean flag = false;
			MobileActionGesture.verticalSwipeByPercentages(0.8, 0.2, 0.5);

			customer_fields.addAll(AndroidLocators.findElements_With_Xpath(
					"//*[@resource-id='in.spoors.effortplus:id/formHolderEdit']/android.widget.LinearLayout//android.widget.LinearLayout//*[contains(@class,'Text') and not(contains(@resource-id,'android:id/text1'))]"));

			customer_Dependencyfield.addAll(AndroidLocators.findElements_With_Xpath(
					"//*[@resource-id='in.spoors.effortplus:id/formHolderEdit']/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.LinearLayout//*[contains(@text,'"
							+ customerkFieldType + "')]"));

			// get the count of customer fields
			countOfDependentField = customer_Dependencyfield.size();
			System.out.println("... After swiping and adding customer fields, the count is ... : " + countOfDependentField);

			// if customer last element matches element present in List then break the for
			// loop
			for (int l = 0; l < countOfDependentField; l++) {
				// printing the elements in the list
				System.out.println("*** Fields Inside loop *** : " + customer_fields.get(l).getText());

				// if specified element found break the for loop
				if (customer_Dependencyfield.size() > 0
						|| customer_fields.get(l).getText().contains(CustomerLastElementText)) {
					System.out.println("---- customer inside elements ---- : " + customer_fields.get(l).getText());
					flag = true;
				}
			}

			// break the loop specified element found
			if (flag == true) {
				break;
			}
		}

		// iterate and fill the form
		for (int k = 0; k < countOfDependentField; k++) {
			String OriginalText = customer_Dependencyfield.get(k).getText();
			String fieldsText = customer_Dependencyfield.get(k).getText().split("\\(")[0]
					.replaceAll("[!@#$%&*(),.?\":{}|<>]", "");
			System.out.println("***** Before removing regular expression ***** : " + OriginalText
					+ "\n..... After removing regexp ..... : " + fieldsText);
			if (fieldsText.equals(customerkFieldType)) {
				switch (fieldsText) {
				case "Text":
					MobileActionGesture.scrollUsingText(fieldsText);
					// text method
					customerTextFieldDependency(customerBaseCondition, fieldsText, customerFieldInput);
					break;
				case "Currency":
					MobileActionGesture.scrollUsingText(fieldsText);
					// currency method
					testingDependencyFieldsBasedOnCurrency(customerBaseCondition, fieldsText, inputData);
					break;
				case "Number":
					MobileActionGesture.scrollUsingText(fieldsText);
					// currency method
					testingDependencyFieldsBasedOnCurrency(customerBaseCondition, fieldsText, inputData);
					break;
				case "Customer":
					MobileActionGesture.scrollUsingText(fieldsText);
					// customer method
					Work_advanceSettings.customerPicker(customerBaseCondition, fieldsText, customerFieldInput);
					break;
				case "Pick List":
					MobileActionGesture.scrollUsingText(fieldsText);
					// picklist method
					Work_advanceSettings.workPickList(customerBaseCondition, fieldsText, customerFieldInput);
					break;
				case "Dropdown":
					MobileActionGesture.scrollUsingText(fieldsText);
					// dropdown method
					Work_advanceSettings.dropdownSelection(customerBaseCondition, fieldsText, customerFieldInput);
					break;
				case "Date":
					MobileActionGesture.scrollUsingText(fieldsText);
					// Date method
					Work_advanceSettings.datePicking(customerBaseCondition, fieldsText, customerFieldInput);
					break;
				}
			}
		}
	}
	
	//customer text input validate dependency fields
	public static void customerTextFieldDependency(String customerBaseCondition, String fieldsText, String customerFieldInput) {
		String getAboveOrBelowOfMainElement = Work_advanceSettings.commonMethodForInput(fieldsText);
		
		// initializing string for inputdata
		String textInputData = null;

		// text input data
		String[] myInput = { customerFieldInput, customerFieldInput + "extraWords", "extraWords" + customerFieldInput,
				"extraWords" };

		// iterating the given input
		for (int k = 0; k < myInput.length; k++) {
			MobileActionGesture.scrollUsingDirectText(getAboveOrBelowOfMainElement);
			textInputData = myInput[k];
			System.out.println("------- Text Input data ------ :" + textInputData);

			// based previous element inputing the main element
			Work_advanceSettings.insertInputBasedOnAboveOrBelowEle(getAboveOrBelowOfMainElement, textInputData);

			// validating the work fields
			if (customerBaseCondition.equals("Hide when")) {
				MobileActionGesture.scrollTospecifiedElement("REFRESH");
				if (AndroidLocators.findElements_With_Xpath(
						"//*[@text='REFRESH']/parent::*//*[@resource-id='in.spoors.effortplus:id/formLinearLayout']/android.widget.LinearLayout/android.widget.LinearLayout//*[starts-with(@text,'"
								+ textInputData + "')]")
						.size() > 0) {
					CommonUtils.getdriver()
							.findElement(MobileBy.xpath("//*[starts-with(@text,'" + textInputData + "')]")).clear();
					AndroidLocators.clickElementusingResourceId("in.spoors.effortplus:id/refreshButton");
				}
			} else if (customerBaseCondition.equals("Disable when")) {
				MobileActionGesture.scrollTospecifiedElement("REFRESH");
				MobileActionGesture.scrollUsingText(getAboveOrBelowOfMainElement);
			} else if (customerBaseCondition.equals("Mandatory when")) {
				MobileActionGesture.scrollTospecifiedElement("REFRESH");
				MobileActionGesture.scrollUsingText(getAboveOrBelowOfMainElement);
			}
		}
	}
	
	public static void testingDependencyFieldsBasedOnCurrency(String customerBaseCondition, String fieldsText, String textInputData) {
		String getAboveOrBelowOfMainElement = Work_advanceSettings.commonMethodForInput(fieldsText);
		
		// initializing and assigning
		int currencyInput = Integer.parseInt(textInputData);
		
		for (int j = 0; j < 3; j++) {
			
			currencyInput = currencyInput - 1;
			System.out.println("------- Currency value ------ :" + currencyInput);

			// based previous element inputing the main element
			Work_advanceSettings.insertInputBasedOnAboveOrBelowEle(getAboveOrBelowOfMainElement, String.valueOf(currencyInput));

			// validating the work fields
			if (customerBaseCondition.equals("Hide when")) {
				MobileActionGesture.scrollTospecifiedElement("REFRESH");
				if (AndroidLocators.findElements_With_Xpath(
						"//*[@text='REFRESH']/parent::*//*[@resource-id='in.spoors.effortplus:id/formLinearLayout']/android.widget.LinearLayout/android.widget.LinearLayout//*[starts-with(@text,'"
								+ textInputData + "')]")
						.size() > 0) {
					CommonUtils.getdriver()
							.findElement(MobileBy.xpath("//*[starts-with(@text,'" + textInputData + "')]")).clear();
					AndroidLocators.clickElementusingResourceId("in.spoors.effortplus:id/refreshButton");
				}
			} else if (customerBaseCondition.equals("Disable when")) {
				MobileActionGesture.scrollTospecifiedElement("REFRESH");
				MobileActionGesture.scrollUsingText(getAboveOrBelowOfMainElement);
			} else if (customerBaseCondition.equals("Mandatory when")) {
				MobileActionGesture.scrollTospecifiedElement("REFRESH");
				MobileActionGesture.scrollUsingText(getAboveOrBelowOfMainElement);
			}
		}
	}
	
	//hightlighting background color based on dependent values 
	public static void validating_BackgroundFields_Basedon_Dependent_Fieldvalue(String inputValue,
			String dependencyField) throws MalformedURLException {

		String colorFieldType = dependencyField;
		int fieldValue = Integer.parseInt(inputValue);

		// get all formfields elements of text
		List<MobileElement> customerFieldsLists = AndroidLocators.findElements_With_Xpath(
				"//*[@resource-id='in.spoors.effortplus:id/formHolderEdit']/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.LinearLayout//*[contains(@class,'Text') and not(contains(@resource-id,'android:id/text1'))]");

		// get all formfields elements of text
		List<MobileElement> customerSpecifiedField = AndroidLocators.findElements_With_Xpath(
				"//*[@resource-id='in.spoors.effortplus:id/formHolderEdit']/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.LinearLayout//*[contains(@text,'"
						+ colorFieldType + "')]");

		// get form fields count
		int CustomerColorFieldsCount = customerFieldsLists.size();
		System.out.println("===== Customer Color fields count is ====== :" + CustomerColorFieldsCount);

		// removing elements from list
		customerFieldsLists.clear();

		String customerLastElement = null;

		// scroll and get last element
		MobileActionGesture.flingVerticalToBottom_Android();
		customerFieldsLists.addAll(AndroidLocators.findElements_With_Xpath(
				"//*[@resource-id='in.spoors.effortplus:id/formHolderEdit']/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.LinearLayout//*[contains(@class,'Text') and not(contains(@resource-id,'android:id/text1'))]"));

		// retriving the customer last element
		customerLastElement = customerFieldsLists.get(customerFieldsLists.size() - 1).getText()
				.replaceAll("[!@#$%&*(),.?\":{}|<>]", "");
		System.out.println("**** Customer Last element is **** :" + customerLastElement);

		// removing elements from list
		customerFieldsLists.clear();

		// adding specified element to list present in first screen
		MobileActionGesture.flingToBegining_Android();
		customerSpecifiedField.addAll(AndroidLocators.findElements_With_Xpath(
				"//*[@resource-id='in.spoors.effortplus:id/formHolderEdit']/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.LinearLayout//*[contains(@text,'"
						+ colorFieldType + "')]"));

		CustomerColorFieldsCount = customerSpecifiedField.size();
		System.out.println("**** Before swiping fields count is **** : " + CustomerColorFieldsCount);

		while (customerSpecifiedField.isEmpty()) {
			boolean flag = false;
			MobileActionGesture.verticalSwipeByPercentages(0.8, 0.2, 0.5);
			customerSpecifiedField.addAll(AndroidLocators.findElements_With_Xpath(
					"//*[@resource-id='in.spoors.effortplus:id/formHolderEdit']/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.LinearLayout//*[contains(@text,'"
							+ colorFieldType + "')]"));

			customerFieldsLists.addAll(AndroidLocators.findElements_With_Xpath(
					"//*[@resource-id='in.spoors.effortplus:id/formHolderEdit']/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.LinearLayout//*[contains(@class,'Text') and not(contains(@resource-id,'android:id/text1'))]"));

			// count of customer fields
			CustomerColorFieldsCount = customerSpecifiedField.size();
			System.out.println("===== After swiping fields count ===== : " + CustomerColorFieldsCount);

			// traverse the loop if customer last element matches element present in List or
			// specified element found then exit
			// loop
			for (int i = 0; i < CustomerColorFieldsCount; i++) {
				// printing the elements in the list
				System.out.println("*** Fields Inside loop *** : " + customerSpecifiedField.get(i).getText());

				// if specified element found break the for loop
				if (customerSpecifiedField.size() > 0
						|| customerFieldsLists.get(i).getText().contains(customerLastElement)) {
					System.out
							.println("---- customer inside elements ---- : " + customerSpecifiedField.get(i).getText());
					flag = true;
				}
			}

			// break the loop specified element found
			if (flag == true) {
				break;
			}
		}

		MobileActionGesture.flingToBegining_Android();
		//highlighting background color based on the field value
		insertValueForHighlightingBackgroungValue(customerSpecifiedField, CustomerColorFieldsCount, colorFieldType,
				fieldValue);
	}

	//highlighting background color based on the field value
	public static void insertValueForHighlightingBackgroungValue(List<MobileElement> customerSpecifiedField,
			int CustomerColorFieldsCount, String colorFieldType, int fieldValue) {
		// iterate and fill the form
		for (int k = 0; k < CustomerColorFieldsCount; k++) {
			String OriginalText = customerSpecifiedField.get(k).getText();
			String fieldsText = customerSpecifiedField.get(k).getText().split("\\(")[0]
					.replaceAll("[!@#$%&*(),.?\":{}|<>]", "");
			System.out.println("***** Before removing regular expression ***** : " + OriginalText
					+ "\n..... After removing regexp ..... : " + fieldsText);
			if (fieldsText.equals(colorFieldType)) {
				switch (fieldsText) {

				case "Currency":
				case "G-Currency":
				case "Number":
				case "G-Number":

					MobileActionGesture.scrollUsingText(fieldsText);
					String getAboveOrBelowOfMainElement = Work_advanceSettings.commonMethodForInput(fieldsText);

					if (AndroidLocators.findElements_With_Xpath("//*[contains(@text,'" + fieldsText + "')]")
							.size() > 0) {

						for (int j = 0; j < 3; j++) {
							MobileActionGesture.scrollUsingText(getAboveOrBelowOfMainElement);

							// inputting value
							fieldValue = fieldValue - 1;
							System.out.println("==== inputting color value ==== :" + fieldValue);

							// based previous element inputing the main element
							Work_advanceSettings.insertInputBasedOnAboveOrBelowEle(getAboveOrBelowOfMainElement,
									String.valueOf(fieldValue));

							MobileActionGesture.scrollTospecifiedElement("REFRESH");

							// incrementing input value
							fieldValue = fieldValue + 2;
							System.out.println("---- After increasing the fielvalue ---- :" + fieldValue);
						}
					} else {
						System.out.println("Element not found");
					}
				}
			}
		}
	}

	//customer error and warn message
	public static void fieldValidation_BasedOn_ValueIn_OtherField(String errorCondition, String fieldInput)
			throws MalformedURLException, InterruptedException, ParseException {
		
		// splitting and assigning input to variable
		String[] inputArray = fieldInput.split(",");
		String currencyInput = inputArray[0];
		String dateInput = null;
		dateInput = inputArray[1];

		
		// customer fields
		List<MobileElement> customer_fields = AndroidLocators.findElements_With_Xpath(
				"//*[@resource-id='in.spoors.effortplus:id/formHolderEdit']/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.LinearLayout//*[contains(@class,'Text') and not(contains(@resource-id,'android:id/text1'))]");

		// get size of list
		int custFieldsCount = customer_fields.size();
		System.out.println("**** Customer fields count **** :" + custFieldsCount);

		// removing elements from list
		customer_fields.clear();

		// find the last element to stop continuos scrolling
		String custLastElement = null;
		// scroll from top to bottom and add customerlist fields
		MobileActionGesture.flingVerticalToBottom_Android();

		// add customer fields to get last element of the customer fields
		customer_fields.addAll(AndroidLocators.findElements_With_Xpath(
				"//*[@resource-id='in.spoors.effortplus:id/formHolderEdit']/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.LinearLayout//*[contains(@class,'Text') and not(contains(@resource-id,'android:id/text1'))]"));

		// get count of elements
		custFieldsCount = customer_fields.size();
		System.out.println("**** Customer fields in the last page are **** :" + custFieldsCount);

		// retrieving the last element of customer
		custLastElement = customer_fields.get(customer_fields.size() - 1).getText();
		System.out.println("==== customer last element ==== : " + custLastElement);

		// removing elelments from list(here we only need last element after finding
		// last element we are removing elements from list)
		customer_fields.clear();

		// scroll to top
		MobileActionGesture.flingToBegining_Android();

		// add first screen elements of customer to list
		customer_fields.addAll(AndroidLocators.findElements_With_Xpath(
				"//*[@resource-id='in.spoors.effortplus:id/formHolderEdit']/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.LinearLayout//*[contains(@class,'Text') and not(contains(@resource-id,'android:id/text1'))]"));

		// get count of elements available in the first screen are
		custFieldsCount = customer_fields.size();
		System.out.println("*** customer first screen fields count *** : " + custFieldsCount);

		// scroll and add elelemnts to list until last element found
		while (!customer_fields.isEmpty()) {
			boolean flag = false;
			MobileActionGesture.verticalSwipeByPercentages(0.8, 0.2, 0.5);

			customer_fields.addAll(AndroidLocators.findElements_With_Xpath(
					"//*[@resource-id='in.spoors.effortplus:id/formHolderEdit']/android.widget.LinearLayout//android.widget.LinearLayout//*[contains(@class,'Text') and not(contains(@resource-id,'android:id/text1'))]"));

			// get the count of customer fields
			custFieldsCount = customer_fields.size();
			System.out.println("... After swiping and adding customer fields, the count is ... : " + custFieldsCount);

			// if customer last element matches element present in List then break the for
			// loop
			for (int l = 0; l < custFieldsCount; l++) {
				// printing the elements in the list
				System.out.println("*** Fields Inside loop *** : " + customer_fields.get(l).getText());

				// if list matches with last element the loop will break
				if (customer_fields.get(l).getText().contains(custLastElement)) {
					System.out.println("---- customer inside elements ---- : " + customer_fields.get(l).getText());
					flag = true;
				}
			}

			// break the loop once last element found
			if (flag == true) {
				break;
			}
		}

		MobileActionGesture.flingToBegining_Android();

		// providing input for customer fields by iterating the customer list
		for (int i = 0; i < custFieldsCount; i++) {

			String originalText = customer_fields.get(i).getText();
			String cusFieldsText = customer_fields.get(i).getText().split("\\(")[0].replaceAll("[!@#$%&*(),.?\":{}|<>]",
					"");
			System.out.println("---- Before removing special character ---- : " + originalText
					+ "\n.... After removing special character .... : " + cusFieldsText);

			if (cusFieldsText.contains("Name")) {
				MobileActionGesture.scrollUsingText(cusFieldsText);
				if (AndroidLocators.findElements_With_Xpath("//*[contains(@text,'" + cusFieldsText + "')]")
						.size() > 0) {
					CustomerPageActions.randomstringCusName = RandomStringUtils.randomAlphabetic(5).toLowerCase();
					AndroidLocators.enterTextusingID("//*[contains(@text,'" + cusFieldsText + "')]",
							CustomerPageActions.randomstringCusName);
					CustomerPageActions.randomstringCusName = AndroidLocators.returnUsingId("nameEditText").getText();
					System.out.println(
							"---- Retrieving the customer name ---- :" + CustomerPageActions.randomstringCusName);
				}
			} else if (cusFieldsText.contains("Currency") || cusFieldsText.contains("Number")) {
				MobileActionGesture.scrollUsingText(cusFieldsText);
				if (AndroidLocators.findElements_With_Xpath("//*[contains(@text,'" + cusFieldsText + "')]")
						.size() > 0) {
					//currency or number input
					curencyErrorAndWarnMeassage(cusFieldsText, currencyInput, errorCondition);
				}
			} else if (cusFieldsText.contains("Date")) {
				MobileActionGesture.scrollUsingText(cusFieldsText);
				if (AndroidLocators.findElements_With_Xpath("//*[contains(@text,'" + cusFieldsText + "')]")
						.size() > 0) {
					//date input
					dateErrorAndWarnMessageValidation(cusFieldsText, dateInput, errorCondition);
				}
			}
		}
	}
	
	public static int curencyErrorAndWarnMeassage(String cusFieldsText, String currencyInput, String errorCondition)
			throws InterruptedException, MalformedURLException, ParseException {
		
		// initializing assigning
		int currencyErrorInput = Integer.parseInt(currencyInput);

		// assigning inputdata to variable
		String getAboveOrBelowOfMainElement = Work_advanceSettings.commonMethodForInput(cusFieldsText);

		for (int j = 0; j < 3; j++) {
			MobileActionGesture.scrollUsingText(getAboveOrBelowOfMainElement);
			currencyErrorInput = currencyErrorInput - 1;
			System.out.println("------- Currency value ------ :" + currencyErrorInput);

			// based previous element inputing the main element
			Work_advanceSettings.insertInputBasedOnAboveOrBelowEle(getAboveOrBelowOfMainElement,
					String.valueOf(currencyErrorInput));
			
			
			// validating error and warning condition
			if (errorCondition.equals("Show Error when")) {
				//save button
				CustomerPageActions.saveMethod();
				// retrieving error message using OCR
				String dateText = CommonUtils.OCR();
				System.out.println("---- Expected toast message is ---- : " + dateText);
			} else if (errorCondition.equals("Show Warning when")) {
				System.out.println("------- Currency value ------ :" + currencyErrorInput);

				// based previous element inputing the main element
				Work_advanceSettings.insertInputBasedOnAboveOrBelowEle(getAboveOrBelowOfMainElement,
						String.valueOf(currencyErrorInput));

				// check if mandatory fields exist then fill those mandatory fields and validate
				// warning alerts
				MobileActionGesture.flingToBegining_Android();
				if (AndroidLocators.findElements_With_Xpath(
						"//*[@resource-id='in.spoors.effortplus:id/formHolderEdit']/android.widget.LinearLayout//android.widget.LinearLayout//*[contains(@text,'*')]")
						.size() > 0) {
					// fill mandatory fields
					CustomerPageActions.fillCustomerMandatoryFields();
					// click on save button
					CustomerPageActions.saveMethod();
					// handling alert
					FormAdvanceSettings.handlingWarningAlert();
				}
			}
			
			// increasing the currency value
			currencyErrorInput = currencyErrorInput + 2;
			System.out.println(".... After increasing the currency value .... : " + currencyErrorInput);
		}
		return currencyErrorInput;
	}

		
		
	// date error and warn meessage validation
	public static void dateErrorAndWarnMessageValidation(String workFieldsText, String dateInput, String errorCondition)
			throws ParseException, InterruptedException, MalformedURLException {

		String getAboveOrBelowOfMainElement = Work_advanceSettings.commonMethodForInput(workFieldsText);

		// date formatter
		SimpleDateFormat DateFor = new SimpleDateFormat("dd MMMM yyyy");

		java.util.Date date = new java.util.Date();
		// formating current date using date formatter
		String toDaydate = DateFor.format(date);
		// printing current date
		System.out.println("**** Todays date is **** : " + toDaydate);

		// parse the today date
		Date presentDate = DateFor.parse(toDaydate);
		// print parsing today date
		System.out.println(".... After parsing today date is ... : " + presentDate);

		// parsing given date from string
		date = DateFor.parse(dateInput);
		// formating input date into our date formatter
		String formatGivenDate = DateFor.format(date);
		// printing given date
		System.out.println("---- Given date is ---- : " + formatGivenDate);

		for (int p = 0; p < 3; p++) {
			// swipe to specified field
			MobileActionGesture.scrollUsingText(getAboveOrBelowOfMainElement);
			// Number of Days to add
			date = DateUtils.addDays(date, -1);
			// conversion of date
			String inputDate = DateFor.format(date);

			// Printing customized date
			System.out.println(" **** My given date is **** : " + inputDate);

			if (CommonUtils.getdriver()
					.findElements(MobileBy
							.xpath("//*[@text='" + workFieldsText + "']/parent::*/parent::*/android.widget.Button"))
					.size() > 0) {
				AndroidLocators.clickElementusingXPath(
						"//*[@text='" + workFieldsText + "']/parent::*/parent::*/android.widget.Button");

				// providing the given input
				Forms_basic.getCalendarDates(inputDate);
			} else {
				Work_advanceSettings.selectInputBasedOnAboveOrBelowElement(getAboveOrBelowOfMainElement);
				// providing the given input
				Forms_basic.getCalendarDates(inputDate);
			}

			// validating date based on codition
			if (errorCondition.equals("Show Error when")) {
				// click on save button
				CustomerPageActions.saveMethod();
				// retrieving error message using OCR
				String dateText = CommonUtils.OCR();
				System.out.println("---- Expected toast message is ---- : " + dateText);
			} else if (errorCondition.equals("Show Warning when")) {
				// check if mandatory fields exist then fill those mandatory fields and validate
				// warning alerts
				MobileActionGesture.flingToBegining_Android();
				if (AndroidLocators.findElements_With_Xpath(
						"//android.widget.LinearLayout[@resource-id='in.spoors.effortplus:id/formLinearLayout']/android.widget.LinearLayout/android.widget.LinearLayout[1]//*[contains(@text,'*')]")
						.size() > 0) {
					// fill mandatory fields
					CustomerPageActions.fillCustomerMandatoryFields();
					// customer save button
					CustomerPageActions.saveMethod();
					// handling alert
					FormAdvanceSettings.handlingWarningAlert();
				}
			}

			// adding new date
			date = DateUtils.addDays(date, 2);
			// format the increased date
			String newIncreasedDate = DateFor.format(date);
			// printing the increased date
			System.out.println("**** After increasing the date **** : " + newIncreasedDate);
		}
	}
	
	
	
	
	
	
	
	
	
	
	
}
