Feature: Testing customer module 

@Customer_creation 
Scenario: Customer creation 
	Given verify customer card exist or not "Customers" 
	When user create a customer 
	Then validate customer is created or not
	And modify the customer
	
@Customer_Min_Max_validations 
Scenario: Customer min max validation 
	Given Go to customer page "Customers" 
	When user enter min value 5 and max value 9 and check validations 
	
@Customer_regularExpression_Testing 
Scenario: Customer RegularExpression 
	Given Move to customer page "Customers" 
	When user enter the regular expression "[0-9a-zA-Z]" then validate customer fields 
	
@Customer_FieldDependencyBasedOnOtherFields 
Scenario Outline: Work Field Dependency Based on Values in Other Fields 
	Given fling to customer "<customer>" 
	When user enters the basecondition "<ConditionName>" for the customer dependencyfield "<fieldType>" the input is "<inputValue>" 
	Examples: 
		|customer|ConditionName|fieldType|inputValue|
		|Customers|Hide when|Customer|charan,Tata|
		
@Highlighting_Background_Fields_Based_on_Dependent_value 
Scenario Outline: Customer Highlighting Background of a Field Based on Dependent value 
	Given Swipe to customer screen "<customer>" 
	When user enters value "<fieldvalue>" for the dependent field "<fieldType>" 
	Examples: 
		|customer|fieldvalue|fieldType|
		|Customers|10|Currency|

@Customer_Validate_Based_on_Values_in_Other_Fields 
Scenario Outline: Customer Validate Based on Values in OtherFields 
	Given Scroll to customer screen "<customer>" 
	When user gives the condition as "<errorCondition>" and provides the input as "<inputValue>" 
	Examples: 
		|customer|errorCondition|inputValue|
		|Customers|Show Error when|10,18 Feb 2021|
				
	