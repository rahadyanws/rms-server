Feature: rms generate token
  I want to use this fto generate toketn

  Scenario: client call to generate token
	    When client call /signin
	    Then client get response signin code 200
			And the client receives token

	Scenario: client call to store profile data
			When client call /signup
			Then client get response signup code 201
			And the client receives signup
