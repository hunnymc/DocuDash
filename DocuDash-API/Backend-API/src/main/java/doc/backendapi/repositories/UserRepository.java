package doc.backendapi.repositories;

import doc.backendapi.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail (String email);

    Optional<User> findByUsername (String username);

    @Query("SELECT u.id FROM User u WHERE u.email = ?1")
    Optional<Integer> findIdByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);
}
