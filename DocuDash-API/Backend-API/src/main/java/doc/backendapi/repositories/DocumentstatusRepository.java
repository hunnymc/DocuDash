package doc.backendapi.repositories;

import doc.backendapi.entities.Document;
import doc.backendapi.entities.Documentstatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.Instant;
import java.util.List;

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

//    int countByDocumentID_DateAdd(Instant dateAdd);

    // count document in the same day by dateAdd
    @Query("SELECT COUNT(ds) FROM Documentstatus ds WHERE YEAR(ds.documentID.dateAdd) = YEAR(?1) AND MONTH(ds.documentID.dateAdd) = MONTH(?1) AND DAY(ds.documentID.dateAdd) = DAY(?1)")
    int countByDocumentID_DateAdd(Instant dateAdd);

    int countByStatusID_Id(Integer id);

    List<Documentstatus> findByDocumentID_UsersUserid_Id(Integer id);

    int countByDocumentID_UsersUserid_IdAndStatusID_Id(Integer id, Integer id1);

    @Query("SELECT COUNT(ds) FROM Documentstatus ds WHERE ds.documentID.usersUserid.id = ?1 AND ds.statusID.id = 3 AND ds.isOwnerRead = 0")
    int countUserWaiting(Integer id);

    @Query("SELECT COUNT(ds) FROM Documentstatus ds WHERE ds.documentID.usersUserid.id = ?1 AND ds.isOwnerRead = 0 AND (ds.statusID.id = 2 OR ds.statusID.id = 3 OR ds.statusID.id = 5)")
    int countUserAll(Integer id);

    @Query("SELECT COUNT(ds) FROM Documentstatus ds WHERE ds.documentID.usersUserid.id = ?1 AND ds.statusID.id = 4 AND ds.isOwnerRead = 0")
    int countUserDone(Integer id);

    @Query("SELECT COUNT(ds) FROM Documentstatus ds WHERE ds.documentID.usersUserid.id = ?1 AND ds.statusID.id = 5 AND ds.isOwnerRead = 0")
    int countUserRejected(Integer id);
}