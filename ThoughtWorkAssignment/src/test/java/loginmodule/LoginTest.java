package loginmodule;




import static org.testng.Assert.assertEquals;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;


public class LoginTest 
{
	JsonPath js;
    @BeforeMethod
    public void setBaseURL()
    {
    	RestAssured.baseURI = "https://reqres.in";
    	
    }
    @Test
    public void successlogin() {
    	
    	given().header("Content-Type", "application/json")
    	.body(Payload.Payload("eve.holt@reqres.in", "cityslicka")).when().log().all().post("/api/login").then().log().all().assertThat().statusCode(200);
    	
    }
    
    
    @Test
    public void verifyToken() {
    	
    	String response = given().header("Content-Type", "application/json")
    	.body(Payload.Payload("eve.holt@reqres.in", "cityslicka")).when().post("/api/login").then().extract().response().asString();
    	js = new JsonPath(response);
    	
    	System.out.println("Token value is "+js.getString("token"));
    		assertEquals("QpwL5tke4Pnpja7X4", js.getString("token"), "Token value is correct");
    	
    }
//blankusername
    @Test
    public void VerifyStatusAndResponseBlankUserName() {
    	
    	String response = given().header("Content-Type", "application/json")
    	    	.body(Payload.Payload("", "cityslicka")).when().log().all().post("/api/login").then().log().all().assertThat().statusCode(400).extract().response().asString();
    	js = new JsonPath(response);
    	
    	
    	System.out.println("Error message is "+js.getString("error"));
    	assertEquals("Missing email or username", js.getString("error"), "response is not matched as expected");
    	
    } 

    @Test
    public void VerifyStatusAndResponseBlankPassword() {
    	
    	String response = given().header("Content-Type", "application/json")
    	    	.body(Payload.Payload("eve.holt@reqres.in", "")).when().log().all().post("/api/login").then().log().all().assertThat().statusCode(400).extract().response().asString();
    	js = new JsonPath(response);
    	
    	
    	System.out.println("Error message is "+js.getString("error"));
    	assertEquals("Missing password", js.getString("error"), "response is not matched as expected");
    	
    } 
    
    @Test
    public void VerifyStatusAndResponseInvalidUserName() {
    	
    	String response = given().header("Content-Type", "application/json")
    	    	.body(Payload.Payload("testunique", "cityslicka")).when().log().all().post("/api/login").then().log().all().assertThat().statusCode(400).extract().response().asString();
    	js = new JsonPath(response);
    	
    	
    	System.out.println("Error message is "+js.getString("error"));
    	assertEquals("user not found", js.getString("error"), "response is not matched as expected");
    	
    } 

    @Test
    public void VerifyNovalidationforInvalidpassword() {
    	
    	String response = given().header("Content-Type", "application/json")
    	.body(Payload.Payload("eve.holt@reqres.in", "invaliduniquepassword")).when().log().all().post("/api/login").then().log().all().assertThat().statusCode(200).extract().response().asString();
    	
    	
js = new JsonPath(response);
    	
    	System.out.println("Token value is "+js.getString("token"));
    		assertEquals("QpwL5tke4Pnpja7X4", js.getString("token"), "Token value is correct");
    	
    } 
    
}
