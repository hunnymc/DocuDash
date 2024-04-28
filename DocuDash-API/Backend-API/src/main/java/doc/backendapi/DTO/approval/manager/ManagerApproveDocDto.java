package doc.backendapi.DTO.approval.manager;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ManagerApproveDocDto implements Serializable {

    @NotNull(message = "Document ID must not be null")
    @NotBlank(message = "Document ID must not be blank")
    Integer document_id; // document id

    @NotNull(message = "Pass status must not be null")
    @NotBlank(message = "Document ID must not be blank")
    @Min(value = 0, message = "Pass status must be either 0 or 1")
    @Max(value = 1, message = "Pass status must be either 0 or 1")
    Integer is_pass; // 1 = pass, 0 = fail

    @NotNull(message = "Manager ID must not be null")
    @NotBlank(message = "Manager ID must not be blank")
    Integer manager_id; // manager id

    String comment; // comment

}