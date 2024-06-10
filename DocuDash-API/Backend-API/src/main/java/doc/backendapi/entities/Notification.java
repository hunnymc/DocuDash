package doc.backendapi.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "notification")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NotificationID", nullable = false)
    private Integer id;

    @Size(max = 255)
    @Column(name = "Message")
    private String message;

    @Column(name = "DateSent")
    private Instant dateSent;

    @Size(max = 255)
    @Column(name = "SecrencyLevel")
    private String secrencyLevel;

    @Size(max = 255)
    @Column(name = "Urgency")
    private String urgency;

    @NotNull
    @Column(name = "users_UserID", nullable = false)
    private Integer usersUserid;

    @NotNull
    @Column(name = "DocumentId")
    private Integer documentId;

    @Size(max = 255)
    @NotNull
    @Column(name = "read_status", nullable = false)
    private String readStatus;

    @Size(max = 255)
    @Column(name = "SourceUsername")
    private String sourceUsername;

    @Size(max = 255)
    @Column(name = "DocTitle")
    private String docTitle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "NotificationTypeID")
    private NotificationType notificationTypeID;

}