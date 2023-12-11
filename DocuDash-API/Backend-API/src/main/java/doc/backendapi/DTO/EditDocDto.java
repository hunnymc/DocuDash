package doc.backendapi.DTO;

import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link doc.backendapi.entities.Document}
 */
@Data
@NoArgsConstructor
public class EditDocDto implements Serializable {
    @Size(max = 255)
    String title;
    @Size(max = 255)
    String filePath;
    @Size(max = 255)
    String category;
    @Size(max = 255)
    String fromSource;
    @Size(max = 255)
    String branchSource;
    @Size(max = 255)
    String emailSource;
    @Size(max = 255)
    String phoneSource;
    @Size(max = 255)
    String description;
}