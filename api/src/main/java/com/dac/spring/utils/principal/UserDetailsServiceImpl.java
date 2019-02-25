package com.dac.spring.utils.principal;

import com.dac.spring.entity.EmployeeEntity;
import com.dac.spring.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        EmployeeEntity customer = employeeRepository.findByEmail(email)
        .orElseThrow(() -> new UsernameNotFoundException("No user found with email " + email));
        return UserPrincipal.build(customer);
    }
}