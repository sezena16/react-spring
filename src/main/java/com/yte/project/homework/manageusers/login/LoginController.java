package com.yte.project.homework.manageusers.login;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/login", method = RequestMethod.POST)
public class LoginController {

    private final LoginService loginService;

    @PostMapping
    public LoginResponse login(@Valid @RequestBody final LoginRequest loginRequest) {
        return loginService.authenticate(loginRequest);
    }
}
