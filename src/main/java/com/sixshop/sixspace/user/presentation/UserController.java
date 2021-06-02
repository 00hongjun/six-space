package com.sixshop.sixspace.user.presentation;

import com.sixshop.sixspace.user.application.UserService;
import com.sixshop.sixspace.user.presentation.dto.UserCreateRequest;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/users")
    public ResponseEntity createUser(@RequestBody UserCreateRequest request) {
        userService.createUser(request);

        URI uri = URI.create("/users/" + request.getId());
        return ResponseEntity.created(uri)
            .build();
    }

}
