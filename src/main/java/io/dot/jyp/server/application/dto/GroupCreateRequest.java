package io.dot.jyp.server.application.dto;

import io.dot.jyp.server.domain.Account;
import io.dot.jyp.server.domain.Authority;
import io.dot.jyp.server.domain.Diner;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GroupCreateRequest {
    private Account account;
    private Diner diner;

    @Schema(required = true, implementation = Authority.Name.class)
    private String authorityName;
}
