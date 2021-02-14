package io.dot.jyp.server.domain;

import io.dot.jyp.server.domain.exception.BadRequestException;
import io.dot.jyp.server.domain.exception.ErrorCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    default void existsByEmailThenThrow(String email) {
        if (this.existsByEmail(email)) {
            throw new BadRequestException(String.format("Email '%s' is already exist", email), ErrorCode.DUPLICATED_EMAIL);
        }
    }

    boolean existsByEmail(String email);

    default void existsByNicknameThenThrow(String nickname) {
        if (this.existsByNickname(nickname)) {
            throw new BadRequestException(String.format("Nickname '%s' is already exist", nickname), ErrorCode.DUPLICATED_NICKNAME);
        }
    }

    boolean existsByNickname(String nickname);

    Optional<Account> findWithRoleByEmailAndStatus(String email, Account.Status status);

    Optional<Account> findByEmail(String email);
}
