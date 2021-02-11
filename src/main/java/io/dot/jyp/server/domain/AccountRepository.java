package io.dot.jyp.server.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findWithRoleByEmailAndStatus(String email, Account.Status status);
    Optional<Account> findByEmail(String email);
}
