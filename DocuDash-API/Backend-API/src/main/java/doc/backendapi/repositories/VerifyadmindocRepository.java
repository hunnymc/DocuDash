package doc.backendapi.repositories;

import doc.backendapi.entities.Document;
import doc.backendapi.entities.Verifyadmindoc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface VerifyadmindocRepository extends JpaRepository<Verifyadmindoc, Integer> {

    @Query("SELECT vad.isPass FROM Verifyadmindoc vad WHERE vad.documentID.id = ?1")
    Integer getVerifyAdminDocByDocumentID(Integer documentID);

    void deleteByDocumentID(Document documentID);


    @Transactional
    @Modifying
    @Query("update Verifyadmindoc v set v.isPass = ?1 where v.documentID = ?2")
    int updateIsPassByDocumentID(Integer isPass, Document documentID);

    Optional<Verifyadmindoc> findByDocumentID_Id(Integer id);
}