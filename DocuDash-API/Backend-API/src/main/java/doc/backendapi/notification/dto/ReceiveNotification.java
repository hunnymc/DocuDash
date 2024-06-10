package doc.backendapi.notification.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.Instant;

/**
 * DTO for {@link doc.backendapi.entities.Notification}
 */
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReceiveNotification {
    Integer id;

    @Size(max = 255)
    String message;

    Instant dateSent;

    @NotNull
    @Size(max = 255)
    String readStatus;

    @Size(max = 255)
    String sourceUsername;

    @Size(max = 255)
    String docTitle;

    Integer documentId;

    Integer notificationTypeID;

    Integer isRead;

}