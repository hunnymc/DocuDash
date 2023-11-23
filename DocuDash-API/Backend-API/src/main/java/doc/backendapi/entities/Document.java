package doc.backendapi.entities;

import jakarta.persistence.*;
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

    @Column(name = "Title")
    private String title;

    @Column(name = "FilePath")
    private String filePath;

    @Column(name = "DateAdd")
    private Instant dateAdd;

    @Column(name = "DateUpdate")
    private Instant dateUpdate;

    @Column(name = "Category")
    private String category;

    @Column(name = "SecrecyLevel")
    private String secrecyLevel;

    @Column(name = "Urgency")
    private String urgency;

    @Column(name = "FromSource")
    private String fromSource;

    @Column(name = "Status")
    private String status;

    @Column(name = "UploadBy")
    private Integer uploadBy;

}