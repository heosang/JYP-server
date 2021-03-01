package io.dot.jyp.server.application.dto;

import io.dot.jyp.server.domain.Diner;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GameStartResponse {
    private String DinerName;
    private String DinerKind;

    public static GameStartResponse of(
            String DinerName,
            String DinerKind
    ) {
        return new GameStartResponse(
                DinerName,
                DinerKind
        );
    }
}
