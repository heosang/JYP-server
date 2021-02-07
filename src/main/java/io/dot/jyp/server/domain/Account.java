package io.dot.jyp.server.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Arrays;

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
    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    private Account(
            String email,
            String passphrase,
            String nickname,
            Status status,
            Role role
    ) {
        this.email = email;
        this.passphrase = passphrase;
        this.nickname = nickname;
        this.status = status;
        this.role = role;
    }

    private Account(
            String email,
            String passphrase,
            Status status,
            Role role
    ) {
        this.email = email;
        this.passphrase = passphrase;
        this.status = status;
        this.role = role;
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
                Role.GUEST
        );
    }

    public static Account login(
            String email,
            String passphrase
    ) {
        return new Account(
                email,
                passphrase,
                Status.ACTIVE,
                Role.GUEST
        );
    }

    public void invite() {
        this.status = Status.INVITED;
    }


    public boolean isInvited() {
        return this.status.equals(Status.INVITED);
    }

    public boolean isActive() {
        return this.status.equals(Status.ACTIVE);
    }

    public boolean isHost() {
        return this.role.equals(Role.HOST);
    }

    public boolean isGuest() {
        return this.role.equals(Role.GUEST);
    }

    @Schema(name = "AccountStatus", enumAsRef = true)
    public enum Status {
        INVITED("invited"),
        ACTIVE("active"),
        INACTIVE("inactive");

        private final String name;

        Status(String name) {
            this.name = name;
        }

        public static Status of(String name) {
            return Arrays.stream(values())
                    .filter(v -> name.equals(v.name) || name.equalsIgnoreCase(v.name))
                    .findFirst()
                    .orElseThrow(() ->
                            new IllegalArgumentException(
                                    String.format("'%s' is not supported account status", name)));
        }
    }

    @Schema(name = "AccountRole", enumAsRef = true)
    public enum Role {
        GUEST("guest"),
        HOST("host");

        private final String name;

        Role(String name) {
            this.name = name;
        }

        public static Role of(String name) {
            return Arrays.stream(values())
                    .filter(v -> name.equals(v.name) || name.equalsIgnoreCase(v.name))
                    .findFirst()
                    .orElseThrow(() ->
                            new IllegalArgumentException(
                                    String.format("'%s' is not supported account role", name)));
        }
    }
}
