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
@Table(name = "taskdocusers")
public class Taskdocuser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "verifydoc", nullable = false)
    private Integer id;

    @Size(max = 255)
    @Column(name = "TaskTitle")
    private String taskTitle;

    @Size(max = 255)
    @Column(name = "Description")
    private String description;

    @Column(name = "DateCreate")
    private Instant dateCreate;

    @Column(name = "DateUpdate")
    private Instant dateUpdate;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "documents_DocumentID1", nullable = false)
    private Document documentsDocumentid1;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "users_UserID", nullable = false)
    private User usersUserid;

}