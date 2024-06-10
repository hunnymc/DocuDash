package doc.backendapi.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Getter
@Setter
@Entity
@Table(name = "verifystatustype", schema = "documentdb")
public class Verifystatustype {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "StatusTypeID", nullable = false)
    private Integer id;

    @Size(max = 255)
    @Column(name = "StatusTypeName")
    private String statusTypeName;

}