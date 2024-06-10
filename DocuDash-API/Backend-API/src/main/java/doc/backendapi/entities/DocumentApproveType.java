package doc.backendapi.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "DocumentApproveType", schema = "documentdb")
public class DocumentApproveType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    //TODO [JPA Buddy] generate columns from DB
}