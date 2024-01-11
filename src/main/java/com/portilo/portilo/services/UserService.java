package com.portilo.portilo.services;

import com.portilo.portilo.mapper.RegisterRequest;
import com.portilo.portilo.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Data
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository  userRepository;
    private final RegisterRequest registerRequest;
    private final PasswordEncoder passwordEncoder ;


    public Integer create(RegisterRequest registerRequest) {
        var user = RegisterRequest.toEntity(registerRequest);
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        return userRepository.save(user).getId();
    }
   public RegisterRequest getUserById(Integer id){
        return userRepository.findById( Long.valueOf ( id ) )
                .map( registerRequest::fromEntity )
                .orElseThrow(() -> new EntityNotFoundException("No user found with the ID:: " + id));
    }


}
