package doc.backendapi.DTO.approval;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * DTO for {@link doc.backendapi.entities.Verifydoc}
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateVerifyManagerDto implements Serializable {

    Integer documentID;
    Integer managerID;
    Integer documentApproveTypeID;

}

