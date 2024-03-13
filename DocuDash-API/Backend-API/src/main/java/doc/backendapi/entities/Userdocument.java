package doc.backendapi.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "userdocuments")
public class Userdocument {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserDocumentID", nullable = false)
    private Integer id;

    @Size(max = 45)
    @Column(name = "AccessLevel", length = 45)
    private String accessLevel;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "documents_DocumentID1", nullable = false)
    private Document documentsDocumentid1;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "users_UserID", nullable = false)
    private User usersUserid;

    @Size(max = 255)
    @Column(name = "ownerDocument")
    private String ownerDocument;

}