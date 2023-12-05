package doc.backendapi.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "dochasusercollections")
public class Dochasusercollection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DocUserCollectionID", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumns({
            @JoinColumn(name = "usercollections_UserCollectionID", referencedColumnName = "UserCollectionID", nullable = false),
            @JoinColumn(name = "usercollections_DocHasUserCollections_DocUserCollectionID", referencedColumnName = "DocHasUserCollections_DocUserCollectionID", nullable = false)
    })
    private Usercollection usercollections;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "documents_DocumentID", nullable = false)
    private Document documentsDocumentid;

}