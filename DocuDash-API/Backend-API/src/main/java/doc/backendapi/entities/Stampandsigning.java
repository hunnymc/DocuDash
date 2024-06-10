package doc.backendapi.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "stampandsignings")
public class Stampandsigning {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "StampingID", nullable = false)
    private Integer id;

    @Size(max = 255)
    @Column(name = "OperationStatus")
    private String operationStatus;

    @Column(name = "Timestamp")
    private Instant timestamp;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "documents_DocumentID", nullable = false)
    private Document documentsDocumentid;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "users_UserID", nullable = false)
    private User usersUserid;

}