package io.dot.jyp.server.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Arrays;
import java.util.Objects;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Resource {
    private String id;

    @Enumerated(EnumType.STRING)
    private Type type;

    public static Resource of(String id, Type type) {
        return new Resource(id, type);
    }

    public static Resource of(String id, String type) {
        return of(id, Type.of(type));
    }

    public static Resource organization (String id) {
        return new Resource(id, Type.ORGANIZATION);
    }
    public boolean isOrganization () {
        return this.type.equals(Type.ORGANIZATION);
    }

    public static Resource group(String id) {
        return new Resource(id, Type.GROUP);
    }

    public boolean isGroup() {
        return this.type.equals(Type.GROUP);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resource resource = (Resource) o;
        return Objects.equals(id, resource.id) &&
                type == resource.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type);
    }

    @Schema(name = "ResourceType", enumAsRef = true)
    @Getter
    public enum Type {
        ORGANIZATION("organization"),
        GROUP("group"),
        GAME("game");

        private final String name;

        Type(String name) {
            this.name = name;
        }

        public static Type of(String name) {
            return Arrays.stream(values())
                    .filter(v -> name.equals(v.name) || name.equalsIgnoreCase(v.name))
                    .findFirst()
                    .orElseThrow(() ->
                            new IllegalArgumentException(String.format("'%s' is not supported resource type", name))
                    );
        }
    }
}