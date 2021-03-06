Feature: Performing sanity checklist 

@signin 
Scenario: Signin Functionality 
	Given Navigate to Home page 
	When user click on signin 
	Then verify signin validation 
	
@CustomerActivity 
Scenario: Customer Activity 
	Given Scroll to customer card and click 
		|Customers|    
	When user search for customer with 
		|Ajay Mobile|
	Then verify customer exist or not "Arjun" 
	And  verfiy customer checkin 
	And  Do customer activity and checkout 
		|Form-4|
		
@Routeplan 
Scenario: Route Activity 
	Given Scroll to route plan and click 
		|Route plans|
	When user search for route 
		|Check-out Anyway|
	Then verify customer route checkin validation 
	And perform routeactivity  
	Then complete client visit do customer checkout 
	
	
@Dayplan 
Scenario: Day plan Activity 
	Given Scroll to Day planner 
		|Day planner|
	When user click on currentday in Day plan calendar 
	Then verify day plan exist or not 
	And Do day plan activity and checkout 
		|Form-4|
		
@Workcompletion 
Scenario: Work Creation 
	Given Scroll to work "Sanity" 
	When user create work
	Then perform workaction
	
@Form 
Scenario: Form completion with allFields 
	Given Scroll to form and click 
		|Forms|
	When user click on specified form
		|AllFieldsForm new| 	
	Then fill the form to complete 
	
@Send_Debug_info
Scenario: Sending Debug information
	Given Swipe to settings
	When user click on send debug info
	Then send debug information with remarks
	
@signout
Scenario: Signout
	Given user wants to signout	

