package doc.backendapi.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

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
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "users_UserID", nullable = false)
    private User usersUserid;

}