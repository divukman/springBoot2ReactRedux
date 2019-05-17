package com.dimitar.ppmtool.services;

import com.dimitar.ppmtool.domain.User;
import com.dimitar.ppmtool.exceptions.DuplicateUsernameException;
import com.dimitar.ppmtool.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    public User saveUser(final User newUser) {

        final String username = newUser.getUsername();
        if (userRepository.findByUsername(username) != null) {
            throw new DuplicateUsernameException( username + " already exists.");
        }

        newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
        return userRepository.save(newUser);
    }

    public User getUser(final String username) {
        return userRepository.findByUsername(username);
    }

}
