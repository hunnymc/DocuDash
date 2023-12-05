package doc.backendapi.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class UsercollectionId implements Serializable {
    private static final long serialVersionUID = -8167056415043956178L;
    @NotNull
    @Column(name = "UserCollectionID", nullable = false)
    private Integer userCollectionID;

    @NotNull
    @Column(name = "DocHasUserCollections_DocUserCollectionID", nullable = false)
    private Integer dochasusercollectionsDocusercollectionid;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        UsercollectionId entity = (UsercollectionId) o;
        return Objects.equals(this.userCollectionID, entity.userCollectionID) &&
                Objects.equals(this.dochasusercollectionsDocusercollectionid, entity.dochasusercollectionsDocusercollectionid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userCollectionID, dochasusercollectionsDocusercollectionid);
    }

}