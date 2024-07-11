package EpicodeFS0124.core.security;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<CustomUser, Long> {

    Optional<CustomUser> findOneByUsername(String username);

    Optional<CustomUser> findByEmail(String email);

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

}
