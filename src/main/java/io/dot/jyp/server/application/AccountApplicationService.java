package io.dot.jyp.server.application;

import io.dot.jyp.server.application.dto.LoginRequest;
import io.dot.jyp.server.application.dto.SignUpRequest;
import io.dot.jyp.server.application.dto.SignUpResponse;
import io.dot.jyp.server.domain.Account;
import io.dot.jyp.server.domain.AccountRepository;
import io.dot.jyp.server.domain.PassphraseEncoder;
import io.dot.jyp.server.domain.PassphraseVerifier;
import io.dot.jyp.server.domain.exception.BadRequestException;
import io.dot.jyp.server.domain.exception.ErrorCode;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Qualifier("accountService")
@Service
public class AccountApplicationService {
    private final AccountRepository accountRepository;
    private final PassphraseEncoder passphraseEncoder;
    private final PassphraseVerifier passphraseVerifier;

    public AccountApplicationService(
            AccountRepository accountRepository,
            PassphraseEncoder passphraseEncoder,
            PassphraseVerifier passphraseVerifier
    ) {
        this.accountRepository = accountRepository;
        this.passphraseEncoder = passphraseEncoder;
        this.passphraseVerifier = passphraseVerifier;
    }

    @Transactional
    public SignUpResponse signUp(SignUpRequest request) {
        this.accountRepository.existsByEmailThenThrow(request.getEmail());
        this.accountRepository.existsByNicknameThenThrow(request.getNickname());

        Account account = Account.signup(
                request.getEmail(),
                this.passphraseEncoder.encode(request.getPassphrase()),
                request.getNickname());

        this.accountRepository.save(account);
        return SignUpResponse.of(request.getNickname());
    }

    @Transactional
    public void login(LoginRequest request) {
        String email = request.getEmail();
        Account account = accountRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BadRequestException(String.format("user email '%s' does not exist", email), ErrorCode.EMAIL_DOES_NOT_EXIST));
        this.passphraseVerifier.validate(account.getEmail(), request.getPassphrase());
        this.accountRepository.save(account);
    }
}
