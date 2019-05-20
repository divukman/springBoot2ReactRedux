package com.dimitar.ppmtool.services;

import com.dimitar.ppmtool.domain.User;
import com.dimitar.ppmtool.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       final User user = userRepository.findByUsername(username);

       if (user == null) {
           throw new UsernameNotFoundException("User not found.");
       }

       return user;
    }

    @Transactional
    public User loadUserById(final Long id) {
        return userRepository.findById(id)
                .orElseThrow( () ->  new UsernameNotFoundException("User not found.") );
    }
}
