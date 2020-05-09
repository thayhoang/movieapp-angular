package com.hoangmn.service;

import com.hoangmn.config.JwtUtils;
import com.hoangmn.exception.BadRequestException;
import com.hoangmn.model.ERole;
import com.hoangmn.model.Role;
import com.hoangmn.model.User;
import com.hoangmn.model.UserDetailsImpl;
import com.hoangmn.payload.JwtResponse;
import com.hoangmn.payload.LoginRequest;
import com.hoangmn.payload.SignupRequest;
import com.hoangmn.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class JwtAuthenticationService {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Value("${allow-roles}")
    private List<String> allowRoles;

    public JwtResponse authenticateUser(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> {
                    switch (item.getAuthority()) {
                        case "ROLE_ADMIN":
                            return "admin";
                        case "ROLE_MODERATOR":
                            return "mod";
                        default:
                            return "user";
                    }
                })
                .collect(Collectors.toList());

        return new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles);
    }

    public void validateRequest(SignupRequest request) throws BadRequestException {
        if (request.getUsername() == null || request.getUsername().isEmpty()) {
            throw new BadRequestException("Error: username is required");
        }

        if (request.getPassword() == null || request.getPassword().isEmpty()) {
            throw new BadRequestException("Error: password is required");
        }

        if (request.getEmail() == null || request.getEmail().isEmpty()) {
            throw new BadRequestException("Error: password is required");
        }

        if (request.getRoles() == null || request.getRoles().isEmpty()) {
            throw new BadRequestException("Error: Provide at least 1 role");
        }

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new BadRequestException("Error: Email is already in use!");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Error: Email is already in use!");
        }

        for (String role : request.getRoles()) {
            if (!allowRoles.contains(role)) {
                throw new BadRequestException("Error: Role is not allowed : " + role);
            }
        }
    }

    public User buildRegisterUser(SignupRequest request) {
        User user = new User(request.getUsername(),
                request.getEmail(),
                encoder.encode(request.getPassword()));

        Set<Role> roles = request.getRoles().stream().map(role -> {
            switch (role) {
                case "admin":
                    return new Role(ERole.ROLE_ADMIN);
                case "mod":
                    return new Role(ERole.ROLE_MODERATOR);
                default:
                    return new Role(ERole.ROLE_USER);
            }
        }).collect(Collectors.toSet());

        user.setRoles(roles);

        return user;
    }

    public void save(User registerUser) {
        userRepository.save(registerUser);
    }
}
