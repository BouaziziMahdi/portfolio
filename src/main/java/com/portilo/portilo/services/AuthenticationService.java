package com.portilo.portilo.services;
import com.portilo.portilo.Entity.Role;
import com.portilo.portilo.Entity.TypeRole;
import com.portilo.portilo.Entity.User;
import com.portilo.portilo.mapper.AuthenticationRequest;
import com.portilo.portilo.mapper.AuthenticationResponse;
import com.portilo.portilo.mapper.RegisterRequest;
import com.portilo.portilo.repository.RoleRepository;
import com.portilo.portilo.repository.UserRepository;
import com.portilo.portilo.security.JwtService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository useruserRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RoleRepository roleRepository;


    @Transactional
    public AuthenticationResponse register(RegisterRequest request) {

        var user = User.builder()
                .firstName(request.getFirstname ())
                .lastName(request.getLastname())
                .email(request.getEmail())
                .password(
                        passwordEncoder.encode(request.getPassword())
                )
                .build();
        // set roles
        var userRole = roleRepository.findByName( TypeRole.USER.name())
                .orElse(
                        Role.builder()
                                .name(TypeRole.USER.name())
                                .build()
                );
        if (userRole.getId() == null) {
            userRole = roleRepository.save(userRole);
        }
        var defaultUserRole = List.of(userRole);
        user.setRoles(defaultUserRole);
        var savedUser = useruserRepository.save(user);

        userRole.setUsers(new ArrayList<> (List.of(savedUser)));
        roleRepository.save(userRole);
        var claims = new HashMap<String, Object> ();
        claims.put("role", user.getRoles());

        var jwtToken = jwtService.generateToken(savedUser, claims);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .userId(String.valueOf(savedUser.getId()))
                .username(savedUser.getFirstName() + " " + savedUser.getLastName())
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken (
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = useruserRepository.findByEmail(request.getEmail())
                .orElseThrow();
        var claims = new HashMap<String, Object>();
        claims.put("role", user.getRoles());
        var jwtToken = jwtService.generateToken(user, claims);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .userId(String.valueOf(user.getId()))
                .username(user.getFirstName() + " " + user.getLastName())
                .build();
    }
}