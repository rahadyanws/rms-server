package com.rahadyan.rms.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author Rahadyan_W995
 *
 * This class used to be the response of the application
 * status to return the response status (e.g. 200, 401, etc)
 * message to return the response message (e.g. success, Invalid Username or Password!, etc)
 * and use JWTAuthenticationResponse for authentication response
 * 
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse {
	private Boolean success;
    private String message;
}
