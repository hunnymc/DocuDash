package doc.backendapi.notification.dto;

import doc.backendapi.DTO.UserDto;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.Instant;

/**
 * DTO for {@link doc.backendapi.entities.Notification}
 */
@Getter
@Setter
@NoArgsConstructor
public class NotificationDto {

    Integer id;

    @Size(max = 255)
    String message;

    Instant dateSent;

    @NotNull
    Integer usersUseridId;

}