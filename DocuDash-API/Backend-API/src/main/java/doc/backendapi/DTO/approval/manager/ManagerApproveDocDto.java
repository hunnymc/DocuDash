package doc.backendapi.DTO.approval.manager;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ManagerApproveDocDto implements Serializable {
    Integer document_id; // document id
    Integer is_pass; // 1 = pass, 0 = fail
    Integer manager_id; // manager id
}