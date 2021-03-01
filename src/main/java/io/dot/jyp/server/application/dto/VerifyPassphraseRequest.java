package io.dot.jyp.server.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class VerifyPassphraseRequest {
    @Schema(required = true)
    @ToString.Exclude
    private String passphrase;
}
