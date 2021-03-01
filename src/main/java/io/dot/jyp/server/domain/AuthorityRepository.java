package io.dot.jyp.server.domain;

import io.dot.jyp.server.domain.exception.InternalServerException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, String> {
    Optional<Authority> findByName(Authority.Name name);

    List<Authority> findAllByNameIn(List<Authority.Name> names);

    List<Authority> findAllByResourceType(Resource.Type resourceType);

    default Authority findByNameOrElseThrow(Authority.Name name) {
        return this.findByName(name).orElseThrow(InternalServerException::new);
    }

    default List<Authority> findAllByNameInOrElseThrow(List<Authority.Name> names) {
        List<Authority> authorities = this.findAllByNameIn(names);
        if (authorities.size() != names.size()) {
            throw new InternalServerException("some authorities are missing");
        }
        return authorities;
    }
}
