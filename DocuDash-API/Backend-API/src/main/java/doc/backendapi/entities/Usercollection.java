package doc.backendapi.entities;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "usercollections")
public class Usercollection {
    @EmbeddedId
    private UsercollectionId id;

    @Size(max = 255)
    @Column(name = "CollectName")
    private String collectName;

    @Size(max = 255)
    @Column(name = "Description")
    private String description;

}