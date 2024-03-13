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
@Table(name = "documents")
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DocumentID", nullable = false)
    private Integer id;

    @Size(max = 255)
    @Column(name = "Title")
    private String title;

    @Size(max = 255)
    @Column(name = "FilePath")
    private String filePath;

    @Column(name = "DateAdd")
    private Instant dateAdd;

    @Column(name = "DateUpdate")
    private Instant dateUpdate;

    @Size(max = 255)
    @Column(name = "Category")
    private String category;

    @Size(max = 255)
    @Column(name = "SecrecyLevel")
    private String secrecyLevel;

    @Size(max = 255)
    @Column(name = "Urgency")
    private String urgency;

    @Size(max = 255)
    @Column(name = "FromSource")
    private String fromSource;

    @Size(max = 255)
    @Column(name = "branchSource")
    private String branchSource;

    @Size(max = 255)
    @Column(name = "emailSource")
    private String emailSource;

    @Size(max = 255)
    @Column(name = "phoneSource")
    private String phoneSource;

    @Size(max = 255)
    @Column(name = "Status")
    private String status;

    @Size(max = 255)
    @Column(name = "Description")
    private String description;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "users_UserID", nullable = false)
    private User usersUserid;

}