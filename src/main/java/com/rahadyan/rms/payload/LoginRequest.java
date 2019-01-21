package com.rahadyan.rms.payload;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author Rahadyan_W995
 *
 * This class is used to mapping data`s request from login action
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
	@NotBlank
    private String usernameOrEmail;

    @NotBlank
    private String password;
}
