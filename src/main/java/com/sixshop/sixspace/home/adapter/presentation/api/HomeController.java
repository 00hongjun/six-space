package com.sixshop.sixspace.home.adapter.presentation.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public ResponseEntity hello() {
        return ResponseEntity.ok("hello world");
    }

}
