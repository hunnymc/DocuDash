package doc.backendapi.DTO.approval;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * DTO for {@link doc.backendapi.entities.Verifydoc}
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateVerifyManagerDto implements Serializable {

    @NotNull(message = "Document ID must not be null")
    @NotBlank(message = "Document ID must not be blank")
    Integer documentID;

    @NotNull(message = "Manager ID must not be null")
    @NotBlank(message = "Manager ID must not be blank")
    Integer managerID;

    @NotNull(message = "Document Approve Type ID must not be null")
    @NotBlank(message = "Document Approve Type ID must not be blank")
    Integer documentApproveTypeID;

}

