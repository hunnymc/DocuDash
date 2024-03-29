package doc.backendapi.repositories;

import doc.backendapi.entities.Document;
import doc.backendapi.entities.User;
import doc.backendapi.entities.Verifydoc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface VerifydocRepository extends JpaRepository<Verifydoc, Integer> {
    @Query("SELECT vd FROM Verifydoc vd WHERE vd.managerID = ?1")
    List<Verifydoc> findByManagerID(User manager);

    @Query("SELECT vd FROM Verifydoc vd WHERE vd.documentID = ?1")
    List<Verifydoc> findByDocumentID(Document document);

//    @Transactional
//    @Modifying
//    @Query("delete from Verifydoc v where v.documentID = ?1")
//    void DeleteBydocumentID(int documentID);

    void deleteByDocumentID (Document document);

    Optional<Verifydoc> findByDocumentID_IdAndManagerID_Id(Integer id, Integer id1);

    List<Verifydoc> findByManagerID_IdAndIsPassNotAndIsPassNot(Integer id, Integer isPass, Integer isPass1);
}