package doc.backendapi.DTO;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
    @Email(regexp = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@" + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$",
            message = "Email is invalid")
    String emailSource;

    @Size(max = 15, message = "Phone size must not be more than 15")
    String phoneSource;

    @Size(max = 255)
    String description;

}