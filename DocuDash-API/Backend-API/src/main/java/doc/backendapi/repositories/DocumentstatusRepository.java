package doc.backendapi.repositories;

import doc.backendapi.entities.Document;
import doc.backendapi.entities.Documentstatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface DocumentstatusRepository extends JpaRepository<Documentstatus, Integer> {

    @Query("SELECT ds.id FROM Documentstatus ds WHERE ds.statusID = ?1")
    Integer getStatusId(String status);

    // DocumentstatusRepository.java
    @Query("SELECT ds.statusID.id FROM Documentstatus ds WHERE ds.documentID.id = ?1")
    Integer getStatusByDocumentId(Integer documentId);

    // deleteByDocumentId
    @Modifying
    @Query("DELETE FROM Documentstatus ds WHERE ds.documentID.id = ?1")
    void deleteByDocumentId(Integer documentId);

    @Query("SELECT ds.documentApproveTypeID.id FROM Documentstatus ds WHERE ds.documentID.id = ?1")
    Integer getApproveTypeByDocumentId(Integer id);

    @Query("SELECT ds FROM Documentstatus ds WHERE ds.documentID = ?1")
    Documentstatus findByDocumentID(Document document);

    boolean existsByDocumentID_Id(Integer id);
}