package io.dot.jyp.server.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccountVerifyPassphraseRequest {
    @Schema(required = true)
    @ToString.Exclude
    private String passphrase;
}
