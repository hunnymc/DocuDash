package doc.backendapi.entities;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Size;
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