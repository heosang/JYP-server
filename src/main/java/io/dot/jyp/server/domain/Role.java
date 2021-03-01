package io.dot.jyp.server.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "roles", indexes = {
        @Index(name = "roles_account_id", columnList = "account_id"),
})
@Getter
@Setter
@NoArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "authority_id", foreignKey = @ForeignKey(name = "fk_authority_id"), nullable = false)
    private Authority authority;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "id", column = @Column(name = "resource_id", nullable = false)),
            @AttributeOverride(name = "type", column = @Column(name = "resource_type", nullable = false))
    })
    private Resource resource;

    @ManyToOne(fetch = FetchType.LAZY)
    private Account account;

    private Role(Authority authority, Resource resource) {
        if (!authority.getResourceType().equals(resource.getType())) {
            throw new IllegalArgumentException("unmatched resource type");
        }
        this.authority = authority;
        this.resource = resource;
    }

    public static Role of(Authority authority, Resource resource) {
        return new Role(authority, resource);
    }

    public static String formatToGrantedAuthority(Resource resource, Permission permission) {
        return String.format("%s_%s_%s", resource.getType(), resource.getId(), permission);
    }

    public List<String> getGrantedAuthorities() {
        return this.authority.getPermissions()
                .stream()
                .map(permission -> formatToGrantedAuthority(this.resource, permission))
                .collect(Collectors.toList());
    }

    public boolean isAdmin() {
        return this.isSameAuthority(Authority.Name.ORGANIZATION_ADMIN);
    }

    public boolean isManager() {
        return this.isSameAuthority(Authority.Name.ORGANIZATION_MANAGER);
    }

    public boolean isUser() {
        return this.isSameAuthority(Authority.Name.ORGANIZATION_USER);
    }

    public boolean isGroup() {
        return Authority.Name.getPredefinedGroupAuthorities()
                .stream()
                .anyMatch(this::isSameAuthority);
    }

    public boolean isSameAuthority(Authority.Name name) {
        return this.authority.getName().equals(name);
    }

    public boolean isSameResource(Resource resource) {
        return this.resource.equals(resource);
    }

    public boolean hasPermission(Permission permission) {
        return this.authority.hasPermission(permission);
    }
}
