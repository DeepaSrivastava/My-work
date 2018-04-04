package stepdefin;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;

public class AtitutdeTest {
	
	
	
	@Given("^I work in NHS$")
	public void I_work_in_NHS() throws Throwable {
	   System.out.println("Working in NHS");
	  
	}
	@When("^I meet ([^\"]*)$")

	public void Imeet (String str) throws Throwable{
		
		if(str.equals("someone"))
		{
	System.out.println("e-buyer homepage loaded");
		}
		if(str.equals("NewspaperBoy"))
		{
	System.out.println("e-buyer homepage loaded");
		}
	}
	

}
