package com.portilo.portilo.Controller;

import com.portilo.portilo.mapper.RegisterRequest;
import com.portilo.portilo.services.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Users")
public class UserController {

    private final UserService service;


    @PostMapping
    public Integer save(
            @RequestBody RegisterRequest request
            ) {
        return service.create ( request );
    }

    @GetMapping("/{user-id}")
    public ResponseEntity<RegisterRequest> findById(
            @PathVariable("user-id") Long id
    ) {
        return ResponseEntity.ok ( service.getUserById ( id ) );
    }
}