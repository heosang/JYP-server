package io.dot.jyp.server.application.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GroupAdmissionResponse {
    private int id;
    private String name;

    private GroupAdmissionResponse(
            int id,
            String name) {
        this.id = id;
        this.name = name;
    }

    public static GroupAdmissionResponse of(
            int id,
            String name
    ) {
        return new GroupAdmissionResponse(
                id,
                name
        );
    }
}
