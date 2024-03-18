package doc.backendapi.repositories;

import doc.backendapi.entities.Document;
import doc.backendapi.entities.User;
import doc.backendapi.entities.Verifydoc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VerifydocRepository extends JpaRepository<Verifydoc, Integer> {
    @Query("SELECT vd FROM Verifydoc vd WHERE vd.managerID = ?1")
    List<Verifydoc> findByManagerID(User manager);

    @Query("SELECT vd FROM Verifydoc vd WHERE vd.documentID = ?1")
    List<Verifydoc> findByDocumentID(Document document);

}