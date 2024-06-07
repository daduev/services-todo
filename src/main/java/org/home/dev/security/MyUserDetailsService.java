package org.home.dev.security;

import org.home.dev.db.entity.Users;
import org.home.dev.db.repository.UsersRepo;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
public class MyUserDetailsService implements UserDetailsService {

    private UsersRepo usersRepo;

    public MyUserDetailsService(UsersRepo usersRepo) {
        this.usersRepo = usersRepo;
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final Users user = usersRepo.findOneByUserName(username);

        if(user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        final List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getUsersRoles().forEach(role -> {
            authorities.add( new SimpleGrantedAuthority( role.getRole() ) );
        });

        return new User(user.getUserName(), user.getPassword(), authorities);
    }
}
