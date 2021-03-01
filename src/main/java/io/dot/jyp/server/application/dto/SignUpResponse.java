package io.dot.jyp.server.application.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignUpResponse {
    private String nickname;

    private SignUpResponse(String nickname) {
        this.nickname = nickname;
    }

    public static SignUpResponse of(String nickname) {
        return new SignUpResponse(nickname);
    }
}
