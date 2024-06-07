package org.home.dev.service.user;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.home.dev.db.entity.Users;
import org.home.dev.db.entity.UsersRoles;
import org.home.dev.db.repository.UsersRepo;
import org.home.dev.dto.user.LoginDto;
import org.home.dev.dto.user.UserDto;
import org.home.dev.exception.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class UsersServiceImpl {

    private UsersRepo usersRepo;
    private PasswordEncoder encoder;
    private AuthenticationManager authenticationManager;
    private SecurityContextRepository securityContextRepository;

    public UsersServiceImpl(UsersRepo usersRepo,
                            PasswordEncoder encoder,
                            AuthenticationManager authenticationManager,
                            SecurityContextRepository securityContextRepository) {
        this.usersRepo = usersRepo;
        this.encoder = encoder;
        this.authenticationManager = authenticationManager;
        this.securityContextRepository = securityContextRepository;
    }

    public UserDto login(LoginDto loginDto,
                         HttpServletRequest request, HttpServletResponse response) {
        UsernamePasswordAuthenticationToken token = UsernamePasswordAuthenticationToken.unauthenticated(
                loginDto.getUsername(), loginDto.getPassword());
        Authentication authentication = authenticationManager.authenticate(token);
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
        securityContextRepository.saveContext(context, request, response);
        return getUserDto();
    }

    @Transactional
    public void signup(final LoginDto loginDto) {
        boolean exists = usersRepo.existsByUserNameLikeIgnoreCase(loginDto.getUsername());
        if(exists) {
            throw new BadRequestException("Username is already exist!");
        }

        Users user = new Users();
        user.setUserName(loginDto.getUsername().toLowerCase());
        user.setPassword(encoder.encode(loginDto.getPassword()));
        usersRepo.save(user);
    }

    public boolean authenticated() {
        return SecurityContextHolder.getContext().getAuthentication().isAuthenticated();
    }

    public UserDto getUserDto() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        final Users user = usersRepo.findOneByUserName(currentUserName);
        return  mapToUsersDto(user);
    }

    public Users getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return usersRepo.findOneByUserName( authentication.getName() );
    }

    public List<UserDto> getUsers() {
        return usersRepo.findAll().stream().map(this::mapToUsersDto).collect(Collectors.toList());
    }

    private UserDto mapToUsersDto(final Users user) {
        return new UserDto(
                user.getId(),
                user.getUserName(),
                user.getPassword(),
                user.getUsersRoles().stream().map(UsersRoles::getRole).collect(Collectors.toList())
        );
    }

}
