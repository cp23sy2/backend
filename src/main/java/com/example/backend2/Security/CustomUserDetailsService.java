package com.example.backend2.Security;

//import com.example.backend2.Entity.User;
import com.example.backend2.Repository.UserRepository;
import com.example.backend2.services.LdapAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String roleFromLdap = LdapAuthenticationService.getUserRole();

        List<GrantedAuthority> authorities;
        if ("staff_group".equals(roleFromLdap)) {
            authorities = Arrays.asList(new SimpleGrantedAuthority("staff_group"));
        } else if ("st_group".equals(roleFromLdap)) {
            authorities = Arrays.asList(new SimpleGrantedAuthority("st_group"));
        } else {
            throw new UsernameNotFoundException("Invalid role for user: " + username);
        }

        return new User(username, null, authorities);
    }

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User user = userRepository.findByUserEmail(username);
//        if (user == null) {
//            throw new UsernameNotFoundException("email Not found" + username);
//        }
//
//        return new org.springframework.security.core.userdetails.User(user.getUserEmail(), null, mapRolesToAuthorities(user));
//    }
//
//    private static Collection<? extends GrantedAuthority> mapRolesToAuthorities(User user) {
//        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
//        authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole()));
//        return authorities;
//    }

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User user = userRepository.findByUserEmail(username);
//        if (user == null) {
//            throw new UsernameNotFoundException("email Not found" + username);
//        }
//        return new org.springframework.security.core.userdetails.User(user.getUserEmail(), null, mapRolesToAuthorities(user));
//    }
//
//    private static Collection<? extends GrantedAuthority> mapRolesToAuthorities(User user) {
//
//        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
//        authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole()));
//        return authorities;
//    }

}
