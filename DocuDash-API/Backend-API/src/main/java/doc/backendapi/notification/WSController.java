package doc.backendapi.notification;

import doc.backendapi.notification.dto.Message;
import doc.backendapi.notification.dto.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ws/")
public class WSController {

    @Autowired
    private WSService service;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @PostMapping("/send-message")
    public void sendMessage(@RequestBody final Message message) {
        service.notifyFrontend(message.getMessageContent());
    }

    @PostMapping("/send-private-message/{id}")
    public void sendPrivateMessage(@PathVariable final String id,
                                   @RequestBody final Message message) {
        service.notifyUser(id, message.getMessageContent());
    }

    @GetMapping("/sidebar-admin-message")
    public void sendSidebarAdminMessage() {
        notificationService.sendNewDocNotificationToAdmin();
    }

}