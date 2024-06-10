package doc.backendapi.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
@Table(name = "documentstatus", schema = "documentdb")
public class Documentstatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DocumentStatusID", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DocumentID")
    private Document documentID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "StatusID")
    private Verifystatustype statusID;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "DocumentApproveTypeID")
    private DocumentApproveType documentApproveTypeID;

    @Column(name = "SentToUser")
    private Integer sentToUser;

    @Column(name = "isOwnerRead")
    private Integer isOwnerRead;

}