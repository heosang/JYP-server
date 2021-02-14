package io.dot.jyp.server.web;

import io.dot.jyp.server.application.AccountApplicationService;
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

    private final AccountApplicationService accountApplicationService;

    public AccountController(AccountApplicationService accountApplicationService) {
        this.accountApplicationService = accountApplicationService;
    }

    @PostMapping("/signup")
    @ResponseStatus(value = HttpStatus.CREATED)
    public SignUpResponse signUp(@RequestBody final SignUpRequest request) {
        return accountApplicationService.signUp(request);
    }

    @PostMapping("/login")
    @ResponseStatus(value = HttpStatus.OK)
    public void login(@RequestBody final LoginRequest request) {
        accountApplicationService.login(request);
    }

}
