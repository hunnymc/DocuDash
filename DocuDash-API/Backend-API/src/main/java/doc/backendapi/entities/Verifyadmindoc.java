package doc.backendapi.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Getter
@Setter
@Entity
@Table(name = "verifyadmindocs", schema = "documentdb")
public class Verifyadmindoc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "isPass")
    private Integer isPass;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DocumentID")
    private Document documentID;

    @Column(name = "isRead")
    private Integer isRead;

    @Size(max = 999)
    @Column(name = "comment", length = 999)
    private String comment;

}