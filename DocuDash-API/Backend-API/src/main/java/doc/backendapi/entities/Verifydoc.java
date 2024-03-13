package doc.backendapi.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "verifydocs")
public class Verifydoc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "VerifyID", nullable = false)
    private Integer id;

    @Column(name = "isPass")
    private Byte isPass;

    @Size(max = 255)
    @Column(name = "Comment")
    private String comment;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "documents_DocumentID", nullable = false)
    private Document documentsDocumentid;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "users_UserID", nullable = false)
    private User usersUserid;

}