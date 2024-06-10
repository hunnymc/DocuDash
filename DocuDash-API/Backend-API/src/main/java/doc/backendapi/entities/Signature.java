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
@Table(name = "signatures")
public class Signature {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SignatureID", nullable = false)
    private Integer id;

    @Size(max = 255)
    @Column(name = "ImagePath")
    private String imagePath;

    @Column(name = "DateCreate")
    private Instant dateCreate;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "users_UserID", nullable = false)
    private User usersUserid;

}