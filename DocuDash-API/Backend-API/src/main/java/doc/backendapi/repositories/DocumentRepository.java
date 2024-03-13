package doc.backendapi.repositories;

import doc.backendapi.entities.Document;
import doc.backendapi.entities.User;
import javax.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Arrays;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Integer> {
    Arrays findAllByUsersUserid(@NotNull User usersUserid);

    @Query("SELECT COALESCE(MAX(d.id), 0) + 1 FROM Document d")
    int getNewDocId();

}