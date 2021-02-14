package io.dot.jyp.server.domain;

import io.dot.jyp.server.domain.exception.AuthenticationException;
import io.dot.jyp.server.domain.exception.BadRequestException;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "accounts")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "passphrase", nullable = false)
    private String passphrase;

    @Column(name = "nickname", nullable = false)
    private String nickname;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "account_id", foreignKey = @ForeignKey(name = "fk_account_id"))
    private List<Role> roles = new ArrayList<>();

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "created_at")
    private LocalDateTime created_at;

    private Account(
            String email,
            String passphrase,
            String nickname,
            Status status,
            List<Role> roles
    ) {
        this.email = email;
        this.passphrase = passphrase;
        this.nickname = nickname;
        this.status = status;
        this.addRoles(roles);
    }

    private Account(
            String email,
            String passphrase,
            String nickname,
            Status status,
            LocalDateTime created_at
    ) {
        this.email = email;
        this.passphrase = passphrase;
        this.nickname = nickname;
        this.status = status;
        this.created_at = created_at;
    }

    private Account(
            String name,
            String email,
            String passphrase,
            Status status,
            List<Role> roles,
            LocalDateTime created_at
    ) {
        this.email = email;
        this.passphrase = passphrase;
        this.nickname = name;
        this.status = status;
        this.addRoles(roles);
        this.created_at = created_at;
    }

    public static Account signup(
            String email,
            String passphrase,
            String nickname
    ) {
        return new Account(
                email,
                passphrase,
                nickname,
                Status.ACTIVE,
                LocalDateTime.now(ZoneId.of("Asia/Seoul"))
        );
    }

    public static Account active(
            String email,
            String passphrase,
            String nickname,
            List<Role> roles
    ) {
        return new Account(
                email,
                passphrase,
                nickname,
                Status.ACTIVE,
                roles
        );
    }

    public boolean isAdmin() {
        return this.roles.stream().anyMatch(Role::isAdmin);
    }

    private void checkRoleAddable(Role role) {
        if (this.getMatchedRole(role.getResource(), role.getAuthority().getName()).isPresent()) {
            throw new BadRequestException(String.format(
                    "account '%s' already has role of authority '%s' at '%s' '%s'",
                    this.getId(),
                    role.getAuthority().getName(),
                    role.getResource().getType(),
                    role.getResource().getId()
            ));
        }
        if (role.getResource().isGroup() && this.hasAnyRoleOf(role.getResource())) {
            throw new BadRequestException(
                    String.format("account '%s' already has role of group '%s'", this.getId(), role.getResource().getId())
            );
        }
    }

    public boolean hasAnyRoleOf(Resource resource) {
        return this.roles.stream()
                .anyMatch(role -> role.getResource().equals(resource));
    }

    public void addRole(Role role) {
        this.checkRoleAddable(role);
        role.setAccount(this);
        this.roles.add(role);
    }

    public void addRoles(List<Role> newRoles) {
        newRoles.forEach(this::addRole);
    }

    public boolean isInvited() {
        return this.status.equals(Status.INVITED);
    }

    public boolean isActive() {
        return this.status.equals(Status.ACTIVE);
    }

    public boolean isDeleted() {
        return this.status.equals(Status.DELETED);
    }

    public Account deleteAccount(Account account) {
        if (this.equals(account)) {
            throw new BadRequestException("you cannot delete yourself");
        }

        if (this.isAdmin()) {
            throw new AuthenticationException("there is no authority");
        }

        if (!this.isActive()) {
            throw new BadRequestException(String.format("account '%s' is not active", this.getId()));
        }
        account.delete();
        return account;
    }

    private void delete() {
        if (!this.isActive()) {
            throw new BadRequestException("you can delete only active account");
        }

        this.status = Status.DELETED;
        this.passphrase = null;
        this.roles.clear();
    }

    public void updateName(String nickname) {
        this.nickname = nickname;
    }

    public void updatePassphrase(String passphrase) {
        this.passphrase = passphrase;
    }

    public Optional<Role> getMatchedRole(Resource resource, Authority.Name authorityName) {
        return this.roles.stream()
                .filter(role -> role.isSameResource(resource) && role.isSameAuthority(authorityName))
                .findFirst();
    }

    public void deleteMatchedRole(Resource resource, Authority.Name authorityName) {
        this.roles.removeIf(role ->
                role.isSameResource(resource) && role.isSameAuthority(authorityName)
        );
    }

    @Schema(name = "AccountStatus", enumAsRef = true)
    public enum Status {
        INVITED("invited"),
        ACTIVE("active"),
        DELETED("deleted");

        private final String name;

        Status(String name) {
            this.name = name;
        }

        public static Account.Status of(String name) {
            return Arrays.stream(values())
                    .filter(v -> name.equals(v.name) || name.equalsIgnoreCase(v.name))
                    .findFirst()
                    .orElseThrow(() ->
                            new IllegalArgumentException(
                                    String.format("'%s' is not supported account status", name)));
        }
    }
}
