package com.rahadyan.rms.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rahadyan.rms.model.User;
import com.rahadyan.rms.repository.UserRepository;

/**
 * 
 * @author Rahadyan_W995
 *
 */

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    /**
	 * This method load UserDetails by user name find user by user name with
	 * UserRepository then create UserPrincipal by the user's found by user name
	 * 
	 * @return UserPrincipal created by user's information
	 */
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String usernameOrEmail)
            throws UsernameNotFoundException {
        // Let people login with either username or email
        User user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() -> 
                        new UsernameNotFoundException("User not found with username or email : " + usernameOrEmail)
        );

        return UserPrincipal.create(user);
    }

    /**
	 * This method load UserDetails by user id and this method is used by JWTAuthenticationFilter 
	 * find user by user id with UserRepository then create UserPrincipal by the user's found by user id
	 * 
	 * @return UserPrincipal created by user's information
	 */
    @Transactional
    public UserDetails loadUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(
            () -> new UsernameNotFoundException("User not found with id : " + id)
        );

        return UserPrincipal.create(user);
    }
}