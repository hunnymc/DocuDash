package doc.backendapi.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@Entity
@Table(name = "verifydocs", schema = "documentdb")
public class Verifydoc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "VerifyID", nullable = false)
    private Integer id;

    @Column(name = "isPass")
    private Integer isPass;

    @Size(max = 255)
    @Column(name = "Comment")
    private String comment;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "DocumentID", nullable = false)
    private Document documentID;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "managerID", nullable = false)
    private User managerID;

    @Column(name = "isRead")
    private Integer isRead;

}