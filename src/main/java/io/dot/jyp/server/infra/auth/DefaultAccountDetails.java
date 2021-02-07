package io.dot.jyp.server.infra.auth;

import io.dot.jyp.server.domain.Account;
import io.dot.jyp.server.domain.exception.AuthenticationException;
import lombok.Getter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.Collection;

@Getter
public class DefaultAccountDetails implements UserDetails {
    private final Account account;
    private final GrantedAuthority grantedAuthorities;

    public DefaultAccountDetails(Account account, GrantedAuthority grantedAuthorities) {
        this.account = account;
        this.grantedAuthorities = grantedAuthorities;
    }

    public static DefaultAccountDetails of(Account account) {
        return new DefaultAccountDetails(
                account,
                new SimpleGrantedAuthority(
                        String.format("%s", account.getRole())
                )
        );
    }

    public static DefaultAccountDetails fromAuthentication(Authentication authentication) {
        if (authentication == null) {
            throw new AuthenticationException("authentication error");
        }
        if (!(authentication.getPrincipal() instanceof DefaultAccountDetails)) {
            throw new AuthenticationException("authentication error");
        }
        return (DefaultAccountDetails) authentication.getPrincipal();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return this.account.getPassphrase();
    }

    @Override
    public String getUsername() {
        return this.account.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}