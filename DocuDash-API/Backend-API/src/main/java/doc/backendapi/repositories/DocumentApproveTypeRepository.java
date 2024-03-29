package doc.backendapi.repositories;

import doc.backendapi.entities.DocumentApproveType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentApproveTypeRepository extends JpaRepository<DocumentApproveType, Integer> {
}