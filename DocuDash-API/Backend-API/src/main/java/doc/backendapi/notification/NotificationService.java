package doc.backendapi.notification;

import doc.backendapi.entities.Document;
import doc.backendapi.entities.Notification;
import doc.backendapi.entities.User;
import doc.backendapi.notification.dto.ReceiveNotification;
import doc.backendapi.notification.dto.ResponseMessage;
import doc.backendapi.repositories.DocumentRepository;
import doc.backendapi.repositories.NotificationRepository;
import doc.backendapi.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Collections;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class NotificationService {

    @Autowired
    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    private final NotificationRepository notificationRepository;

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final DocumentRepository documentRepository;

    @Autowired
    private final ModelMapper modelMapper;

    public void sendGlobalNotification() {
        ResponseMessage message = new ResponseMessage("Global Notification");
        messagingTemplate.convertAndSend("/topic/global-notifications", message);
    }

    public void sendPrivateNotification(final String userId) {
        ResponseMessage message = new ResponseMessage("Private Notification");
        messagingTemplate.convertAndSendToUser(userId, "/topic/private-notifications", message);
    }

    public void sendNewDocNotification(final String userId, List<ReceiveNotification> notification) {
        messagingTemplate.convertAndSendToUser(userId, "/topic/private-notifications", notification);
    }

    public void createNotification(int userId, int documentId, String message, String sourceUsername) {

        // Save the notification to the database
        Notification notification = new Notification();
        notification.setMessage(message);
        notification.setUsersUserid(userId);
        notification.setDocumentId(documentId);
        notification.setDateSent(Instant.now());
        notification.setDocTitle(documentRepository.findById(documentId).orElseThrow(() -> new RuntimeException("Document not found")).getTitle());
        notification.setSourceUsername(sourceUsername);
        notification.setReadStatus("UNREAD");

        // Save the notification to the database
        Notification newNotification = notificationRepository.save(notification);
        System.out.println("Notification saved: " + newNotification);

        // create list of notifications for specific user by user id
        List<Notification> n = notificationRepository.findByUsers_UserID(userId);

        List<ReceiveNotification> notifications = n.stream()
                .map((element) -> modelMapper.map(element, ReceiveNotification.class))
                .collect(Collectors.toList());

//        List<ReciveNotificationDto> reciveNotificationDtos = new java.util.ArrayList<>(n.stream()
//                .map((element) -> modelMapper.map(element, ReciveNotificationDto.class))
//                .toList());

        // Reverse the list to get the latest notifications at the beginning
        Collections.reverse(notifications);

        // Get the first 5 notifications from the reversed list
        List<ReceiveNotification> latestFiveNotifications = notifications.stream()
                .limit(5)
                .collect(Collectors.toList());

        // Send the notification to the user
        sendNewDocNotification(newNotification.getUsersUserid().toString(), latestFiveNotifications);
    }

    public List<ReceiveNotification> getNotificationsByUserId(int userId) {

        // Get the first 5 notifications from the reversed list
        List<Notification> n = notificationRepository.findByUsers_UserID(userId);
        List<ReceiveNotification> notifications = n.stream()
                .map((element) -> modelMapper.map(element, ReceiveNotification.class))
                .collect(Collectors.toList());

        // Reverse the list to get the latest notifications at the beginning
        Collections.reverse(notifications);
        return notifications.stream()
                .limit(5)
                .collect(Collectors.toList());
    }

}