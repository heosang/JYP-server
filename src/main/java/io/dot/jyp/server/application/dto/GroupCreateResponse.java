package io.dot.jyp.server.application.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GroupCreateResponse {
    private String title;
    private String link;

    private GroupCreateResponse(
            String title,
            String link) {
        this.title = title;
        this.link = link;
    }

    public static GroupCreateResponse of(
            String title,
            String link
    ) {
        return new GroupCreateResponse(
                title,
                link
        );
    }
}
