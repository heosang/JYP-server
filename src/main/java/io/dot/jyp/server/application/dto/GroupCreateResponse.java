package io.dot.jyp.server.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GroupCreateResponse {
    private int id;
    private String name;

    public static GroupCreateResponse of(
            int id,
            String name
    ) {
        return new GroupCreateResponse(
                id,
                name
        );
    }
}
