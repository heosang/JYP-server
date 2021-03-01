package io.dot.jyp.server.application.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccountSignUpRequest {
    private String email;
    private String passphrase;
}
