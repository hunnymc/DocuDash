package doc.backendapi.repositories;

import doc.backendapi.entities.Notification;
import doc.backendapi.entities.NotificationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {

    @Query("SELECT n FROM Notification n WHERE n.usersUserid = ?1")
    List<Notification> findByUsers_UserID(int user);

    // find by document id
    @Query("SELECT n FROM Notification n WHERE n.documentId = ?1")
    List<Notification> findByDocumentId(int documentId);

    @Transactional
    @Modifying
    @Query("""
            update Notification n set n.readStatus = ?1
            where n.usersUserid = ?2 and n.documentId = ?3 and n.notificationTypeID = ?4""")
    int updateReadStatusByUsersUseridAndDocumentIdAndNotificationTypeID(String readStatus, Integer usersUserid, Integer documentId, NotificationType notificationTypeID);
}