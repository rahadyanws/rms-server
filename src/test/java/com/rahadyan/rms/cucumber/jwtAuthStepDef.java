package com.rahadyan.rms.cucumber;

import static org.hamcrest.Matchers.is; 
import static org.junit.Assert.assertThat;


import org.skyscreamer.jsonassert.Customization;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.RegularExpressionValueMatcher;
import org.skyscreamer.jsonassert.comparator.CustomComparator;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import com.rahadyan.rms.payload.LoginRequest;
import com.rahadyan.rms.payload.SignUpRequest;

import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class jwtAuthStepDef {
	private LoginRequest loginParam;
	
	private SignUpRequest signupParam;
	
	private TestRestTemplate restTemplate;
	
	private HttpHeaders headers;
	
	private ResponseEntity<String> response = null;
	
	@Before
	public void setUp() {
		loginParam = new LoginRequest();
		loginParam.setUsernameOrEmail("rahadyanws");
		loginParam.setPassword("123456");
		
		signupParam = new SignUpRequest();
		signupParam.setName("Sakty");
		signupParam.setUsername("sakty");
		signupParam.setEmail("sakty@gmail.com");
		signupParam.setPassword("123456");
		
		restTemplate = new TestRestTemplate();
		headers = new HttpHeaders();
	}
	
	@When("^client call /signin$")
	public void client_calls_generate_token() throws Throwable {
		
	    String url = "http://localhost:5000/rms-server" + "/api/v1/auth/signin"; 

		HttpEntity<LoginRequest> entity = new HttpEntity<LoginRequest>(loginParam, headers);

		response = restTemplate.exchange(
				url,
				HttpMethod.POST, entity, String.class);		
	}
	
	@Then("^client get response signin code (\\d+)$")
	public void client_receives_status_signin_code(int statusCode) throws Throwable {
	    assertThat("status code is incorrect : " + response.getBody(), response.getStatusCodeValue(), is(statusCode));
	}
	
	@And("^the client receives token$")
	public void the_client_receives_generated_token() throws Throwable {
		JSONAssert.assertEquals("{accessToken:token, tokenType:Bearer}", response.getBody(),  
				  new CustomComparator(
				  JSONCompareMode.STRICT, 
				  new Customization("accessToken", 
				  new RegularExpressionValueMatcher<Object>("(.+)"))));
	}
	
	@When("^client call /signup$")
	public void client_calls_register_user() throws Throwable {
	    String url = "http://localhost:5000/rms-server" + "/api/v1/auth/signup"; 

		HttpEntity<SignUpRequest> entity = new HttpEntity<SignUpRequest>(signupParam, headers);

		response = restTemplate.exchange(
				url,
				HttpMethod.POST, entity, String.class);		
	}
	
	@Then("^client get response signup code (\\d+)$")
	public void client_receives_status_signup_code(int statusCode) throws Throwable {
	    assertThat("status code is incorrect : " + response.getBody(), response.getStatusCodeValue(), is(statusCode));
	}
	
	@And("^the client receives signup$")
	public void the_client_receives_signup() throws Throwable {
		JSONAssert.assertEquals("{success:true, message:'User registered successfully'}", response.getBody(), false);
	}
}
