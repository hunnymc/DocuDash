package doc.backendapi.repositories;

import doc.backendapi.entities.Userdocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public interface UserdocumentRepository extends JpaRepository<Userdocument, Integer> {
    @Query("SELECT ud FROM Userdocument ud WHERE ud.usersUserid.id = ?1")
    List<Userdocument> findByUsers_UserID(int id);

    @Query("SELECT ud FROM Userdocument ud WHERE ud.usersUserid.id = ?1 AND ud.isShow = ?2")
    List<Userdocument> findByUserIdAndIsShow(int id, int isShow);

    @Query("SELECT ud FROM Userdocument ud WHERE ud.documentsDocumentid1.id = ?1 AND ud.isShow = ?2")
    List<Userdocument> findByDocumentIdAndIsShow(int id, int isShow);

    @Query("SELECT ud FROM Userdocument ud WHERE ud.usersUserid.email = ?1")
    Userdocument findByUsers_Email(String email);

    void deleteByDocumentsDocumentid1Id(int id);

    Optional<Userdocument> findByDocumentsDocumentid1_IdAndIsShow(Integer id, Integer isShow);

    Optional<Userdocument> findByUsersUserid_IdAndIsShow(Integer id, Integer isShow);
}
