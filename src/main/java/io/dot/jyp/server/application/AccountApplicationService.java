package io.dot.jyp.server.application;

import io.dot.jyp.server.application.dto.*;
import io.dot.jyp.server.domain.*;
import io.dot.jyp.server.domain.exception.BadRequestException;
import io.dot.jyp.server.domain.exception.ErrorCode;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Qualifier("accountService")
@Service
public class AccountApplicationService {
    private final AccountRepository accountRepository;
    private final PassphraseEncoder passphraseEncoder;
    private final PassphraseVerifier passphraseVerifier;
    private final RoleRepository roleRepository;
    private final AuthorityRepository authorityRepository;

    public AccountApplicationService(
            AccountRepository accountRepository,
            PassphraseEncoder passphraseEncoder,
            PassphraseVerifier passphraseVerifier,
            RoleRepository roleRepository, AuthorityRepository authorityRepository) {
        this.accountRepository = accountRepository;
        this.passphraseEncoder = passphraseEncoder;
        this.passphraseVerifier = passphraseVerifier;
        this.roleRepository = roleRepository;
        this.authorityRepository = authorityRepository;
    }

    @Transactional
    public void signUp(AccountSignUpRequest request) {
        this.accountRepository.existsByEmailThenThrow(request.getEmail());

        Account targetAccount = Account.signup(
                request.getEmail(),
                this.passphraseEncoder.encode(request.getPassphrase())
        );

        this.accountRepository.save(targetAccount);
    }

    @Transactional
    public void login(AccountLoginRequest request) {
        String email = request.getEmail();
        Account account = accountRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BadRequestException(String.format("user email '%s' does not exist", email), ErrorCode.EMAIL_DOES_NOT_EXIST));
        this.passphraseVerifier.validate(account.getEmail(), request.getPassphrase());
        this.accountRepository.save(account);
    }

    @Transactional
    public void changeNickname(Account account, AccountChangeNicknameRequest request) {
        account.updateNickname(request.getName());
        this.accountRepository.save(account);
    }

    @Transactional
    public void changePassphrase(Account account, AccountChangePassphraseRequest request) {
        account.updatePassphrase(this.passphraseEncoder.encode(request.getNewPassphrase()));
        this.accountRepository.save(account);
    }

    @Transactional(readOnly = true)
    public void verifyPassphrase(String email, String passphrase) {
        this.passphraseVerifier.validate(email, passphrase);
    }

}
