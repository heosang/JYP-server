package io.dot.jyp.server.web;

import io.dot.jyp.server.application.AccountService;
import io.dot.jyp.server.application.dto.LoginRequest;
import io.dot.jyp.server.application.dto.SignUpRequest;
import io.dot.jyp.server.application.dto.SignUpResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/user")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/signup")
    @ResponseStatus(value = HttpStatus.CREATED)
    public SignUpResponse signUp(@RequestBody final SignUpRequest request) {
        return accountService.signUp(request);
    }

    @PostMapping("/login")
    @ResponseStatus(value = HttpStatus.OK)
    public void login(@RequestBody final LoginRequest request) {
        accountService.login(request);
    }

}
