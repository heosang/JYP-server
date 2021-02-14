package io.dot.jyp.server.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(
        name = "authorities",
        uniqueConstraints = @UniqueConstraint(name = "uk_authorities_name", columnNames = "name")
)
@Getter
@Setter
@NoArgsConstructor
public class Authority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", nullable = false)
    @Enumerated(EnumType.STRING)
    private Name name;

    @Column(name = "resource_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private Resource.Type resourceType;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @JoinTable(
            name = "authority_permissions",
            joinColumns = @JoinColumn(name = "authority_id"),
            foreignKey = @ForeignKey(name = "fk_authority_id"),
            indexes = @Index(name = "authority_permissions_authority_id", columnList = "authority_id")
    )
    @Column(name = "permission", nullable = false)
    private List<Permission> permissions;

    public Authority(Name name, List<Permission> permissions) {
        if ((int) permissions.stream().map(Permission::getTarget).distinct().count() != 1) {
            throw new IllegalArgumentException("permissions should have all same targets");
        }
        this.name = name;
        this.resourceType = name.getTarget();
        this.permissions = permissions;
    }

    public boolean hasSameName(Authority authority) {
        return this.name.equals(authority.name);
    }

    public boolean hasPermission(Permission permission) {
        return this.permissions.stream()
                .anyMatch(p -> p.equals(permission));
    }

    public boolean hasSamePermissions(Authority authority) {
        return this.permissions.containsAll(authority.getPermissions())
                && authority.getPermissions().containsAll(this.permissions);
    }

    public void updatePermissions(List<Permission> permissions) {
        this.permissions.removeIf(permission -> !permissions.contains(permission));
        this.permissions.addAll(
                permissions.stream()
                        .filter(permission -> !this.permissions.contains(permission))
                        .collect(Collectors.toList())
        );
    }

    @Schema(name = "AuthorityName", enumAsRef = true)
    @Getter
    public enum Name {
        ORGANIZATION_ADMIN("ORGANIZATION_ADMIN", Resource.Type.ORGANIZATION),
        ORGANIZATION_MANAGER("ORGANIZATION_MANAGER", Resource.Type.ORGANIZATION),
        ORGANIZATION_USER("ORGANIZATION_USER", Resource.Type.ORGANIZATION),

        GROUP_HOST("GROUP_HOST", Resource.Type.GROUP),
        GROUP_GUEST("GROUP_GUEST", Resource.Type.GROUP),

        GAME_JUDGE("GAME_JUDGE", Resource.Type.GAME),
        GAME_JUROR("GAME_JUROR", Resource.Type.GAME),
        GAME_VIEWER("GAME_VIEWER", Resource.Type.GAME);

        private final String name;
        private final Resource.Type target;

        Name(String name, Resource.Type target) {
            this.name = name;
            this.target = target;
        }

        public static Name of(String name) {
            return Arrays.stream(values())
                    .filter(v -> name.equals(v.name) || name.equalsIgnoreCase(v.name))
                    .findFirst()
                    .orElseThrow(() ->
                            new IllegalArgumentException(String.format("'%s' is not supported authority name", name))
                    );
        }

        public static List<Authority.Name> getPredefinedClientAuthorities() {
            return Arrays.asList(
                    ORGANIZATION_ADMIN,
                    ORGANIZATION_MANAGER,
                    ORGANIZATION_USER
            );
        }

        public static List<Authority.Name> getPredefinedGroupAuthorities() {
            return Arrays.asList(
                    GROUP_HOST,
                    GROUP_GUEST
            );
        }

        public static List<Authority.Name> getPredefinedGameAuthorities() {
            return Arrays.asList(
                    GAME_JUDGE,
                    GAME_JUROR,
                    GAME_VIEWER
            );
        }


    }
}