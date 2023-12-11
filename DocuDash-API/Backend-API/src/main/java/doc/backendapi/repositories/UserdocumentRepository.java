package doc.backendapi.repositories;

import doc.backendapi.entities.Userdocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Arrays;
import java.util.List;

public interface UserdocumentRepository extends JpaRepository<Userdocument, Integer> {
    @Query("SELECT ud FROM Userdocument ud WHERE ud.usersUserid.id = ?1")
    List<Userdocument> findByUsers_UserID(int id);

    void deleteByDocumentsDocumentid1Id(int id);
}
