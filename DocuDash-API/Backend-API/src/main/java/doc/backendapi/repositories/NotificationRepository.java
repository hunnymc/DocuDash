package doc.backendapi.repositories;

import doc.backendapi.entities.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {

    @Query("SELECT n FROM Notification n WHERE n.usersUserid = ?1")
    List<Notification> findByUsers_UserID(int user);

    // find by document id
    @Query("SELECT n FROM Notification n WHERE n.documentId = ?1")
    List<Notification> findByDocumentId(int documentId);

}