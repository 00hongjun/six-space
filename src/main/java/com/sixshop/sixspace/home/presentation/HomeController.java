package com.sixshop.sixspace.home.presentation;

import com.sixshop.sixspace.home.presentation.dto.VacationRequest;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
// TODO : api 규격 맞추기, 슬랙으로 넘어오는 유저는 어떻게 구별할지
// 네이밍 createVacationRequest 등
@RestController
@RequestMapping("/users/{slack-user-id}/vacation")
public class HomeController {

    @GetMapping("/")
    public ResponseEntity hello() {
        return ResponseEntity.ok("hello world");
    }

    @PostMapping
    public ResponseEntity foo(@PathVariable("slack-user-id") long slackUserId, @RequestBody @Valid final VacationRequest request) {
        return ResponseEntity.ok("hello world");
    }
}
