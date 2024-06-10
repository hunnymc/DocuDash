package doc.backendapi.repositories;


import doc.backendapi.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail (String email);

    @Query("SELECT u.id FROM User u WHERE u.username = ?1")
    Integer findIdByUsername(String username);

    Optional<User> findByUsername (String username);

    @Query("SELECT u.id FROM User u WHERE u.email = ?1")
    Optional<Integer> findIdByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByIdAndRoleNot(Integer id, String role);

    List<User> findUserByEmail(String email);

    @Transactional
    @Modifying
    @Query("update User u set u.fullName = ?1 where u.id = ?2")
    int updateFullNameById(String fullName, Integer id);

    @Transactional
    @Modifying
    @Query("update User u set u.email = ?1 where u.id = ?2")
    int updateEmailById(String email, Integer id);

    @Transactional
    @Modifying
    @Query("update User u set u.phone = ?1 where u.id = ?2")
    int updatePhoneById(String phone, Integer id);
}
