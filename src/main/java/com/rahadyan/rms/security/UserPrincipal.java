package com.rahadyan.rms.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rahadyan.rms.model.User;

import lombok.AllArgsConstructor;
import lombok.Data;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 
 * @author Rahadyan_W995
 *
 */

@Data
@AllArgsConstructor
public class UserPrincipal implements UserDetails {
	private static final long serialVersionUID = 6222274755446527931L;

	private Long id;

    private String name;

    private String username;

    @JsonIgnore
    private String email;

    @JsonIgnore
    private String password;

    private Collection<? extends GrantedAuthority> authorities;

    /**
     * This method create the UserPrincipal by User object.
     * @param user user's information that will be used to create UserPrincipal set authorities by user role set user information into UserPrincipal
     * @return the UserPrincipal with user information
     */
    public static UserPrincipal create(User user) {
        List<GrantedAuthority> authorities = user.getRoles().stream().map(role ->
                new SimpleGrantedAuthority(role.getName().name())
        ).collect(Collectors.toList());

        return new UserPrincipal(
                user.getId(),
                user.getName(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                authorities
        );
    }

    /**
	 * Indicates whether the user's account has expired. An expired account cannot beauthenticated
	 * 
	 * @return true  if the user's account is valid (ie non-expired)
	 * @return false if no longer valid (ie expired)
	 */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
	 * Indicates whether the user is locked or unlocked. A locked user cannot beauthenticated
	 * 
	 * @return true  if the user is not locked
	 * @return false otherwise
	 */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
	 * Indicates whether the user's credentials (password) has expired. Expiredcredentials prevent authentication
	 * 
	 * @return true  if the user's credentials are valid (ie non-expired)
	 * @return false if no longer valid (ie expired)
	 */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
	 * Indicates whether the user's credentials (password) has expired. Expiredcredentials prevent authentication
	 * 
	 * @return true if the user is enabled
	 * @return false otherwise
	 */
    @Override
    public boolean isEnabled() {
        return true;
    }
}