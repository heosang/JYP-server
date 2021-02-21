package io.dot.jyp.server.application.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GroupEntranceResponse {
    private int id;
    private String name;

    private GroupEntranceResponse(
            int id,
            String name) {
        this.id = id;
        this.name = name;
    }

    public static GroupEntranceResponse of(
            int id,
            String name
    ) {
        return new GroupEntranceResponse(
                id,
                name
        );
    }
}
